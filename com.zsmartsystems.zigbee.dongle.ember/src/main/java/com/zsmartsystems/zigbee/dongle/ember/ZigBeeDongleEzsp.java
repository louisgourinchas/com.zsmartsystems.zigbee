/**
 * Copyright (c) 2016-2017 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zsmartsystems.zigbee.ExtendedPanId;
import com.zsmartsystems.zigbee.IeeeAddress;
import com.zsmartsystems.zigbee.ZigBeeApsFrame;
import com.zsmartsystems.zigbee.ZigBeeKey;
import com.zsmartsystems.zigbee.ZigBeeNetworkManager.ZigBeeInitializeResponse;
import com.zsmartsystems.zigbee.ZigBeeNodeStatus;
import com.zsmartsystems.zigbee.ZigBeeNwkAddressMode;
import com.zsmartsystems.zigbee.ZigBeeProfileType;
import com.zsmartsystems.zigbee.dongle.ember.internal.EmberFirmwareUpdateHandler;
import com.zsmartsystems.zigbee.dongle.ember.internal.EmberNetworkInitialisation;
import com.zsmartsystems.zigbee.dongle.ember.internal.EmberStackConfiguration;
import com.zsmartsystems.zigbee.dongle.ember.internal.EzspFrameHandler;
import com.zsmartsystems.zigbee.dongle.ember.internal.ash.AshFrameHandler;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.EzspFrame;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.EzspFrameRequest;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.EzspFrameResponse;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.command.EzspAddEndpointRequest;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.command.EzspAddEndpointResponse;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.command.EzspChildJoinHandler;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.command.EzspGetChildDataRequest;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.command.EzspGetChildDataResponse;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.command.EzspGetCurrentSecurityStateRequest;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.command.EzspGetCurrentSecurityStateResponse;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.command.EzspGetNetworkParametersRequest;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.command.EzspGetNetworkParametersResponse;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.command.EzspGetParentChildParametersRequest;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.command.EzspGetParentChildParametersResponse;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.command.EzspIncomingMessageHandler;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.command.EzspLaunchStandaloneBootloaderRequest;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.command.EzspLaunchStandaloneBootloaderResponse;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.command.EzspNetworkInitRequest;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.command.EzspNetworkInitResponse;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.command.EzspNetworkStateRequest;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.command.EzspNetworkStateResponse;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.command.EzspSendBroadcastRequest;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.command.EzspSendMulticastRequest;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.command.EzspSendUnicastRequest;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.command.EzspSetConcentratorRequest;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.command.EzspSetConcentratorResponse;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.command.EzspStackStatusHandler;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.command.EzspTrustCenterJoinHandler;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.command.EzspVersionRequest;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.command.EzspVersionResponse;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.structure.EmberApsFrame;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.structure.EmberApsOption;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.structure.EmberConcentratorType;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.structure.EmberCurrentSecurityState;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.structure.EmberKeyData;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.structure.EmberNetworkParameters;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.structure.EmberNetworkStatus;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.structure.EmberOutgoingMessageType;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.structure.EmberStatus;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.structure.EzspConfigId;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.structure.EzspDecisionId;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.structure.EzspPolicyId;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.structure.EzspStatus;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.transaction.EzspSingleResponseTransaction;
import com.zsmartsystems.zigbee.dongle.ember.internal.ezsp.transaction.EzspTransaction;
import com.zsmartsystems.zigbee.transport.ConcentratorConfig;
import com.zsmartsystems.zigbee.transport.TransportConfig;
import com.zsmartsystems.zigbee.transport.TransportConfigOption;
import com.zsmartsystems.zigbee.transport.TransportConfigResult;
import com.zsmartsystems.zigbee.transport.ZigBeePort;
import com.zsmartsystems.zigbee.transport.ZigBeeTransportFirmwareCallback;
import com.zsmartsystems.zigbee.transport.ZigBeeTransportFirmwareStatus;
import com.zsmartsystems.zigbee.transport.ZigBeeTransportFirmwareUpdate;
import com.zsmartsystems.zigbee.transport.ZigBeeTransportReceive;
import com.zsmartsystems.zigbee.transport.ZigBeeTransportState;
import com.zsmartsystems.zigbee.transport.ZigBeeTransportTransmit;

/**
 * Implementation of the Silabs Ember NCP (Network Co Processor) EZSP dongle
 * implementation.
 *
 * @author Chris Jackson
 *
 */
public class ZigBeeDongleEzsp implements ZigBeeTransportTransmit, ZigBeeTransportFirmwareUpdate, EzspFrameHandler {

    /**
     * The {@link Logger}.
     */
    private final Logger logger = LoggerFactory.getLogger(ZigBeeDongleEzsp.class);

    /**
     * The serial port used to connect to the dongle
     */
    private ZigBeePort serialPort;

    /**
     * The ASH protocol handler used to send and receive EZSP packets
     */
    private AshFrameHandler ashHandler;

    /**
     * The Ember bootload handler
     */
    private EmberFirmwareUpdateHandler bootloadHandler;

