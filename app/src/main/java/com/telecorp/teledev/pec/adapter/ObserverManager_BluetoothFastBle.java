package com.telecorp.teledev.pec.adapter;


import com.clj.fastble.data.BleDevice;

import java.util.ArrayList;
import java.util.List;

public class ObserverManager_BluetoothFastBle implements Observable_BluetoothFastBle {

    public static ObserverManager_BluetoothFastBle getInstance() {
        return ObserverManagerHolder.sObserverManager;
    }

    private static class ObserverManagerHolder {
        private static final ObserverManager_BluetoothFastBle sObserverManager = new ObserverManager_BluetoothFastBle();
    }

    private List<Observer_BluetoothFastBle> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer_BluetoothFastBle obj) {
        observers.add(obj);
    }

    @Override
    public void deleteObserver(Observer_BluetoothFastBle obj) {
        int i = observers.indexOf(obj);
        if (i >= 0) {
            observers.remove(obj);
        }
    }

    @Override
    public void notifyObserver(BleDevice bleDevice) {
        for (int i = 0; i < observers.size(); i++) {
            Observer_BluetoothFastBle o = observers.get(i);
            o.disConnected(bleDevice);
        }
    }

}
