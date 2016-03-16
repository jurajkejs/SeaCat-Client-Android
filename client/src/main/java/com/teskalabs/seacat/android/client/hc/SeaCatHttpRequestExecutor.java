/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package com.teskalabs.seacat.android.client.hc;

import com.teskalabs.seacat.android.client.SeaCatInternals;

import org.apache.http.HttpClientConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpProcessor;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * HttpRequestExecutor is a client side HTTP protocol handler based on the 
 * blocking I/O model that implements the essential requirements of the HTTP 
 * protocol for the client side message  processing, as described by RFC 2616. 
 * <br>
 * HttpRequestExecutor relies on {@link HttpProcessor} to generate mandatory 
 * protocol headers for all outgoing messages and apply common, cross-cutting 
 * message transformations to all incoming and outgoing messages. Application 
 * specific processing can be implemented outside HttpRequestExecutor once the 
 * request has been executed and a response has been received.
 *
 *
 * @version $Revision$
 * 
 * @since 4.0
 */
public class SeaCatHttpRequestExecutor extends org.apache.http.protocol.HttpRequestExecutor
{
    /**
     * Sends the request and obtain a response.
     *
     * @param request   the request to execute.
     * @param conn      the connection over which to execute the request.
     *
     * @return  the response to the request.
     *
     * @throws IOException in case of an I/O error.
     * @throws HttpException in case of HTTP protocol violation or a processing 
     *   problem.
     */    
    public HttpResponse execute(
            final HttpRequest request,
            final HttpClientConnection conn,
            final HttpContext context)
                throws IOException, HttpException
    {
        if (request == null) {
            throw new IllegalArgumentException("HTTP request may not be null");
        }
        if (conn == null) {
            throw new IllegalArgumentException("Client connection may not be null");
        }
        if (context == null) {
            throw new IllegalArgumentException("HTTP context may not be null");
        }

        try {
            HttpResponse response = doSendRequest(request, conn, context);
            if (response == null) {
                response = doReceiveResponse(request, conn, context);
            }
            return response;
        } catch (IOException ex) {
            conn.close();
            throw ex;
        } catch (HttpException ex) {
            conn.close();
            throw ex;
        } catch (RuntimeException ex) {
            conn.close();
            throw ex;
        }
    }

    /**
     * Pre-process the given request using the given protocol processor and 
     * initiates the process of request execution.
     *
     * @param request   the request to prepare
     * @param processor the processor to use
     * @param context   the context for sending the request
     *
     * @throws IOException in case of an I/O error.
     * @throws HttpException in case of HTTP protocol violation or a processing 
     *   problem.
     */
    public void preProcess(
            final HttpRequest request,
            final HttpProcessor processor,
            final HttpContext context)
                throws HttpException, IOException
    {
        if (request == null) {
            throw new IllegalArgumentException("HTTP request may not be null");
        }
        if (processor == null) {
            throw new IllegalArgumentException("HTTP processor may not be null");
        }
        if (context == null) {
            throw new IllegalArgumentException("HTTP context may not be null");
        }

        context.setAttribute(ExecutionContext.HTTP_REQUEST, request);
        processor.process(request, context);

        final String host = request.getFirstHeader("Host").getValue();
        if (!host.endsWith(SeaCatInternals.SeaCatHostSuffix))
            throw new MalformedURLException(String.format("Incorrect SeaCat URL host '%s', the host has to end by '%s'", host, SeaCatInternals.SeaCatHostSuffix));
    }

    /**
     * Send the given request over the given connection.
     * <p>
     * This method also handles the expect-continue handshake if necessary.
     * If it does not have to handle an expect-continue handshake, it will
     * not use the connection for reading or anything else that depends on
     * data coming in over the connection.
     *
     * @param request   the request to send, already
     *                  {@link #preProcess preprocessed}
     * @param conn      the connection over which to send the request,
     *                  already established
     * @param context   the context for sending the request
     *
     * @return  always null
     *
     * @throws IOException in case of an I/O error.
     * @throws HttpException in case of HTTP protocol violation or a processing 
     *   problem.
     */
    protected HttpResponse doSendRequest(
            final HttpRequest request,
            final HttpClientConnection conn,
            final HttpContext context)
                throws IOException, HttpException
    {
        if (request == null) {
            throw new IllegalArgumentException("HTTP request may not be null");
        }
        if (conn == null) {
            throw new IllegalArgumentException("HTTP connection may not be null");
        }
        if (context == null) {
            throw new IllegalArgumentException("HTTP context may not be null");
        }

        HttpResponse response = null;

        context.setAttribute(ExecutionContext.HTTP_CONNECTION, conn);
        context.setAttribute(ExecutionContext.HTTP_REQ_SENT, Boolean.FALSE);

        conn.sendRequestHeader(request);
        if (request instanceof HttpEntityEnclosingRequest)
        {
            conn.sendRequestEntity((HttpEntityEnclosingRequest) request);
        }

        conn.flush();


        context.setAttribute(ExecutionContext.HTTP_REQ_SENT, Boolean.TRUE);

        return null;
    } 