    /**
     * The stack configuration we need for the NCP
     */
    private Map<EzspConfigId, Integer> stackConfiguration;

    /**
     * The stack policies we need for the NCP
     */
    private Map<EzspPolicyId, EzspDecisionId> stackPolicies;

    /**
     * The reference to the receive interface
     */
    private ZigBeeTransportReceive zigbeeTransportReceive;

    /**
     * The current network key as {@link EmberKeyData}
     */
    private final EmberKeyData networkKey = new EmberKeyData();

    /**
     * The current network parameters as {@link EmberNetworkParameters}
     */
    private EmberNetworkParameters networkParameters = new EmberNetworkParameters();

    /**
     * The IeeeAddress of the Ember NCP
     */
    private IeeeAddress ieeeAddress;

    /**
     * The Ember version used in this system. Set during initialisation and saved in case the client is interested.
     */
    private String versionString = "Unknown";

	/**
	 * ezsp frame listener
	 */
	private List<EzspFrameHandler> ezspListener = new ArrayList<EzspFrameHandler>();

    public ZigBeeDongleEzsp(final ZigBeePort serialPort) {
        this.serialPort = serialPort;

        stackConfiguration = new HashMap<EzspConfigId, Integer>();
        stackConfiguration.put(EzspConfigId.EZSP_CONFIG_SOURCE_ROUTE_TABLE_SIZE, 16);
        stackConfiguration.put(EzspConfigId.EZSP_CONFIG_SECURITY_LEVEL, 5);
        stackConfiguration.put(EzspConfigId.EZSP_CONFIG_ADDRESS_TABLE_SIZE, 8);
        stackConfiguration.put(EzspConfigId.EZSP_CONFIG_TRUST_CENTER_ADDRESS_CACHE_SIZE, 2);
        stackConfiguration.put(EzspConfigId.EZSP_CONFIG_STACK_PROFILE, 2);
        stackConfiguration.put(EzspConfigId.EZSP_CONFIG_INDIRECT_TRANSMISSION_TIMEOUT, 7680);
        stackConfiguration.put(EzspConfigId.EZSP_CONFIG_MAX_HOPS, 30);
        stackConfiguration.put(EzspConfigId.EZSP_CONFIG_TX_POWER_MODE, 0);
        stackConfiguration.put(EzspConfigId.EZSP_CONFIG_SUPPORTED_NETWORKS, 2);
        stackConfiguration.put(EzspConfigId.EZSP_CONFIG_KEY_TABLE_SIZE, 4);
        stackConfiguration.put(EzspConfigId.EZSP_CONFIG_APPLICATION_ZDO_FLAGS, 0x01);
        stackConfiguration.put(EzspConfigId.EZSP_CONFIG_MAX_END_DEVICE_CHILDREN, 16);

        stackPolicies = new HashMap<EzspPolicyId, EzspDecisionId>();
        stackPolicies.put(EzspPolicyId.EZSP_TRUST_CENTER_POLICY, EzspDecisionId.EZSP_ALLOW_PRECONFIGURED_KEY_JOINS);
        stackPolicies.put(EzspPolicyId.EZSP_MESSAGE_CONTENTS_IN_CALLBACK_POLICY,
                EzspDecisionId.EZSP_MESSAGE_TAG_ONLY_IN_CALLBACK);
        // stackPolicies.put(EzspPolicyId.EZSP_APP_KEY_REQUEST_POLICY, value)

        // stackPolicies.put(EzspPolicyId.EZSP_POLL_HANDLER_POLICY, EzspDecisionId.EZSP_POLL_HANDLER_CALLBACK);
        stackPolicies.put(EzspPolicyId.EZSP_BINDING_MODIFICATION_POLICY,
                EzspDecisionId.EZSP_CHECK_BINDING_MODIFICATIONS_ARE_VALID_ENDPOINT_CLUSTERS);

        networkKey.setContents(new int[16]);
    }

    @Override
    public ZigBeeInitializeResponse initialize() {
	logger.debug("EZSP dongle initialize.");

	if (null != zigbeeTransportReceive) {
		zigbeeTransportReceive.setNetworkState(ZigBeeTransportState.UNINITIALISED);
	}

	if (!initializeEzspProtocol()) {
		return ZigBeeInitializeResponse.FAILED;
	}

	// Perform any stack configuration
	applyStackConfiguration();
	applyStackPolicy();

	getNetworkParameters();

	// Add the endpoint
	addEndpoint(1, 0x0000, ZigBeeProfileType.HOME_AUTOMATION.getId(), new int[] { 0 }, new int[] { 0 });

	// Now initialise the network
	EmberStatus l_nwk_init_status = initializeZigbeeNetwork();

	networkParameters = getNetworkParameters();
	getCurrentSecurityState();

	if (null != zigbeeTransportReceive) {
		zigbeeTransportReceive.setNetworkState(ZigBeeTransportState.INITIALISING);
	}

	logger.debug("EZSP dongle initialize done: Initialised {}", l_nwk_init_status == EmberStatus.EMBER_NOT_JOINED);

	// Check if the network is initialised or if we're yet to join
	if (l_nwk_init_status == EmberStatus.EMBER_NOT_JOINED) {
		return ZigBeeInitializeResponse.NOT_JOINED;
	}

	return ZigBeeInitializeResponse.JOINED;
    }

