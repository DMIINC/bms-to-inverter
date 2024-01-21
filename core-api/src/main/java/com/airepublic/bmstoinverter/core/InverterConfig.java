package com.airepublic.bmstoinverter.core;

/**
 * Configuration read from the confg.properties for the {@link Inverter}.
 */
public class InverterConfig {
    private final String portLocator;
    private final int sendInterval;
    private final InverterDescriptor descriptor;

    public InverterConfig(final String portLocator, final int sendInterval, final InverterDescriptor descriptor) {
        this.portLocator = portLocator;
        this.sendInterval = sendInterval;
        this.descriptor = descriptor;
    }


    /**
     * Gets the port locator like /dev/ttyS0, can0, com3, etc.
     *
     * @return the portLocator the port locator
     */
    public String getPortLocator() {
        return portLocator;
    }


    /**
     * Gets the sending interval in seconds.
     *
     * @return the sending interval in seconds
     */
    public int getSendInterval() {
        return sendInterval;
    }


    /**
     * Gets the {@link InverterDescriptor} for the associated {@link Inverter}.
     *
     * @return the {@link InverterDescriptor} for the associated {@link Inverter}
     */
    public InverterDescriptor getDescriptor() {
        return descriptor;
    }
}
