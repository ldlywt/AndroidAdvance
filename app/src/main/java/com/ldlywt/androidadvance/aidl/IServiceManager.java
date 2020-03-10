/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package com.ldlywt.androidadvance.aidl;
// Declare any non-default types here with import statements

public interface IServiceManager extends android.os.IInterface {
    /**
     * Default implementation for IServiceManager.
     */
    public static class Default implements IServiceManager {
        /**
         * Retrieve an existing service called @a name from the
         * service manager.  Blocks for a few seconds waiting for it to be
         * published if it does not already exist.
         */
        @Override
        public android.os.IBinder getService(String name) throws android.os.RemoteException {
            return null;
        }

        /**
         * Retrieve an existing service called @a name from the
         * service manager.  Non-blocking.
         */
        @Override
        public android.os.IBinder checkService(String name) throws android.os.RemoteException {
            return null;
        }

        /**
         * Place a new @a service called @a name into the service
         * manager.
         */
        @Override
        public void addService(String name, android.os.IBinder service, boolean allowIsolated, int dumpFlags) throws android.os.RemoteException {
        }

        /**
         * Return a list of all currently running services.
         */
        @Override
        public String[] listServices(int dumpFlags) throws android.os.RemoteException {
            return null;
        }

        @Override
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    /**
     * Local-side IPC implementation stub class.
     */
    public static abstract class Stub extends android.os.Binder implements IServiceManager {
        private static final String DESCRIPTOR = "com.ldlywt.androidadvance.IServiceManager";

        /**
         * Construct the stub at attach it to the interface.
         */
        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        /**
         * Cast an IBinder object into an com.ldlywt.androidadvance.IServiceManager interface,
         * generating a proxy if needed.
         */
        public static IServiceManager asInterface(android.os.IBinder obj) {
            if ((obj == null)) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (((iin != null) && (iin instanceof IServiceManager))) {
                return ((IServiceManager) iin);
            }
            return new Stub.Proxy(obj);
        }

        @Override
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override
        public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException {
            String descriptor = DESCRIPTOR;
            switch (code) {
                case INTERFACE_TRANSACTION: {
                    reply.writeString(descriptor);
                    return true;
                }
                case TRANSACTION_getService: {
                    data.enforceInterface(descriptor);
                    String _arg0;
                    _arg0 = data.readString();
                    android.os.IBinder _result = this.getService(_arg0);
                    reply.writeNoException();
                    reply.writeStrongBinder(_result);
                    return true;
                }
                case TRANSACTION_checkService: {
                    data.enforceInterface(descriptor);
                    String _arg0;
                    _arg0 = data.readString();
                    android.os.IBinder _result = this.checkService(_arg0);
                    reply.writeNoException();
                    reply.writeStrongBinder(_result);
                    return true;
                }
                case TRANSACTION_addService: {
                    data.enforceInterface(descriptor);
                    String _arg0;
                    _arg0 = data.readString();
                    android.os.IBinder _arg1;
                    _arg1 = data.readStrongBinder();
                    boolean _arg2;
                    _arg2 = (0 != data.readInt());
                    int _arg3;
                    _arg3 = data.readInt();
                    this.addService(_arg0, _arg1, _arg2, _arg3);
                    reply.writeNoException();
                    return true;
                }
                case TRANSACTION_listServices: {
                    data.enforceInterface(descriptor);
                    int _arg0;
                    _arg0 = data.readInt();
                    String[] _result = this.listServices(_arg0);
                    reply.writeNoException();
                    reply.writeStringArray(_result);
                    return true;
                }
                default: {
                    return super.onTransact(code, data, reply, flags);
                }
            }
        }

        private static class Proxy implements IServiceManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder remote) {
                mRemote = remote;
            }

            @Override
            public android.os.IBinder asBinder() {
                return mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            /**
             * Retrieve an existing service called @a name from the
             * service manager.  Blocks for a few seconds waiting for it to be
             * published if it does not already exist.
             */
            @Override
            public android.os.IBinder getService(String name) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                android.os.IBinder _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeString(name);
                    boolean _status = mRemote.transact(Stub.TRANSACTION_getService, _data, _reply, 0);
                    if (!_status && getDefaultImpl() != null) {
                        return getDefaultImpl().getService(name);
                    }
                    _reply.readException();
                    _result = _reply.readStrongBinder();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            /**
             * Retrieve an existing service called @a name from the
             * service manager.  Non-blocking.
             */
            @Override
            public android.os.IBinder checkService(String name) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                android.os.IBinder _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeString(name);
                    boolean _status = mRemote.transact(Stub.TRANSACTION_checkService, _data, _reply, 0);
                    if (!_status && getDefaultImpl() != null) {
                        return getDefaultImpl().checkService(name);
                    }
                    _reply.readException();
                    _result = _reply.readStrongBinder();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            /**
             * Place a new @a service called @a name into the service
             * manager.
             */
            @Override
            public void addService(String name, android.os.IBinder service, boolean allowIsolated, int dumpFlags) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeStrongBinder(service);
                    _data.writeInt(((allowIsolated) ? (1) : (0)));
                    _data.writeInt(dumpFlags);
                    boolean _status = mRemote.transact(Stub.TRANSACTION_addService, _data, _reply, 0);
                    if (!_status && getDefaultImpl() != null) {
                        getDefaultImpl().addService(name, service, allowIsolated, dumpFlags);
                        return;
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            /**
             * Return a list of all currently running services.
             */
            @Override
            public String[] listServices(int dumpFlags) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                String[] _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(dumpFlags);
                    boolean _status = mRemote.transact(Stub.TRANSACTION_listServices, _data, _reply, 0);
                    if (!_status && getDefaultImpl() != null) {
                        return getDefaultImpl().listServices(dumpFlags);
                    }
                    _reply.readException();
                    _result = _reply.createStringArray();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            public static IServiceManager sDefaultImpl;
        }

        static final int TRANSACTION_getService = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
        static final int TRANSACTION_checkService = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
        static final int TRANSACTION_addService = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
        static final int TRANSACTION_listServices = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);

        public static boolean setDefaultImpl(IServiceManager impl) {
            if (Stub.Proxy.sDefaultImpl == null && impl != null) {
                Stub.Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IServiceManager getDefaultImpl() {
            return Stub.Proxy.sDefaultImpl;
        }
    }

    /**
     * Retrieve an existing service called @a name from the
     * service manager.  Blocks for a few seconds waiting for it to be
     * published if it does not already exist.
     */
    public android.os.IBinder getService(String name) throws android.os.RemoteException;

    /**
     * Retrieve an existing service called @a name from the
     * service manager.  Non-blocking.
     */
    public android.os.IBinder checkService(String name) throws android.os.RemoteException;

    /**
     * Place a new @a service called @a name into the service
     * manager.
     */
    public void addService(String name, android.os.IBinder service, boolean allowIsolated, int dumpFlags) throws android.os.RemoteException;

    /**
     * Return a list of all currently running services.
     */
    public String[] listServices(int dumpFlags) throws android.os.RemoteException;

    int DUMP_FLAG_PRIORITY_DEFAULT = 1 << 3;
}