    @Override
    public boolean startup(boolean reinitialize) {
        logger.debug("EZSP dongle startup.");

        // If ashHandler is null then the serial port didn't initialise
        if (ashHandler == null) {
            logger.error("Initialising Ember Dongle but low level handle is not initialised.");
            return false;
        }

        // Check if the network is initialised
        EzspNetworkStateRequest networkStateRequest = new EzspNetworkStateRequest();
        EzspTransaction networkStateTransaction = ashHandler.sendEzspTransaction(
                new EzspSingleResponseTransaction(networkStateRequest, EzspNetworkStateResponse.class));
        EzspNetworkStateResponse networkStateResponse = (EzspNetworkStateResponse) networkStateTransaction
                .getResponse();
        logger.debug(networkStateResponse.toString());
        logger.debug("EZSP networkStateResponse {}", networkStateResponse.getStatus());

        // If we want to reinitialize the network, then go...
        if (reinitialize) {
            logger.debug("Reinitialising Ember NCP and forming network.");
            initialiseNetwork();
        }

        // Check if the network is now up
        networkStateTransaction = ashHandler.sendEzspTransaction(
                new EzspSingleResponseTransaction(networkStateRequest, EzspNetworkStateResponse.class));
        networkStateResponse = (EzspNetworkStateResponse) networkStateTransaction.getResponse();
        logger.debug(networkStateResponse.toString());
        logger.debug("EZSP networkStateResponse {}", networkStateResponse.getStatus());
        if (networkStateResponse.getStatus() == EmberNetworkStatus.EMBER_JOINED_NETWORK) {
            zigbeeTransportReceive.setNetworkState(ZigBeeTransportState.ONLINE);
        }

        // Get the security state - mainly for debug
        EmberCurrentSecurityState currentSecurityState = getCurrentSecurityState();
        logger.debug("Current Security State = {}", currentSecurityState);

        EzspGetParentChildParametersRequest childParametersRequest = new EzspGetParentChildParametersRequest();
        EzspTransaction childParametersTransaction = ashHandler.sendEzspTransaction(
                new EzspSingleResponseTransaction(childParametersRequest, EzspGetParentChildParametersResponse.class));
        EzspGetParentChildParametersResponse childParametersResponse = (EzspGetParentChildParametersResponse) childParametersTransaction
                .getResponse();

        logger.debug("Current Parent Child Information = {}", childParametersResponse);
        for (int childId = 0; childId < childParametersResponse.getChildCount(); childId++) {
            getChildInformation(childId);
        }

        logger.debug("EZSP dongle startup done.");

        return true;
    }

    @Override
    public void shutdown() {
        if (ashHandler == null) {
            return;
        }

        ashHandler.setClosing();
        serialPort.close();
        ashHandler.close();
        ashHandler = null;
    }

    @Override
    public IeeeAddress getIeeeAddress() {
        return ieeeAddress;
    }

    /**
     * Gets the current network parameters, or an empty parameters class if there's an error
     *
     * @return {@link EmberNetworkParameters}
     */
    private EmberNetworkParameters getNetworkParameters() {
        EzspGetNetworkParametersRequest networkParms = new EzspGetNetworkParametersRequest();
        EzspSingleResponseTransaction transaction = new EzspSingleResponseTransaction(networkParms,
                EzspGetNetworkParametersResponse.class);
        ashHandler.sendEzspTransaction(transaction);
        EzspGetNetworkParametersResponse getNetworkParametersResponse = (EzspGetNetworkParametersResponse) transaction
                .getResponse();
        logger.debug(getNetworkParametersResponse.toString());
        if (getNetworkParametersResponse.getStatus() != EmberStatus.EMBER_SUCCESS
                && getNetworkParametersResponse.getStatus() != EmberStatus.EMBER_NOT_JOINED) {
            logger.debug("Error during retrieval of network parameters: {}", getNetworkParametersResponse);
            return new EmberNetworkParameters();
        }

        return getNetworkParametersResponse.getParameters();
    }

    private void initialiseNetwork() {
        EmberNetworkInitialisation netInitialiser = new EmberNetworkInitialisation(ashHandler);
        netInitialiser.formNetwork(networkParameters, networkKey);

        EzspGetNetworkParametersRequest networkParametersRequest = new EzspGetNetworkParametersRequest();
        EzspTransaction networkParametersTransaction = ashHandler.sendEzspTransaction(
                new EzspSingleResponseTransaction(networkParametersRequest, EzspGetNetworkParametersResponse.class));
        EzspGetNetworkParametersResponse networkParametersResponse = (EzspGetNetworkParametersResponse) networkParametersTransaction
                .getResponse();
        logger.debug(networkParametersResponse.toString());
    }

