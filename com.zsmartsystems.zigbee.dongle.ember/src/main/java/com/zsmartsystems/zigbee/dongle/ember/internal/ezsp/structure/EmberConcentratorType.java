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
 * Class to implement the Ember Enumeration <b>EmberConcentratorType</b>.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public enum EmberConcentratorType {
	/**
	 * Default unknown value
	 */
	UNKNOWN(-1),

	/**
	 * A concentrator with insufficient memory to store source routes for the entire
	 * network. Route records are sent to the concentrator prior to every inbound
	 * APS unicast.
	 */
	EMBER_LOW_RAM_CONCENTRATOR(0xFFF8),

	/**
	 * A concentrator with sufficient memory to store source routes for the entire
	 * network. Remote nodes stop sending route records once the concentrator has
	 * successfully received one.
	 */
	EMBER_HIGH_RAM_CONCENTRATOR(0xFFF9);

	/**
	 * A mapping between the integer code and its corresponding type to facilitate
	 * lookup by code.
	 */
	private static Map<Integer, EmberConcentratorType> codeMapping;

	private int key;

	private EmberConcentratorType(int key) {
		this.key = key;
	}

	private static void initMapping() {
		codeMapping = new HashMap<Integer, EmberConcentratorType>();
		for (EmberConcentratorType s : values()) {
			codeMapping.put(s.key, s);
		}
	}

	/**
	 * Lookup function based on the EmberStatus type code. Returns null if the code
	 * does not exist.
	 *
	 * @param code
	 *            the code to lookup
	 * @return enumeration value of the alarm type.
	 */
	public static EmberConcentratorType getEmberConcentratorType(int code) {
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
