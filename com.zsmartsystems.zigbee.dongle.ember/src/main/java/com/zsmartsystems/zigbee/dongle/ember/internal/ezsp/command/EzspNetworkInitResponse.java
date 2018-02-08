/**
 * Copyright (c) 2016-2017 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.command;

import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.EzspFrameResponse;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.structure.EmberStatus;

/**
 * Class to implement the Ember EZSP command <b>networkInit</b>.
 * <p>
 * Resume network operation after a reboot. The node retains its original type.
 * This should be called on startup whether or not the node was previously part
 * of a network. EMBER_NOT_JOINED is returned if the node is not part of a
 * network.
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspNetworkInitResponse extends EzspFrameResponse {
	public static int FRAME_ID = 0x17;

	/**
	 * An EmberStatus value that indicates one of the following: successful
	 * initialization, EMBER_NOT_JOINED if the node is not part of a network, or the
	 * reason for failure.
	 * <p>
	 * EZSP type is <i>EmberStatus</i> - Java type is {@link EmberStatus}
	 */
	private EmberStatus status;

	/**
	 * Response and Handler constructor
	 */
	public EzspNetworkInitResponse(int[] inputBuffer) {
		// Super creates deserializer and reads header fields
		super(inputBuffer);

		// Deserialize the fields
		status = deserializer.deserializeEmberStatus();
	}

	/**
	 * An EmberStatus value that indicates one of the following: successful
	 * initialization, EMBER_NOT_JOINED if the node is not part of a network, or the
	 * reason for failure.
	 * <p>
	 * EZSP type is <i>EmberStatus</i> - Java type is {@link EmberStatus}
	 *
	 * @return the current status as {@link EmberStatus}
	 */
	public EmberStatus getStatus() {
		return status;
	}

	/**
	 * An EmberStatus value that indicates one of the following: successful
	 * initialization, EMBER_NOT_JOINED if the node is not part of a network, or the
	 * reason for failure.
	 *
	 * @param status
	 *            the status to set as {@link EmberStatus}
	 */
	public void setStatus(EmberStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(51);
		builder.append("EzspNetworkInitResponse [status=");
		builder.append(status);
		builder.append(']');
		return builder.toString();
	}
}