    private EzspGetChildDataResponse getChildInformation(int childId) {
        EzspGetChildDataRequest childDataRequest = new EzspGetChildDataRequest();
        childDataRequest.setIndex(childId);
        EzspTransaction childDataTransaction = ashHandler.sendEzspTransaction(
                new EzspSingleResponseTransaction(childDataRequest, EzspGetChildDataResponse.class));
        EzspGetChildDataResponse childDataResponse = (EzspGetChildDataResponse) childDataTransaction.getResponse();
        logger.debug(childDataResponse.toString());

        return childDataResponse;
    }

    /**
     * Get the current security parameters
     *
     * @return the {@link EmberNetworkParameters} or null on error
     */
    private EmberCurrentSecurityState getCurrentSecurityState() {
        EzspGetCurrentSecurityStateRequest networkParms = new EzspGetCurrentSecurityStateRequest();
        EzspSingleResponseTransaction transaction = new EzspSingleResponseTransaction(networkParms,
                EzspGetCurrentSecurityStateResponse.class);
        ashHandler.sendEzspTransaction(transaction);
        EzspGetCurrentSecurityStateResponse currentSecurityStateResponse = (EzspGetCurrentSecurityStateResponse) transaction
                .getResponse();
        logger.debug(currentSecurityStateResponse.toString());
        if (currentSecurityStateResponse.getStatus() != EmberStatus.EMBER_SUCCESS) {
            logger.debug("Error during retrieval of security parameters: {}", currentSecurityStateResponse);
            return null;
        }
        return currentSecurityStateResponse.getState();
    }

    @Override
    public void sendCommand(final ZigBeeApsFrame apsFrame) {
        if (ashHandler == null) {
            return;
        }
        EzspFrameRequest emberCommand;

        EmberApsFrame emberApsFrame = new EmberApsFrame();
        emberApsFrame.setClusterId(apsFrame.getCluster());
        emberApsFrame.setProfileId(apsFrame.getProfile());
        emberApsFrame.setSourceEndpoint(apsFrame.getSourceEndpoint());
        emberApsFrame.setDestinationEndpoint(apsFrame.getDestinationEndpoint());
        emberApsFrame.setSequence(apsFrame.getApsCounter());
        emberApsFrame.addOptions(EmberApsOption.EMBER_APS_OPTION_RETRY);
        emberApsFrame.addOptions(EmberApsOption.EMBER_APS_OPTION_ENABLE_ROUTE_DISCOVERY);
        emberApsFrame.addOptions(EmberApsOption.EMBER_APS_OPTION_ENABLE_ADDRESS_DISCOVERY);

        if (apsFrame.getAddressMode() == ZigBeeNwkAddressMode.DEVICE && apsFrame.getDestinationAddress() < 0xfff8) {
            EzspSendUnicastRequest emberUnicast = new EzspSendUnicastRequest();
            emberUnicast.setIndexOrDestination(apsFrame.getDestinationAddress());
            emberUnicast.setMessageTag(apsFrame.getSequence());
            emberUnicast.setSequenceNumber(apsFrame.getSequence());
            emberUnicast.setType(EmberOutgoingMessageType.EMBER_OUTGOING_DIRECT);
            emberUnicast.setApsFrame(emberApsFrame);
            emberUnicast.setMessageContents(apsFrame.getPayload());

            emberCommand = emberUnicast;
        } else if (apsFrame.getAddressMode() == ZigBeeNwkAddressMode.DEVICE
                && apsFrame.getDestinationAddress() >= 0xfff8) {

            EzspSendBroadcastRequest emberBroadcast = new EzspSendBroadcastRequest();
            emberBroadcast.setDestination(apsFrame.getDestinationAddress());
            emberBroadcast.setMessageTag(apsFrame.getSequence());
            emberBroadcast.setSequenceNumber(apsFrame.getSequence());
            emberBroadcast.setApsFrame(emberApsFrame);
            emberBroadcast.setRadius(apsFrame.getRadius());
            emberBroadcast.setMessageContents(apsFrame.getPayload());

            emberCommand = emberBroadcast;
        } else if (apsFrame.getAddressMode() == ZigBeeNwkAddressMode.GROUP) {
            emberApsFrame.setGroupId(apsFrame.getGroupAddress());

            EzspSendMulticastRequest emberMulticast = new EzspSendMulticastRequest();
            emberMulticast.setApsFrame(emberApsFrame);
            emberMulticast.setHops(apsFrame.getRadius());
            emberMulticast.setNonmemberRadius(apsFrame.getNonMemberRadius());
            emberMulticast.setMessageTag(apsFrame.getSequence());
            emberMulticast.setMessageContents(apsFrame.getPayload());

            emberCommand = emberMulticast;
        } else {
            logger.debug("EZSP message not sent: {}, {}", apsFrame);
            // ZigBeeGroupAddress groupAddress = (ZigBeeGroupAddress) zclCommand.getDestinationAddress();
            // apsFrame.setGroupId(groupAddress.getGroupId());
            return;
        }

        logger.debug(emberCommand.toString());
        ashHandler.queueFrame(emberCommand);

        // emberUnicast = (EzspSendUnicast) ashHandler.sendEzspRequestAsync(emberUnicast);
    }

