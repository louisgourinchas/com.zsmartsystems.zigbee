/**
 * Copyright (c) 2016-2017 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.command;

import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.EzspFrameRequest;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.serializer.EzspSerializer;

/**
 * Class to implement the Ember EZSP command <b>mfglibSendPacket</b>.
 * <p>
 * Sends a single packet consisting of the following bytes: packetLength,
 * packetContents[0], ... , packetContents[pack-etLength - 3], CRC[0], CRC[1].
 * The total number of bytes sent is packetLength + 1. The radio replaces the
 * last two bytes of packetContents[]with the 16-bit CRC for the packet.
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspMfglibSendPacketRequest extends EzspFrameRequest {
	public static int FRAME_ID = 0x89;

	/**
	 * The packet to send. The last two bytes will be replacedwith the 16-bit CRC.
	 * <p>
	 * EZSP type is <i>uint8_t[]</i> - Java type is {@link int[]}
	 */
	private int[] packetContents;

	/**
	 * Serialiser used to seialise to binary line data
	 */
	private EzspSerializer serializer;

	/**
	 * Request constructor
	 */
	public EzspMfglibSendPacketRequest() {
		frameId = FRAME_ID;
		serializer = new EzspSerializer();
	}

	/**
	 * The packet to send. The last two bytes will be replacedwith the 16-bit CRC.
	 * <p>
	 * EZSP type is <i>uint8_t[]</i> - Java type is {@link int[]}
	 *
	 * @return the current packetContents as {@link int[]}
	 */
	public int[] getPacketContents() {
		return packetContents;
	}

	/**
	 * The packet to send. The last two bytes will be replacedwith the 16-bit CRC.
	 *
	 * @param packetContents
	 *            the packetContents to set as {@link int[]}
	 */
	public void setPacketContents(int[] packetContents) {
		this.packetContents = packetContents;
	}

	@Override
	public int[] serialize() {
		// Serialize the header
		serializeHeader(serializer);

		// Serialize the fields
		serializer.serializeUInt8(packetContents.length);
		serializer.serializeUInt8Array(packetContents);
		return serializer.getPayload();
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(80);
		builder.append("EzspMfglibSendPacketRequest [packetContents=");
		for (int c = 0; c < packetContents.length; c++) {
			if (c > 0) {
				builder.append(' ');
			}
			builder.append(String.format("%02X", packetContents[c]));
		}
		builder.append(']');
		return builder.toString();
	}
}
