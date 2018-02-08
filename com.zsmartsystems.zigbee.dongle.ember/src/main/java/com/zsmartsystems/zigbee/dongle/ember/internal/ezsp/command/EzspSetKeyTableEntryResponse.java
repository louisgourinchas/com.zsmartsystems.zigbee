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
 * Class to implement the Ember EZSP command <b>setKeyTableEntry</b>.
 * <p>
 * Sets the key table entry at the specified index.
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspSetKeyTableEntryResponse extends EzspFrameResponse {
    public static int FRAME_ID = 0x72;

    /**
     * EMBER_KEY_INVALID if the passed key data is using one of the reserved key values.
     * EMBER_INDEX_OUT_OF_RANGE if passed index is not valid. EMBER_SUCCESS on success.
     * <p>
     * EZSP type is <i>EmberStatus</i> - Java type is {@link EmberStatus}
     */
    private EmberStatus status;

    /**
     * Response and Handler constructor
     */
    public EzspSetKeyTableEntryResponse(int[] inputBuffer) {
        // Super creates deserializer and reads header fields
        super(inputBuffer);

        // Deserialize the fields
        status = deserializer.deserializeEmberStatus();
    }

    /**
     * EMBER_KEY_INVALID if the passed key data is using one of the reserved key values.
     * EMBER_INDEX_OUT_OF_RANGE if passed index is not valid. EMBER_SUCCESS on success.
     * <p>
     * EZSP type is <i>EmberStatus</i> - Java type is {@link EmberStatus}
     *
     * @return the current status as {@link EmberStatus}
     */
    public EmberStatus getStatus() {
        return status;
    }

    /**
     * EMBER_KEY_INVALID if the passed key data is using one of the reserved key values.
     * EMBER_INDEX_OUT_OF_RANGE if passed index is not valid. EMBER_SUCCESS on success.
     *
     * @param status the status to set as {@link EmberStatus}
     */
    public void setStatus(EmberStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(56);
        builder.append("EzspSetKeyTableEntryResponse [status=");
        builder.append(status);
        builder.append(']');
        return builder.toString();
    }
}