    @Override
    public void setZigBeeTransportReceive(ZigBeeTransportReceive zigbeeTransportReceive) {
        this.zigbeeTransportReceive = zigbeeTransportReceive;
    }

    @Override
    public void handlePacket(EzspFrame response) {
        logger.debug("RX: " + response.toString());

        if (response instanceof EzspIncomingMessageHandler) {
            EzspIncomingMessageHandler incomingMessage = (EzspIncomingMessageHandler) response;
            EmberApsFrame emberApsFrame = incomingMessage.getApsFrame();

            ZigBeeApsFrame apsFrame = new ZigBeeApsFrame();
            apsFrame.setApsCounter(emberApsFrame.getSequence());
            apsFrame.setCluster(emberApsFrame.getClusterId());
            apsFrame.setDestinationEndpoint(emberApsFrame.getDestinationEndpoint());
            apsFrame.setProfile(emberApsFrame.getProfileId());
            apsFrame.setSourceEndpoint(emberApsFrame.getSourceEndpoint());

            apsFrame.setSequence(incomingMessage.getSequenceNumber());
            apsFrame.setSourceAddress(incomingMessage.getSender());
            apsFrame.setPayload(incomingMessage.getMessageContents());
            zigbeeTransportReceive.receiveCommand(apsFrame);

            return;
        }

        // TODO: Check if this should be done only after initialisation is complete?
        if (response instanceof EzspStackStatusHandler) {
            switch (((EzspStackStatusHandler) response).getStatus()) {
                case EMBER_NETWORK_BUSY:
                    break;
                case EMBER_NETWORK_DOWN:
                    zigbeeTransportReceive.setNetworkState(ZigBeeTransportState.OFFLINE);
                    break;
                case EMBER_NETWORK_UP:
                    break;
                default:
                    break;
            }
            return;
        }

        if (response instanceof EzspTrustCenterJoinHandler) {
            EzspTrustCenterJoinHandler joinHandler = (EzspTrustCenterJoinHandler) response;

            ZigBeeNodeStatus status;
            switch (joinHandler.getStatus()) {
                case EMBER_HIGH_SECURITY_UNSECURED_JOIN:
                case EMBER_STANDARD_SECURITY_UNSECURED_JOIN:
                    status = ZigBeeNodeStatus.UNSECURED_JOIN;
                    break;
                case EMBER_HIGH_SECURITY_UNSECURED_REJOIN:
                case EMBER_STANDARD_SECURITY_UNSECURED_REJOIN:
                    status = ZigBeeNodeStatus.UNSECURED_REJOIN;
                    break;
                case EMBER_HIGH_SECURITY_SECURED_REJOIN:
                case EMBER_STANDARD_SECURITY_SECURED_REJOIN:
                    status = ZigBeeNodeStatus.SECURED_REJOIN;
                    break;
                case EMBER_DEVICE_LEFT:
                    status = ZigBeeNodeStatus.DEVICE_LEFT;
                    break;
                default:
                    logger.debug("Unknown state in trust centre join handler {}", joinHandler.getStatus());
                    return;
            }

            zigbeeTransportReceive.nodeStatusUpdate(status, joinHandler.getNewNodeId(), joinHandler.getNewNodeEui64());
            return;
        }

        if (response instanceof EzspChildJoinHandler) {
            EzspChildJoinHandler joinHandler = (EzspChildJoinHandler) response;
            zigbeeTransportReceive.nodeStatusUpdate(ZigBeeNodeStatus.UNSECURED_JOIN, joinHandler.getChildId(),
                    joinHandler.getChildEui64());
            return;
        }

        logger.debug("Unhandled EZSP Frame: {}", response.toString());
    }

    @Override
    public void handleLinkStateChange(boolean linkState) {
        // Handle link changes and notify framework or just reset link with dongle?
        zigbeeTransportReceive.setNetworkState(linkState ? ZigBeeTransportState.ONLINE : ZigBeeTransportState.OFFLINE);
    }

    @Override
    public int getZigBeeChannel() {
        return networkParameters.getRadioChannel();
    }

    @Override
    public boolean setZigBeeChannel(int channel) {
        networkParameters.setRadioChannel(channel);
        return false;
    }

    @Override
    public int getZigBeePanId() {
        return networkParameters.getPanId();
    }

    @Override
    public boolean setZigBeePanId(int panId) {
        networkParameters.setPanId(panId);
        return true;
    }

    @Override
    public ExtendedPanId getZigBeeExtendedPanId() {
        return networkParameters.getExtendedPanId();
    }

    @Override
    public boolean setZigBeeExtendedPanId(ExtendedPanId extendedPanId) {
        networkParameters.setExtendedPanId(extendedPanId);
        return false;
    }

