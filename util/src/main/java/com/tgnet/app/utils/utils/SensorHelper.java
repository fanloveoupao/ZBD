package com.tgnet.app.utils.utils;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static android.hardware.SensorManager.SENSOR_DELAY_NORMAL;

/**
 * Created by fan-gk on 2017/4/21.
 */


public class SensorHelper {
    public interface ProximityEventListener {
        void onProximityChanged(boolean near);
    }

    public class ProximityEventListenerWrapper implements SensorEventListener {
        private final ProximityEventListener listener;
        private AtomicBoolean near = new AtomicBoolean();
        private volatile boolean isInit = false;

        public ProximityEventListenerWrapper(ProximityEventListener listener) {
            this.listener = listener;
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            try {
                float value = event.values[0];
                final boolean near = value < 3;
                if(isInit){
                    if(this.near.compareAndSet(!near, near)){
                        listener.onProximityChanged(near);
                    }
                }else {
                    isInit = true;
                    this.near.set(near);
                    listener.onProximityChanged(near);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    private final Activity activity;
    private SensorManager sensorManager;
    private final List<SensorEventListener> listeners = new ArrayList<>();

    public SensorHelper(Activity activity) {
        this.activity = activity;
    }

    private SensorManager sensorManager() {
        if (sensorManager == null)
            sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        return sensorManager;
    }

    public SensorHelper(Fragment fragment) {
        this(fragment.getActivity());
    }

    public void registerListener(int type, SensorEventListener sensorEventListener) {
        Sensor sensor = sensorManager().getDefaultSensor(type);
        if (sensor != null) {
            listeners.add(sensorEventListener);
            sensorManager().registerListener(sensorEventListener, sensor, SENSOR_DELAY_NORMAL);
        }
    }

    public void unregisterListeners() {
        for (SensorEventListener listener : listeners) {
            sensorManager().unregisterListener(listener);
        }
    }

    public void registenrProximityEventListener(ProximityEventListener listener) {
        registerListener(Sensor.TYPE_PROXIMITY, new ProximityEventListenerWrapper(listener));
    }

    public void unregistenrProximityEventListener(ProximityEventListener listener) {
        List<SensorEventListener> removes = new ArrayList<>();
        for (SensorEventListener sensorEventListener : listeners) {
            if (sensorEventListener instanceof ProximityEventListenerWrapper
                    && ((ProximityEventListenerWrapper) sensorEventListener).listener == listener) {
                removes.add(sensorEventListener);
            }
        }
        if(removes.size() > 0){
            for (SensorEventListener remove : removes) {
                sensorManager().unregisterListener(remove);
            }
            listeners.removeAll(removes);
        }
    }
}

