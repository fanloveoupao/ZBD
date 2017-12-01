package com.tgnet.events;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by fan-gk on 2017/4/20.
 */

public class EventBusUtil {
    public static EventBus getInstance(){
        return EventBus.getDefault();
    }
}