    @Override
    public boolean setZigBeeNetworkKey(final ZigBeeKey key) {
        networkKey.setContents(key.getValue());

        return false;
    }

    @Override
    public boolean setTcLinkKey(ZigBeeKey key) {
        return false;
    }

    @Override
    public void updateTransportConfig(TransportConfig configuration) {
        for (TransportConfigOption option : configuration.getOptions()) {
            try {
                switch (option) {
                    case CONCENTRATOR_CONFIG:
                        configuration.setResult(option,
                                setConcentrator((ConcentratorConfig) configuration.getValue(option)));
                        break;

                    case TRUST_CENTRE_LINK_KEY:

                    case TRUST_CENTRE_JOIN_MODE:

                    default:
                        configuration.setResult(option, TransportConfigResult.ERROR_UNSUPPORTED);
                        logger.debug("Unsupported configuration option \"{}\" in EZSP dongle", option);
                        break;
                }
            } catch (ClassCastException e) {
                configuration.setResult(option, TransportConfigResult.ERROR_INVALID_VALUE);
            }
        }
    }

    @Override
    public String getVersionString() {
        return versionString;
    }

    private boolean initialiseEzspProtocol() {
        if (!serialPort.open()) {
            logger.error("Unable to open Ember serial port");
            return false;
        }
        ashHandler = new AshFrameHandler(this);

        // Connect to the ASH handler and NCP
        ashHandler.start(serialPort);
        ashHandler.connect();

        // We MUST send the version command first.
        EzspVersionRequest version = new EzspVersionRequest();
        version.setDesiredProtocolVersion(EzspFrame.getEzspVersion());
        EzspTransaction versionTransaction = ashHandler
                .sendEzspTransaction(new EzspSingleResponseTransaction(version, EzspVersionResponse.class));
        EzspVersionResponse versionResponse = (EzspVersionResponse) versionTransaction.getResponse();
        logger.debug(versionResponse.toString());

        if (versionResponse.getProtocolVersion() != EzspFrame.getEzspVersion()) {
            // The device supports a different version that we current have set
            if (!EzspFrame.setEzspVersion(versionResponse.getProtocolVersion())) {
                logger.error("NCP requires unsupported version of EZSP (required = V{}, supported = V{})",
                        versionResponse.getProtocolVersion(), EzspFrame.getEzspVersion());
                return false;
            }

            version = new EzspVersionRequest();
            version.setDesiredProtocolVersion(EzspFrame.getEzspVersion());
            versionTransaction = ashHandler
                    .sendEzspTransaction(new EzspSingleResponseTransaction(version, EzspVersionResponse.class));
            versionResponse = (EzspVersionResponse) versionTransaction.getResponse();
            logger.debug(versionResponse.toString());
        }

        StringBuilder builder = new StringBuilder(60);
        builder.append("EZSP Version=");
        builder.append(versionResponse.getProtocolVersion());
        builder.append(", Stack Type=");
        builder.append(versionResponse.getStackType());
        builder.append(", Stack Version=");
        for (int cnt = 3; cnt >= 0; cnt--) {
            builder.append((versionResponse.getStackVersion() >> (cnt * 4)) & 0x0F);
            if (cnt != 0) {
                builder.append('.');
            }
        }
        versionString = builder.toString();

        return true;
    }

    @Override
    public boolean updateFirmware(final InputStream firmware, final ZigBeeTransportFirmwareCallback callback) {
        if (ashHandler != null) {
            logger.debug("ashHandler is operating in updateFirmware");
            return false;
        }

        if (!initialiseEzspProtocol()) {
            return false;
        }

        zigbeeTransportReceive.setNetworkState(ZigBeeTransportState.OFFLINE);
        callback.firmwareUpdateCallback(ZigBeeTransportFirmwareStatus.FIRMWARE_UPDATE_STARTED);

        // Send the bootload command, but ignore the response since there doesn't seem to be one
        // despite what the documentation seems to indicate
        EzspLaunchStandaloneBootloaderRequest bootloadCommand = new EzspLaunchStandaloneBootloaderRequest();
        EzspTransaction bootloadTransaction = ashHandler.sendEzspTransaction(
                new EzspSingleResponseTransaction(bootloadCommand, EzspLaunchStandaloneBootloaderResponse.class));
        EzspLaunchStandaloneBootloaderResponse bootloadResponse = (EzspLaunchStandaloneBootloaderResponse) bootloadTransaction
                .getResponse();
        logger.debug(bootloadResponse.toString());
        logger.debug("EZSP bootloadResponse {}", bootloadResponse.getStatus());

        if (bootloadResponse.getStatus() != EzspStatus.EZSP_SUCCESS) {
            callback.firmwareUpdateCallback(ZigBeeTransportFirmwareStatus.FIRMWARE_UPDATE_FAILED);
            logger.debug("EZSP bootload failed: bootloadResponse {}", bootloadResponse.getStatus());
            return false;
        }

        // Stop the handler and close the serial port
        logger.debug("EZSP closing frame handler");
        ashHandler.setClosing();
        serialPort.close();
        ashHandler.close();
        ashHandler = null;

        bootloadHandler = new EmberFirmwareUpdateHandler(this, firmware, serialPort, callback);
        bootloadHandler.startBootload();

        return true;
    }

