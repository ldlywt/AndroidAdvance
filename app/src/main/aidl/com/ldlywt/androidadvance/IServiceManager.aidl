// IServiceManager.aidl
package com.ldlywt.androidadvance;

// Declare any non-default types here with import statements

interface IServiceManager {

    /**
     * Retrieve an existing service called @a name from the
     * service manager.  Blocks for a few seconds waiting for it to be
     * published if it does not already exist.
     */
    IBinder getService(String name);

    /**
     * Retrieve an existing service called @a name from the
     * service manager.  Non-blocking.
     */
    IBinder checkService(String name);

    /**
     * Place a new @a service called @a name into the service
     * manager.
     */
    void addService(String name, IBinder service, boolean allowIsolated, int dumpFlags);

    /**
     * Return a list of all currently running services.
     */
    String[] listServices(int dumpFlags);

    /**
     * Assign a permission controller to the service manager.  After set, this
     * interface is checked before any services are added.
     */
//    void setPermissionController(IPermissionController controller);

}