    /**
     * Waits for and receives a response.
     * This method will automatically ignore intermediate responses
     * with status code 1xx.
     *
     * @param request   the request for which to obtain the response
     * @param conn      the connection over which the request was sent
     * @param context   the context for receiving the response
     *
     * @return  the terminal response, not yet post-processed
     *
     * @throws IOException in case of an I/O error.
     * @throws HttpException in case of HTTP protocol violation or a processing 
     *   problem.
     */
    protected HttpResponse doReceiveResponse(
            final HttpRequest          request,
            final HttpClientConnection conn,
            final HttpContext          context)
                throws HttpException, IOException
    {
        if (request == null) {
            throw new IllegalArgumentException("HTTP request may not be null");
        }
        if (conn == null) {
            throw new IllegalArgumentException("HTTP connection may not be null");
        }
        if (context == null) {
            throw new IllegalArgumentException("HTTP context may not be null");
        }

        HttpResponse response = null;
        int statuscode = 0;

        while (response == null || statuscode < HttpStatus.SC_OK) {

            response = conn.receiveResponseHeader();
            if (canResponseHaveBody(request, response)) {
                conn.receiveResponseEntity(response);
            }
            statuscode = response.getStatusLine().getStatusCode();

        } // while intermediate response

        return response;

    }

    /**
     * Post-processes the given response using the given protocol processor and 
     * completes the process of request execution.
     * <p>
     * This method does <i>not</i> read the response entity, if any.
     * The connection over which content of the response entity is being 
     * streamed from cannot be reused until {@link HttpEntity#consumeContent()} 
     * has been invoked.
     *
     * @param response  the response object to post-process
     * @param processor the processor to use
     * @param context   the context for post-processing the response
     *
     * @throws IOException in case of an I/O error.
     * @throws HttpException in case of HTTP protocol violation or a processing 
     *   problem.
     */
    public void postProcess(
            final HttpResponse response,
            final HttpProcessor processor,
            final HttpContext context)
                throws HttpException, IOException
    {
        if (response == null) {
            throw new IllegalArgumentException("HTTP response may not be null");
        }
        if (processor == null) {
            throw new IllegalArgumentException("HTTP processor may not be null");
        }
        if (context == null) {
            throw new IllegalArgumentException("HTTP context may not be null");
        }
        context.setAttribute(ExecutionContext.HTTP_RESPONSE, response);
        processor.process(response, context);
    }


    /**
     * Decide whether a response comes with an entity.
     * The implementation in this class is based on RFC 2616.
     * Unknown methods and response codes are supposed to
     * indicate responses with an entity.
     * <br/>
     * Derived executors can override this method to handle
     * methods and response codes not specified in RFC 2616.
     *
     * @param request   the request, to obtain the executed method
     * @param response  the response, to obtain the status code
     */
    protected boolean canResponseHaveBody(HttpRequest request, HttpResponse response)
    {
        if (request == null) {
            throw new IllegalArgumentException("HTTP request may not be null");
        }
        if (response == null) {
            throw new IllegalArgumentException("HTTP response may not be null");
        }

        if ("HEAD".equalsIgnoreCase(request.getRequestLine().getMethod()))
        {
            return false;
        }
        int status = response.getStatusLine().getStatusCode();
        return status >= HttpStatus.SC_OK
                && status != HttpStatus.SC_NO_CONTENT
                && status != HttpStatus.SC_NOT_MODIFIED
                && status != HttpStatus.SC_RESET_CONTENT;
    }

} // class HttpRequestExecutor
