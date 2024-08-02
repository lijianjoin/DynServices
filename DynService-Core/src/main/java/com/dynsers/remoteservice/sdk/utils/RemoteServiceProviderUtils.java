package com.dynsers.remoteservice.sdk.utils;


import com.dynsers.remoteservice.sdk.data.RemoteServiceId;

public class RemoteServiceProviderUtils {

    private RemoteServiceProviderUtils() {
    }

    public static boolean isIdenticalLocation(RemoteServiceId location1, RemoteServiceId location2) {
        if (location1 == location2) return true;
        if (location1.getGroupId() != null ? !location1.getGroupId().equals(location2.getGroupId()) : location2.getGroupId() != null)
            return false;
        if (location1.getResourceId() != null ? !location1.getResourceId().equals(location2.getResourceId()) : location2.getResourceId() != null)
            return false;
        return (location1.getResourceVersion() != null ? location1.getResourceVersion().equals(location2.getResourceVersion()) : location2.getResourceVersion() == null);
    }
}