    // Callback from the bootload handler when the transfer is completed/aborted/failed
    public void bootloadComplete() {
        bootloadHandler = null;
    }

    @Override
    public String getFirmwareVersion() {
        int versionIndex = versionString.indexOf("Stack Version=");
        if (versionIndex == -1) {
            return "";
        }
        return versionString.substring(versionIndex + 14);
    }

    @Override
    public boolean cancelUpdateFirmware() {
        if (bootloadHandler == null) {
            return false;
        }
        bootloadHandler.cancelUpdate();
        return true;
    }

    private TransportConfigResult setConcentrator(ConcentratorConfig concentratorConfig) {
        EzspSetConcentratorRequest concentratorRequest = new EzspSetConcentratorRequest();
        concentratorRequest.setMinTime(concentratorConfig.getRefreshMinimum());
        concentratorRequest.setMaxTime(concentratorConfig.getRefreshMaximum());
        concentratorRequest.setMaxHops(concentratorConfig.getMaxHops());
        concentratorRequest.setRouteErrorThreshold(concentratorConfig.getMaxFailures());
        concentratorRequest.setDeliveryFailureThreshold(concentratorConfig.getMaxFailures());
        switch (concentratorConfig.getType()) {
            case DISABLED:
                concentratorRequest.setEnable(false);
                break;
            case HIGH_RAM:
                concentratorRequest.setConcentratorType(EmberConcentratorType.EMBER_HIGH_RAM_CONCENTRATOR);
                concentratorRequest.setEnable(true);
                break;
            case LOW_RAM:
                concentratorRequest.setConcentratorType(EmberConcentratorType.EMBER_LOW_RAM_CONCENTRATOR);
                concentratorRequest.setEnable(true);
                break;
            default:
                break;
        }

        EzspTransaction concentratorTransaction = ashHandler.sendEzspTransaction(
                new EzspSingleResponseTransaction(concentratorRequest, EzspSetConcentratorResponse.class));
        EzspSetConcentratorResponse concentratorResponse = (EzspSetConcentratorResponse) concentratorTransaction
                .getResponse();
        logger.debug(concentratorResponse.toString());

        if (concentratorResponse.getStatus() == EzspStatus.EZSP_SUCCESS) {
            return TransportConfigResult.SUCCESS;
        }
        return TransportConfigResult.FAILURE;
    }

    /**
     * Get a map of statistics counters from the dongle
     *
     * @return map of counters
     */
    public Map<String, Long> getCounters() {
        Map<String, Long> counters = new HashMap<String, Long>();

        if (ashHandler != null) {
            counters.putAll(ashHandler.getCounters());
        }

        return counters;
    }

	// Low level API

	/**
	 * Minimal initialization : ASH + EZSP Protocol
	 * 
	 * @return true if success
	 */
	public boolean initializeEzspProtocol() {
		boolean lo_success = false;

		logger.debug("EZSP dongle initialize ASH.");

		if (!serialPort.open()) {
			logger.error("Unable to open Ember serial port");
		} else {
			ashHandler = new AshFrameHandler(this);

			// Connect to the ASH handler and NCP
			ashHandler.start(serialPort);
			ashHandler.connect();

			// We MUST send the version command first.
			EzspVersionRequest version = new EzspVersionRequest();
			version.setDesiredProtocolVersion(EzspFrame.getEzspVersion());
			EzspTransaction versionTransaction = ashHandler
					.sendEzspTransaction(new EzspSingleResponseTransaction(version, EzspVersionResponse.class));
			EzspVersionResponse versionResponse = (EzspVersionResponse) versionTransaction.getResponse();
			logger.debug(versionResponse.toString());

			if (EzspFrame.getEzspVersion() != versionResponse.getProtocolVersion()) {
				// check if it is a supported version
				if (EzspFrame.setEzspVersion(versionResponse.getProtocolVersion())) {
					version = new EzspVersionRequest();
					version.setDesiredProtocolVersion(EzspFrame.getEzspVersion());
					versionTransaction = ashHandler
							.sendEzspTransaction(new EzspSingleResponseTransaction(version, EzspVersionResponse.class));
					versionResponse = (EzspVersionResponse) versionTransaction.getResponse();
					logger.debug(versionResponse.toString());

					StringBuilder builder = new StringBuilder(60);
					builder.append("EZSP Version=");
					builder.append(versionResponse.getProtocolVersion());
					builder.append(", Stack Type=");
					builder.append(versionResponse.getStackType());
					builder.append(", Stack Version=");
					for (int cnt = 3; cnt >= 0; cnt--) {
						builder.append((versionResponse.getStackVersion() >> (cnt * 4)) & 0x0F);
						if (cnt != 0) {
							builder.append('.');
						}
					}
					versionString = builder.toString();

					logger.debug("EZSP Protocol version : {}", versionString);
					lo_success = true;
				}
			}
		}

		return lo_success;
	}

