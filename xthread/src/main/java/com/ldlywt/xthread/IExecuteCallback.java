package com.ldlywt.xthread;

interface IExecuteCallback {
    void addOnExecuteListener(OnExecuteListener listener);

    void removeOnExecuteListener(OnExecuteListener listener);
}