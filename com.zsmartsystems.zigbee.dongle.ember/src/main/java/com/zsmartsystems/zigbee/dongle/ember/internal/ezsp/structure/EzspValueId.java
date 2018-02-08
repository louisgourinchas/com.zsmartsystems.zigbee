/**
 * Copyright (c) 2016-2017 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.structure;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to implement the Ember Enumeration <b>EzspValueId</b>.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public enum EzspValueId {
    /**
     * Default unknown value
     */
    UNKNOWN(-1),

    /**
     * The contents of the node data stack token.
     */
    EZSP_VALUE_TOKEN_STACK_NODE_DATA(0x0000),

    /**
     * The types of MAC passthrough messages that the host wishes to receive.
     */
    EZSP_VALUE_MAC_PASSTHROUGH_FLAGS(0x0001),

    /**
     * The source address used to filter legacy EmberNet messages when the
     * EMBER_MAC_PASSTHROUGH_EMBERNET_SOURCE flag is set in
     * EZSP_VALUE_MAC_PASSTHROUGH_FLAGS.
     */
    EZSP_VALUE_EMBERNET_PASSTHROUGH_SOURCE_ADDRESS(0x0002),

    /**
     * The number of available message buffers.
     */
    EZSP_VALUE_FREE_BUFFERS(0x0003),

    /**
     * Selects sending synchronous callbacks in ezsp-uart.
     */
    EZSP_VALUE_UART_SYNCH_CALLBACKS(0x0004),

    /**
     * The maximum incoming transfer size for the local node.
     */
    EZSP_VALUE_MAXIMUM_INCOMING_TRANSFER_SIZE(0x0005),

    /**
     * The maximum outgoing transfer size for the local node.
     */
    EZSP_VALUE_MAXIMUM_OUTGOING_TRANSFER_SIZE(0x0006),

    /**
     * A boolean indicating whether stack tokens are written to persistent storage as they change.
     */
    EZSP_VALUE_STACK_TOKEN_WRITING(0x0007),

    /**
     * A read-only value indicating whether the stack is currently performing a rejoin.
     */
    EZSP_VALUE_STACK_IS_PERFORMING_REJOIN(0x0008),

    /**
     * A list of EmberMacFilterMatchData values.
     */
    EZSP_VALUE_MAC_FILTER_LIST(0x0009),

    /**
     * The Ember Extended Security Bitmask.
     */
    EZSP_VALUE_EXTENDED_SECURITY_BITMASK(0x000A),

    /**
     * The node short ID.
     */
    EZSP_VALUE_NODE_SHORT_ID(0x000B),

    /**
     * The descriptor capability of the local node.
     */
    EZSP_VALUE_DESCRIPTOR_CAPABILITY(0x000C),

    /**
     * The stack device request sequence number of the local node.
     */
    EZSP_VALUE_STACK_DEVICE_REQUEST_SEQUENCE_NUMBER(0x000D),

    /**
     * Enable or disable radio hold-off.
     */
    EZSP_VALUE_RADIO_HOLD_OFF(0x000E),

    /**
     * The flags field associated with the endpoint data.
     */
    EZSP_VALUE_ENDPOINT_FLAGS(0x000F),

    /**
     * Enable/disable the Mfg security config key settings.
     */
    EZSP_VALUE_MFG_SECURITY_CONFIG(0x0010),

    /**
     * Retrieves the version information from the stack on the NCP.
     */
    EZSP_VALUE_VERSION_INFO(0x0011),

    /**
     * This will get/set the rejoin reason noted by the host for a subsequent call to
     * emberFindAndRejoinNetwork(). After a call to emberFindAndRejoinNetwork() the host's
     * rejoin reason will be set to EMBER_REJOIN_REASON_NONE. The NCP will store the rejoin reason
     * used by the call to emberFindAndRejoinNetwork()
     */
    EZSP_VALUE_NEXT_HOST_REJOIN_REASON(0x0012),

    /**
     * This is the reason that the last rejoin took place. This value may only be retrieved, not set.
     * The rejoin may have been initiated by the stack (NCP) or the application (host). If a host
     * initiated a rejoin the reason will be set by default to EMBER_REJOIN_DUE_TO_APP_EVENT_1.
     * If the application wishes to denote its own rejoin reasons it can do so by calling
     * ezspSetValue(EMBER_VALUE_HOST_REJOIN_REASON, EMBER_REJOIN_DUE_TO_APP_EVENT_X). X
     * is a number corresponding to one of the app events defined. If the NCP initiated a rejoin it
     * will record this value internally for retrieval by
     * ezspGetValue(EZSP_VALUE_REAL_REJOIN_REASON).
     */
    EZSP_VALUE_LAST_REJOIN_REASON(0x0013),

    /**
     * The next ZigBee sequence number.
     */
    EZSP_VALUE_NEXT_ZIGBEE_SEQUENCE_NUMBER(0x0014),

    /**
     * CCA energy detect threshold for radio.
     */
    EZSP_VALUE_CCA_THRESHOLD(0x0015),

    /**
     * The threshold value for a counter
     */
    EZSP_VALUE_SET_COUNTER_THRESHOLD(0x0017),

    /**
     * Resets all counters thresholds to 0xFF
     */
    EZSP_VALUE_RESET_COUNTER_THRESHOLDS(0x0018),

    /**
     * Clears all the counters
     */
    EZSP_VALUE_CLEAR_COUNTERS(0x0019),

    /**
     * The node's new certificate signed by the CA.
     */
    EZSP_VALUE_CERTIFICATE_283K1(0x001A),

    /**
     * The Certificate Authority's public key.
     */
    EZSP_VALUE_PUBLIC_KEY_283K1(0x001B),

    /**
     * The node's new static private key.
     */
    EZSP_VALUE_PRIVATE_KEY_283K1(0x001C),

    /**
     * The NWK layer security frame counter value
     */
    EZSP_VALUE_NWK_FRAME_COUNTER(0x0023),

    /**
     * The APS layer security frame counter value
     */
    EZSP_VALUE_APS_FRAME_COUNTER(0x0024),

    /**
     * Sets the device type to use on the next rejoin using device type
     */
    EZSP_VALUE_RETRY_DEVICE_TYPE(0x0025),

    /**
     * Setting this byte enables R21 behavior on the NCP.
     */
    EZSP_VALUE_ENABLE_R21_BEHAVIOR(0x0029),

    /**
     * Configure the antenna mode(0-primary,1-secondary,2-toggle on tx ack fail).
     */
    EZSP_VALUE_ANTENNA_MODE(0x0030),

    /**
     * Enable or disable packet traffic arbitration.
     */
    EZSP_VALUE_ENABLE_PTA(0x0031),

    /**
     * Set packet traffic arbitration configuration options.
     */
    EZSP_VALUE_PTA_OPTIONS(0x0032),

    /**
     * Configure manufacturing library options (0-non-CSMA transmits,1-CSMA transmits).
     */
    EZSP_VALUE_MFGLIB_OPTIONS(0x0033);

    /**
     * A mapping between the integer code and its corresponding type to
     * facilitate lookup by code.
     */
    private static Map<Integer, EzspValueId> codeMapping;

    private int key;

    private EzspValueId(int key) {
        this.key = key;
    }

    private static void initMapping() {
        codeMapping = new HashMap<Integer, EzspValueId>();
        for (EzspValueId s : values()) {
            codeMapping.put(s.key, s);
        }
    }

    /**
     * Lookup function based on the EmberStatus type code. Returns null if the
     * code does not exist.
     *
     * @param code
     *            the code to lookup
     * @return enumeration value of the alarm type.
     */
    public static EzspValueId getEzspValueId(int code) {
        if (codeMapping == null) {
            initMapping();
        }

        if (codeMapping.get(code) == null) {
            return UNKNOWN;
        }

        return codeMapping.get(code);
    }

    /**
     * Returns the EZSP protocol defined value for this enum
     *
     * @return the EZSP protocol key
     */
    public int getKey() {
        return key;
    }
}
