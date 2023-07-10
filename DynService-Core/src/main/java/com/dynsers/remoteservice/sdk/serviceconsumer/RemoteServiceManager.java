package com.dynsers.remoteservice.sdk.serviceconsumer;

import com.dynsers.remoteservice.sdk.data.RemoteServiceId;
import com.dynsers.remoteservice.sdk.annotations.RemoteService;
import com.dynsers.remoteservice.sdk.exceptions.RSFieldIsNotRemoteServiceException;
import com.dynsers.remoteservice.sdk.interfaces.RemoteServiceRegister;
import io.micrometer.common.util.StringUtils;

import java.lang.reflect.Field;


public class RemoteServiceManager {

    private static RemoteServiceManager instance;

    @RemoteService(groupId = "${remoteService.server.groupId}",
            resourceId = "${remoteService.server.resourceId}",
            resourceVersion = "${remoteService.server.resourceVersion}",
            serviceVersion = "${remoteService.server.serviceVersion}",
            url = "${remoteService.server.url}")
    private RemoteServiceRegister remoteServiceRegister;

    public static RemoteServiceManager getInstance() {
        RemoteServiceManager localRef = instance;
        if (localRef == null) {
            synchronized (RemoteServiceManager.class) {
                localRef = instance;
                if (localRef == null) {
                    instance = localRef = new RemoteServiceManager();
                    try {
                        injectRemoteService(localRef);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return localRef;
    }

    private static void injectRemoteService(Object obj) throws IllegalAccessException {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            RemoteServiceProxy.setField(obj, field);
        }
    }

    /**
     * Configure all the interface which type is cls to use the serviceId
     *
     * @param owner
     * @param cls
     * @param serviceId
     */
    public void configRemoteService(Object owner, Class<?> cls, RemoteServiceId serviceId) {

    }

    /**
     * Configure the parameter, whose name is paramName to use the serivceId
     *
     * @param owner
     * @param paramName
     * @param serviceId
     */
    public void configRemoteService(Object owner, String paramName, RemoteServiceId serviceId) throws NoSuchFieldException, IllegalAccessException {
        if(StringUtils.isEmpty(serviceId.getUri())) {
            RemoteServiceId requestId = remoteServiceRegister.getRemoteServiceId(serviceId);
            serviceId.setUri(requestId.getUri());
        }
        RemoteServiceInvocationHandler handler = RemoteServiceInvocationHandlerPool.getInvocationHandler(owner, serviceId);
        if(null!= handler) {
            RemoteServiceId rsId = RemoteServiceContainer.getRemoteServiceId(owner, paramName);
            handler.updateRemoteService(serviceId);
            RemoteServiceContainer.storeServiceProviderId(owner, paramName, serviceId);
        }
        else {
            Field field = owner.getClass().getDeclaredField(paramName);
            if (field.isAnnotationPresent(RemoteService.class)) {
                RemoteServiceProxy.setField(owner, field, serviceId);
                RemoteServiceContainer.storeServiceProviderId(owner, paramName, serviceId);
            } else {
                throw new RSFieldIsNotRemoteServiceException(
                        String.format("%s's Parameter: %s is Not Remote Service", owner.getClass(), paramName
                        ));
            }
        }
    }
//    public void setRemoteServiceProvider(Class<?> cls, RemoteServiceId requestId) {

//    }


//    public RemoteServiceId getRemoteServiceProvider(RemoteServiceId requestId) {

//    }


//    public List<RemoteServiceId> getRemoteServiceProviders(Class<?> bean) {

//    }

//    public void setRemoteServiceProviders(Class<?> inter, RemoteServiceId) {

//    }
}
