package com.teskalabs.seacat.android.client.intf;

import java.nio.ByteBuffer;

import com.teskalabs.seacat.android.client.core.Reactor;

public interface IStream
{
	void reset();

	boolean receivedALX1_SYN_REPLY(Reactor reactor, ByteBuffer frame, int frameLength, byte frameFlags);
	boolean receivedSPD3_RST_STREAM(Reactor reactor, ByteBuffer frame, int frameLength, byte frameFlags);
	boolean receivedDataFrame(Reactor reactor, ByteBuffer frame, int frameLength, byte frameFlags);
}
