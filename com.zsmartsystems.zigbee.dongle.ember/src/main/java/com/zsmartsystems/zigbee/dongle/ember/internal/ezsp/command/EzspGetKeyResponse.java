/**
 * Copyright (c) 2016-2017 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.command;

import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.EzspFrameResponse;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.structure.EmberKeyStruct;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.structure.EmberStatus;

/**
 * Class to implement the Ember EZSP command <b>getKey</b>.
 * <p>
 * Gets a Security Key based on the passed key type.
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspGetKeyResponse extends EzspFrameResponse {
	public static int FRAME_ID = 0x6A;

	/**
	 * The success or failure of the operation.
	 * <p>
	 * EZSP type is <i>EmberStatus</i> - Java type is {@link EmberStatus}
	 */
	private EmberStatus status;

	/**
	 * The structure containing the key and its associated data.
	 * <p>
	 * EZSP type is <i>EmberKeyStruct</i> - Java type is {@link EmberKeyStruct}
	 */
	private EmberKeyStruct keyStruct;

	/**
	 * Response and Handler constructor
	 */
	public EzspGetKeyResponse(int[] inputBuffer) {
		// Super creates deserializer and reads header fields
		super(inputBuffer);

		// Deserialize the fields
		status = deserializer.deserializeEmberStatus();
		keyStruct = deserializer.deserializeEmberKeyStruct();
	}

	/**
	 * The success or failure of the operation.
	 * <p>
	 * EZSP type is <i>EmberStatus</i> - Java type is {@link EmberStatus}
	 *
	 * @return the current status as {@link EmberStatus}
	 */
	public EmberStatus getStatus() {
		return status;
	}

	/**
	 * The success or failure of the operation.
	 *
	 * @param status
	 *            the status to set as {@link EmberStatus}
	 */
	public void setStatus(EmberStatus status) {
		this.status = status;
	}

	/**
	 * The structure containing the key and its associated data.
	 * <p>
	 * EZSP type is <i>EmberKeyStruct</i> - Java type is {@link EmberKeyStruct}
	 *
	 * @return the current keyStruct as {@link EmberKeyStruct}
	 */
	public EmberKeyStruct getKeyStruct() {
		return keyStruct;
	}

	/**
	 * The structure containing the key and its associated data.
	 *
	 * @param keyStruct
	 *            the keyStruct to set as {@link EmberKeyStruct}
	 */
	public void setKeyStruct(EmberKeyStruct keyStruct) {
		this.keyStruct = keyStruct;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(71);
		builder.append("EzspGetKeyResponse [status=");
		builder.append(status);
		builder.append(", keyStruct=");
		builder.append(keyStruct);
		builder.append(']');
		return builder.toString();
	}
}
