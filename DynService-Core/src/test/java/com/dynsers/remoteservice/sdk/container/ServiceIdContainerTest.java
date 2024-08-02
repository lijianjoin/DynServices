package com.dynsers.remoteservice.sdk.container;

import com.dynsers.remoteservice.sdk.data.RemoteServiceId;
import com.dynsers.remoteservice.sdk.exceptions.RemoteServiceServiceAlreadyRegisterException;
import com.dynsers.remoteservice.sdk.exceptions.RemoteServiceServiceNotRegisterException;
import com.dynsers.remoteservice.sdk.utils.RemoteServiceServiceIdUtils;
import org.awaitility.Awaitility;
import org.awaitility.Durations;
import org.junit.jupiter.api.Test;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ServiceIdContainerTest {

    private RemoteServiceId getTestServiceId() {
        String uuid = String.valueOf(UUID.randomUUID());
        RemoteServiceId serviceId = new RemoteServiceId();
        serviceId
                .setGroupId("com.sartorius.dynservice")
                .setResourceId("demo")
                .setResourceVersion("0.0.1")
                .setServiceId("com.sartorius.dynservice.demo.service.TestService")
                .setServiceVersion("0.0.1")
                .setServiceName("testService")
                .setUri("localhost:8888/demo")
                .setUuid(uuid);
        return serviceId;
    }

    private RemoteServiceId getTestServiceRequestId() {

        RemoteServiceId serviceIdRequest = new RemoteServiceId();
        serviceIdRequest.setGroupId("com.sartorius.dynservice");
        serviceIdRequest.setResourceId("demo1");
        serviceIdRequest.setResourceVersion("0.0.1");
        serviceIdRequest.setServiceId("com.sartorius.dynservice.demo.service.TestService");
        serviceIdRequest.setServiceVersion("0.0.1");
        serviceIdRequest.setServiceName("testService");
        serviceIdRequest.setUuid("uuid");
        return serviceIdRequest;
    }

    @Test
    void testStoreServiceIdWithAlreadyRegisterException() {
        ServiceIdContainer container = new ServiceIdContainer();
        String uuid = String.valueOf(UUID.randomUUID());
        RemoteServiceId serviceId = getTestServiceId();

        container.storeServiceId(serviceId);

        Exception exception = assertThrows(RemoteServiceServiceAlreadyRegisterException.class, () -> {
            container.storeServiceId(serviceId);
        });
        String excepMsg = RemoteServiceServiceIdUtils.getServiceIdAsPlainString(serviceId);
        assertEquals(excepMsg, exception.getMessage());
    }

    @Test
    void testStoreServiceId() {
        ServiceIdContainer container = new ServiceIdContainer();
        String uuid = String.valueOf(UUID.randomUUID());
        RemoteServiceId serviceId = getTestServiceId();

        container.storeServiceId(serviceId);

        RemoteServiceId stored = container.getRemoteService(serviceId);
        assertEquals(serviceId.getUri(), stored.getUri());
    }

    @Test
    void testDeleteServiceId() {
        ServiceIdContainer container = new ServiceIdContainer();
        RemoteServiceId serviceId = getTestServiceId();
        container.storeServiceId(serviceId);

        String uuid = String.valueOf(UUID.randomUUID());
        RemoteServiceId serviceIdRequest = getTestServiceRequestId();
        serviceIdRequest.setUuid(uuid);

        container.storeServiceId(serviceIdRequest);

        container.deleteServiceId(serviceId);

        Exception exception = assertThrows(RemoteServiceServiceNotRegisterException.class, () -> {
            container.getRemoteService(serviceId);
        });
        String excepMsg = RemoteServiceServiceIdUtils.getServiceIdAsPlainString(serviceId);
        assertEquals(excepMsg, exception.getMessage());
    }

    @Test
    void testGetIdWithWrongGroupNotRegisterException() {
        ServiceIdContainer container = new ServiceIdContainer();
        String uuid = String.valueOf(UUID.randomUUID());
        RemoteServiceId serviceId = getTestServiceId();
        serviceId.setUuid(uuid);

        container.storeServiceId(serviceId);

        RemoteServiceId serviceIdRequest = getTestServiceRequestId();

        Exception exception = assertThrows(RemoteServiceServiceNotRegisterException.class, () -> {
            container.getRemoteService(serviceIdRequest);
        });
        String excepMsg = RemoteServiceServiceIdUtils.getServiceIdAsPlainString(serviceIdRequest);
        assertEquals(excepMsg, exception.getMessage());
    }

    @Test
    void testGetIdWithWrongServiceNotRegisterException() {
        ServiceIdContainer container = new ServiceIdContainer();
        String uuid = String.valueOf(UUID.randomUUID());
        RemoteServiceId serviceId = getTestServiceId();
        serviceId.setUuid(uuid);

        container.storeServiceId(serviceId);

        RemoteServiceId serviceIdRequest = getTestServiceRequestId();
        serviceIdRequest.setServiceId("com.sartorius.dynservice.demo.service.TestServiceddd");

        Exception exception = assertThrows(RemoteServiceServiceNotRegisterException.class, () -> {
            container.getRemoteService(serviceIdRequest);
        });
        String excepMsg = RemoteServiceServiceIdUtils.getServiceIdAsPlainString(serviceIdRequest);
        assertEquals(excepMsg, exception.getMessage());
    }

    @Test
    void testGetIdWithWrongUUIDStillGetService() {
        ServiceIdContainer container = new ServiceIdContainer();
        RemoteServiceId serviceId = getTestServiceId();

        container.storeServiceId(serviceId);

        RemoteServiceId serviceIdRequest = getTestServiceRequestId();

        serviceIdRequest.setUuid("uuid");
        serviceIdRequest.setResourceId("demo");
        RemoteServiceId rsId = container.getRemoteService(serviceIdRequest);

        assertNotNull(rsId);
    }

    @Test
    void testLockByContainer() throws NoSuchFieldException, IllegalAccessException {
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
                RemoteServiceId serviceId = getTestServiceId();
                container.storeServiceId(serviceId);
                endTime[0] = System.currentTimeMillis();
            }
        };

        Runnable lockT = new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    Awaitility.await().pollDelay(Durations.TWO_SECONDS).until(() -> true);
                }
            }
        };
        try (ExecutorService executor = Executors.newFixedThreadPool(2)) {
            executor.execute(lockT);
            executor.execute(t1);
            executor.shutdown();
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                throw new RuntimeException("timeout exceeded waiting for executor shutdown.");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        long executeTime = endTime[0] - startTime[0];
        assertTrue(executeTime > 1000);
    }

    @Test
    void testLockByResourceContainer() throws NoSuchFieldException, IllegalAccessException {
        ServiceIdContainer container = new ServiceIdContainer();
        int number = 0;
        Field internalContainerField = container.getClass().getDeclaredField("container");
        ReflectionUtils.makeAccessible(internalContainerField);
        final long[] startTime = {0};
        final long[] endTime = {0};
        GroupServiceContainer internalContainer = (GroupServiceContainer) internalContainerField.get(container);
        RemoteServiceId serviceId = getTestServiceId();
        container.storeServiceId(serviceId);

        serviceId.setServiceId("com.sartorius.dynservice.demo.service.TestService2");
        String groupKey = RemoteServiceServiceIdUtils.getGroupResourceKey(serviceId);
        Object lock = internalContainer.getResourceServices(groupKey);
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
                    Awaitility.await().pollDelay(Durations.TWO_SECONDS).until(() -> true);
                }
            }
        };
        try (ExecutorService executor = Executors.newFixedThreadPool(2)) {
            executor.execute(lockT);
            executor.execute(t1);
            executor.shutdown();
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                throw new RuntimeException("timeout exceeded waiting for executor shutdown.");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        long executeTime = endTime[0] - startTime[0];
        assertTrue(executeTime > 1000);
    }

    @Test
    void testLockByServiceIdContainer() throws NoSuchFieldException, IllegalAccessException {
        ServiceIdContainer container = new ServiceIdContainer();
        int number = 0;
        Field internalContainerField = container.getClass().getDeclaredField("container");
        ReflectionUtils.makeAccessible(internalContainerField);
        final long[] startTime = {0};
        final long[] endTime = {0};
        GroupServiceContainer internalContainer = (GroupServiceContainer) internalContainerField.get(container);
        RemoteServiceId serviceId = getTestServiceId();
        container.storeServiceId(serviceId);
        ResourceServiceContainer resourceContainer = internalContainer.getResourceServices(serviceId);
        ServiceNameContainer lock = resourceContainer.getServices(serviceId);
        serviceId.setUuid(String.valueOf(UUID.randomUUID()));
        Runnable t1 = () -> {
            startTime[0] = System.currentTimeMillis();
            container.storeServiceId(serviceId);
            endTime[0] = System.currentTimeMillis();
        };

        Runnable lockT = () -> {
            synchronized (lock) {
                Awaitility.await().pollDelay(Durations.TWO_SECONDS).until(() -> true);
            }
        };
        try (ExecutorService executor = Executors.newFixedThreadPool(2)) {
            executor.execute(lockT);
            executor.execute(t1);
            executor.shutdown();
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                throw new RuntimeException("timeout exceeded waiting for executor shutdown.");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        long executeTime = endTime[0] - startTime[0];
        assertTrue(executeTime > 1000);
    }

}
