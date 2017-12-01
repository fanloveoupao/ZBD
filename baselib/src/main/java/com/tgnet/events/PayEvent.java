package com.tgnet.events;

/**
 * author: ZK.
 * date:   On 2017/10/9.
 */

public class PayEvent implements IEvent {
    public boolean isSuccess() {
        return success;
    }

    private final boolean success;

    public PayEvent(boolean success) {
        this.success = success;
    }
}
