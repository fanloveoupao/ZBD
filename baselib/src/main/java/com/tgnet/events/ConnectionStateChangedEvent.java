package com.tgnet.events;

/**
 * Created by fan-gk on 2017/4/21.
 */

public class ConnectionStateChangedEvent implements IEvent {
    private int type;
    private boolean isConnected;

    public ConnectionStateChangedEvent(int type, boolean isConnected) {
        this.type = type;
        this.isConnected = isConnected;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public int getType() {
        return type;
    }
}
