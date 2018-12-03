package com.telecorp.teledev.pec.adapter;


import com.clj.fastble.data.BleDevice;

public interface Observable_BluetoothFastBle {

    void addObserver(Observer_BluetoothFastBle obj);

    void deleteObserver(Observer_BluetoothFastBle obj);

    void notifyObserver(BleDevice bleDevice);
}
