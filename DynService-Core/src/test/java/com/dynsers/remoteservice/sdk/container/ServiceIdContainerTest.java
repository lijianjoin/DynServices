package com.dynsers.remoteservice.sdk.container;

import com.dynsers.remoteservice.sdk.exceptions.RSServiceAlreadyRegisterException;
import com.dynsers.remoteservice.sdk.exceptions.RSServiceNotFoundException;
import com.dynsers.remoteservice.sdk.exceptions.RSServiceNotRegisterException;
import com.dynsers.remoteservice.sdk.utils.RSServiceIdUtils;
import com.dynsers.remoteservice.sdk.data.RemoteServiceId;
import org.junit.jupiter.api.Test;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceIdContainerTest {



    @Test
    public void testStoreServiceIdWithAlreadyRegisterException() {
        ServiceIdContainer container = new ServiceIdContainer();
        String uuid = String.valueOf(UUID.randomUUID());
        RemoteServiceId serviceId = new RemoteServiceId();
        serviceId.setGroupId("com.dynsers.dynservice");
        serviceId.setResourceId("demo");
        serviceId.setResourceVersion("0.0.1");
        serviceId.setServiceId("com.dynsers.dynservice.demo.service.TestService");
        serviceId.setServiceVersion("0.0.1");
        serviceId.setUri("localhost:8888/demo");
        serviceId.setUuid(uuid);

        container.storeServiceId(serviceId);

        Exception exception = assertThrows(RSServiceAlreadyRegisterException.class, () -> {
            container.storeServiceId(serviceId);
        });
        String excepMsg = RSServiceIdUtils.getServiceIdAsPlainString(serviceId);
        assertEquals(excepMsg, exception.getMessage());

    }

    @Test
    public void testStoreServiceId() {
        ServiceIdContainer container = new ServiceIdContainer();
        String uuid = String.valueOf(UUID.randomUUID());
        RemoteServiceId serviceId = new RemoteServiceId();
        serviceId.setGroupId("com.dynsers.dynservice");
        serviceId.setResourceId("demo");
        serviceId.setResourceVersion("0.0.1");
        serviceId.setServiceId("com.dynsers.dynservice.demo.service.TestService");
        serviceId.setServiceVersion("0.0.1");
        serviceId.setUri("localhost:8888/demo");
        serviceId.setUuid(uuid);

        container.storeServiceId(serviceId);

        RemoteServiceId stored = container.getRemoteService(serviceId);
        assertEquals(serviceId.getUri(), stored.getUri());
    }

    @Test
    public void testDeleteServiceId() {
        ServiceIdContainer container = new ServiceIdContainer();
        String uuid = String.valueOf(UUID.randomUUID());
        RemoteServiceId serviceId = new RemoteServiceId();
        serviceId.setGroupId("com.dynsers.dynservice");
        serviceId.setResourceId("demo");
        serviceId.setResourceVersion("0.0.1");
        serviceId.setServiceId("com.dynsers.dynservice.demo.service.TestService");
        serviceId.setServiceVersion("0.0.1");
        serviceId.setUri("localhost:8888/demo");
        serviceId.setUuid(uuid);

        container.storeServiceId(serviceId);

        uuid = String.valueOf(UUID.randomUUID());
        RemoteServiceId serviceIdRequest = new RemoteServiceId();
        serviceIdRequest.setGroupId("com.dynsers.dynservicessss");
        serviceIdRequest.setResourceId("demo");
        serviceIdRequest.setResourceVersion("0.0.1");
        serviceIdRequest.setServiceId("com.dynsers.dynservice.demo.service.TestService");
        serviceIdRequest.setServiceVersion("0.0.1");
        serviceIdRequest.setUuid(uuid);

        container.storeServiceId(serviceIdRequest);

        container.deleteServiceId(serviceId);

        Exception exception = assertThrows(RSServiceNotRegisterException.class, () -> {
            container.getRemoteService(serviceId);
        });
        String excepMsg = RSServiceIdUtils.getServiceIdAsPlainString(serviceId);
        assertEquals(excepMsg, exception.getMessage());
    }

    @Test
    public void testGetIdWithWrongGroupNotRegisterException() {
        ServiceIdContainer container = new ServiceIdContainer();
        String uuid = String.valueOf(UUID.randomUUID());
        RemoteServiceId serviceId = new RemoteServiceId();
        serviceId.setGroupId("com.dynsers.dynservice");
        serviceId.setResourceId("demo");
        serviceId.setResourceVersion("0.0.1");
        serviceId.setServiceId("com.dynsers.dynservice.demo.service.TestService");
        serviceId.setServiceVersion("0.0.1");
        serviceId.setUri("localhost:8888/demo");
        serviceId.setUuid(uuid);

        container.storeServiceId(serviceId);

        RemoteServiceId serviceIdRequest = new RemoteServiceId();
        serviceIdRequest.setGroupId("com.dynsers.dynservicessss");
        serviceIdRequest.setResourceId("demo");
        serviceIdRequest.setResourceVersion("0.0.1");
        serviceIdRequest.setServiceId("com.dynsers.dynservice.demo.service.TestService");
        serviceIdRequest.setServiceVersion("0.0.1");
        serviceIdRequest.setUuid(uuid);

        Exception exception = assertThrows(RSServiceNotRegisterException.class, () -> {
            container.getRemoteService(serviceIdRequest);
        });
        String excepMsg = RSServiceIdUtils.getServiceIdAsPlainString(serviceIdRequest);
        assertEquals(excepMsg, exception.getMessage());
    }

    @Test
    public void testGetIdWithWrongServiceNotRegisterException() {
        ServiceIdContainer container = new ServiceIdContainer();
        String uuid = String.valueOf(UUID.randomUUID());
        RemoteServiceId serviceId = new RemoteServiceId();
        serviceId.setGroupId("com.dynsers.dynservice");
        serviceId.setResourceId("demo");
        serviceId.setResourceVersion("0.0.1");
        serviceId.setServiceId("com.dynsers.dynservice.demo.service.TestService");
        serviceId.setServiceVersion("0.0.1");
        serviceId.setUri("localhost:8888/demo");
        serviceId.setUuid(uuid);

        container.storeServiceId(serviceId);

        RemoteServiceId serviceIdRequest = new RemoteServiceId();
        serviceIdRequest.setGroupId("com.dynsers.dynservice");
        serviceIdRequest.setResourceId("demo");
        serviceIdRequest.setResourceVersion("0.0.1");
        serviceIdRequest.setServiceId("com.dynsers.dynservice.demo.service.TestServiceddd");
        serviceIdRequest.setServiceVersion("0.0.1");
        serviceIdRequest.setUuid(uuid);

        Exception exception = assertThrows(RSServiceNotRegisterException.class, () -> {
            container.getRemoteService(serviceIdRequest);
        });
        String excepMsg = RSServiceIdUtils.getServiceIdAsPlainString(serviceIdRequest);
        assertEquals(excepMsg, exception.getMessage());
    }

    @Test
    public void testGetIdWithWrongUUIDNotRegisterException() {
        ServiceIdContainer container = new ServiceIdContainer();
        String uuid = String.valueOf(UUID.randomUUID());
        RemoteServiceId serviceId = new RemoteServiceId();
        serviceId.setGroupId("com.dynsers.dynservice");
        serviceId.setResourceId("demo");
        serviceId.setResourceVersion("0.0.1");
        serviceId.setServiceId("com.dynsers.dynservice.demo.service.TestService");
        serviceId.setServiceVersion("0.0.1");
        serviceId.setUri("localhost:8888/demo");
        serviceId.setUuid(uuid);

        container.storeServiceId(serviceId);

        RemoteServiceId serviceIdRequest = new RemoteServiceId();
        serviceIdRequest.setGroupId("com.dynsers.dynservice");
        serviceIdRequest.setResourceId("demo");
        serviceIdRequest.setResourceVersion("0.0.1");
        serviceIdRequest.setServiceId("com.dynsers.dynservice.demo.service.TestService");
        serviceIdRequest.setServiceVersion("0.0.1");
        serviceIdRequest.setUuid("uuid");

        Exception exception = assertThrows(RSServiceNotRegisterException.class, () -> {
            container.getRemoteService(serviceIdRequest);
        });
        String excepMsg = RSServiceIdUtils.getServiceIdAsPlainString(serviceIdRequest);
        assertEquals(excepMsg, exception.getMessage());
    }

    @Test
    public void testLockByContainer() throws NoSuchFieldException, IllegalAccessException {
        ServiceIdContainer container = new ServiceIdContainer();
        int number = 0;
        Field internalContainer = container.getClass().getDeclaredField("container");
        ReflectionUtils.makeAccessible(internalContainer);
        final long[] startTime = {0};
        final long[] endTime = {0};
        Object lock = internalContainer.get(container);
        Runnable t1 = new Runnable() {
            @Override
            public void run() {
                startTime[0] = System.currentTimeMillis();
                String uuid = String.valueOf(UUID.randomUUID());
                RemoteServiceId serviceId = new RemoteServiceId();
                serviceId.setGroupId("com.dynsers.dynservice");
                serviceId.setResourceId("demo");
                serviceId.setResourceVersion("0.0.1");
                serviceId.setServiceId("com.dynsers.dynservice.demo.service.TestService");
                serviceId.setServiceVersion("0.0.1");
                serviceId.setUri("localhost:8888/demo");
                serviceId.setUuid(uuid);
                container.storeServiceId(serviceId);
                endTime[0] = System.currentTimeMillis();
            }
        };

        Runnable lockT = new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(lockT);
        executor.execute(t1);
        executor.shutdown();
        while (!executor.isTerminated()) {
            // empty body
        }
        long executeTime = endTime[0] - startTime[0];
        assertTrue(executeTime > 1000);
    }

    @Test
    public void testLockByResourceContainer() throws NoSuchFieldException, IllegalAccessException {
        ServiceIdContainer container = new ServiceIdContainer();
        int number = 0;
        Field internalContainerField = container.getClass().getDeclaredField("container");
        ReflectionUtils.makeAccessible(internalContainerField);
        final long[] startTime = {0};
        final long[] endTime = {0};
        Map<String, Map<String, Map<String, RemoteServiceId>>> internalContainer =
                (Map<String, Map<String, Map<String, RemoteServiceId>>>)internalContainerField.get(container);
        String uuid = String.valueOf(UUID.randomUUID());
        RemoteServiceId serviceId = new RemoteServiceId();
        serviceId.setGroupId("com.dynsers.dynservice");
        serviceId.setResourceId("demo");
        serviceId.setResourceVersion("0.0.1");
        serviceId.setServiceId("com.dynsers.dynservice.demo.service.TestService");
        serviceId.setServiceVersion("0.0.1");
        serviceId.setUri("localhost:8888/demo");
        serviceId.setUuid(uuid);
        container.storeServiceId(serviceId);

        serviceId.setServiceId("com.dynsers.dynservice.demo.service.TestService2");
        String groupKey = RSServiceIdUtils.getGroupResourceKey(serviceId);
        Object lock = internalContainer.get(groupKey);
        Runnable t1 = new Runnable() {
            @Override
            public void run() {
                startTime[0] = System.currentTimeMillis();
                container.storeServiceId(serviceId);
                endTime[0] = System.currentTimeMillis();
            }
        };

        Runnable lockT = new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(lockT);
        executor.execute(t1);
        executor.shutdown();
        while (!executor.isTerminated()) {
            // empty body
        }
        long executeTime = endTime[0] - startTime[0];
        assertTrue(executeTime > 1000);
    }

    @Test
    public void testLockByServiceIdContainer() throws NoSuchFieldException, IllegalAccessException {
        ServiceIdContainer container = new ServiceIdContainer();
        int number = 0;
        Field internalContainerField = container.getClass().getDeclaredField("container");
        ReflectionUtils.makeAccessible(internalContainerField);
        final long[] startTime = {0};
        final long[] endTime = {0};
        Map<String, Map<String, Map<String, RemoteServiceId>>> internalContainer =
                (Map<String, Map<String, Map<String, RemoteServiceId>>>)internalContainerField.get(container);
        String uuid = String.valueOf(UUID.randomUUID());
        RemoteServiceId serviceId = new RemoteServiceId();
        serviceId.setGroupId("com.dynsers.dynservice");
        serviceId.setResourceId("demo");
        serviceId.setResourceVersion("0.0.1");
        serviceId.setServiceId("com.dynsers.dynservice.demo.service.TestService");
        serviceId.setServiceVersion("0.0.1");
        serviceId.setUri("localhost:8888/demo");
        serviceId.setUuid(uuid);
        container.storeServiceId(serviceId);
        String groupKey = RSServiceIdUtils.getGroupResourceKey(serviceId);
        Map<String, Map<String, RemoteServiceId>> resourceContainer = internalContainer.get(groupKey);
        Map<String, RemoteServiceId> lock = resourceContainer.get(RSServiceIdUtils.getServiceKey(serviceId));

        serviceId.setUuid(String.valueOf(UUID.randomUUID()));
        Runnable t1 = new Runnable() {
            @Override
            public void run() {
                startTime[0] = System.currentTimeMillis();
                container.storeServiceId(serviceId);
                endTime[0] = System.currentTimeMillis();
            }
        };

        Runnable lockT = new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(lockT);
        executor.execute(t1);
        executor.shutdown();
        while (!executor.isTerminated()) {
            // empty body
        }
        long executeTime = endTime[0] - startTime[0];
        assertTrue(executeTime > 1000);
    }

}
