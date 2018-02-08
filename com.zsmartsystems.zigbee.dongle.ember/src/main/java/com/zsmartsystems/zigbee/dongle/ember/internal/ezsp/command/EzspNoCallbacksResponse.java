/**
 * Copyright (c) 2016-2017 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.command;

import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.EzspFrameResponse;

/**
 * Class to implement the Ember EZSP command <b>noCallbacks</b>.
 * <p>
 * Indicates that there are currently no pending callbacks.
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspNoCallbacksResponse extends EzspFrameResponse {
	public static int FRAME_ID = 0x07;

	/**
	 * Response and Handler constructor
	 */
	public EzspNoCallbacksResponse(int[] inputBuffer) {
		// Super creates deserializer and reads header fields
		super(inputBuffer);

		// Deserialize the fields
	}

	@Override
	public String toString() {
		return "EzspNoCallbacksResponse []";
	}
}