	/**
	 * set a new configuration value for stack, Not send to NCP, use
	 * applyStackConfiguration function for that
	 * 
	 * @param i_configId
	 *            : configuration Id
	 * @param i_value
	 *            : configuration value
	 */
	public void setStackConfigurationValue(EzspConfigId i_configId, Integer i_value) {
		stackConfiguration.put(i_configId, i_value);
	}

	/**
	 * Apply to NCP current stack configuration value, shall be done before
	 * initializing network
	 */
	public void applyStackConfiguration() {
		// Perform any stack configuration
		EmberStackConfiguration stackConfigurer = new EmberStackConfiguration(ashHandler);

		stackConfigurer.setConfiguration(stackConfiguration);
		Map<EzspConfigId, Integer> configuration = stackConfigurer.getConfiguration(stackConfiguration.keySet());
		for (EzspConfigId config : configuration.keySet()) {
			logger.debug("Configuration state {} = {}", config, configuration.get(config));
		}
	}

	/**
	 * set a new poicy value, Not send to NCP, use applyStackPolicy function for
	 * that
	 * 
	 * @param i_policyId
	 * @param i_decisionId
	 */
	public void setStackPolicyValue(EzspPolicyId i_policyId, EzspDecisionId i_decisionId) {
		stackPolicies.put(i_policyId, i_decisionId);
	}

	/**
	 * Apply to NCP current policy value, shall be done before initializing network
	 */
	public void applyStackPolicy() {
		// Perform any stack configuration
		EmberStackConfiguration stackConfigurer = new EmberStackConfiguration(ashHandler);

		stackConfigurer.setPolicy(stackPolicies);
		Map<EzspPolicyId, EzspDecisionId> policies = stackConfigurer.getPolicy(stackPolicies.keySet());
		for (EzspPolicyId policy : policies.keySet()) {
			logger.debug("Policy state {} = {}", policy, policies.get(policy));
		}
	}

	/**
	 * adding a new endpoint to NCP, shall be done before initializing network
	 * 
	 * @param i_epNumber
	 * @param i_deviceId
	 * @param i_profileId
	 * @param i_inClusterList
	 * @param i_outClusterList
	 * @return
	 */
	public EzspStatus addEndpoint(int i_epNumber, int i_deviceId, int i_profileId, int[] i_inClusterList,
			int[] i_outClusterList) {
		// Add endpoint
		EzspAddEndpointRequest addEndpoint = new EzspAddEndpointRequest();
		addEndpoint.setEndpoint(i_epNumber);
		addEndpoint.setDeviceId(i_deviceId);
		addEndpoint.setProfileId(i_profileId);
		addEndpoint.setInputClusterList(i_inClusterList);
		addEndpoint.setOutputClusterList(i_outClusterList);
		logger.debug(addEndpoint.toString());
		EzspTransaction addEndpointTransaction = ashHandler
				.sendEzspTransaction(new EzspSingleResponseTransaction(addEndpoint, EzspAddEndpointResponse.class));
		EzspAddEndpointResponse addEndpointResponse = (EzspAddEndpointResponse) addEndpointTransaction.getResponse();
		logger.debug(addEndpointResponse.toString());

		return addEndpointResponse.getStatus();
	}

	/**
	 * Initialize zigbee pro stack
	 * 
	 * @return
	 */
	public EmberStatus initializeZigbeeNetwork() {
		// Initialise the network
		EzspNetworkInitRequest networkInitRequest = new EzspNetworkInitRequest();
		EzspTransaction networkInitTransaction = ashHandler.sendEzspTransaction(
				new EzspSingleResponseTransaction(networkInitRequest, EzspNetworkInitResponse.class));
		EzspNetworkInitResponse networkInitResponse = (EzspNetworkInitResponse) networkInitTransaction.getResponse();
		logger.debug(networkInitResponse.toString());

		networkParameters = getNetworkParameters();
		getCurrentSecurityState();

		logger.debug("EZSP dongle initialize done: Initialised {}",
				networkInitResponse.getStatus() == EmberStatus.EMBER_NOT_JOINED);

		return networkInitResponse.getStatus();
	}

	/**
	 * Direct call EZSP NCP function
	 * 
	 * @param request
	 * @param requiredResponse
	 * @return
	 */
	public EzspFrameResponse singleCall(EzspFrameRequest request, Class<?> requiredResponse) {
		EzspTransaction transaction = ashHandler
				.sendEzspTransaction(new EzspSingleResponseTransaction(request, requiredResponse));
		logger.debug(transaction.toString());
		return transaction.getResponse();
	}

	/***************************************************************************
	 * Listener management
	 */

	public void addListener(EzspFrameHandler listener) {
		if (!this.ezspListener.contains(listener)) {
			this.ezspListener.add(listener);
		}
	}

	public void removeListener(EzspFrameHandler listener) {
		if (this.ezspListener.contains(listener)) {
			this.ezspListener.remove(listener);
		}
	}
}
