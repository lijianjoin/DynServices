package com.dynsers.remoteservice.utils;

import com.dynsers.remoteservice.exceptions.RemoteServiceRequestParameterSerializableException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class TestSerializableConverterUtils {

    @Test
    void convertObjectArrayToSerializableArray_withAllSerializableElements_shouldReturnSerializableArray() {
        Object[] input = {1, "test", 3.14};
        Serializable[] result = SerializableConverterUtils.convertObjectArrayToSerializableArray(input);
        Assertions.assertEquals(3, result.length);
    }

    @Test
    void convertObjectArrayToSerializableArray_withNonSerializableElement_shouldThrowException() {
        Object[] input = {1, new Object(), "test"};
        Assertions.assertThrows(
                RemoteServiceRequestParameterSerializableException.class,
                () -> SerializableConverterUtils.convertObjectArrayToSerializableArray(input));
    }

    @Test
    void convertObjectArrayToSerializableArray_withEmptyArray_shouldReturnEmptyArray() {
        Object[] input = {};
        Serializable[] result = SerializableConverterUtils.convertObjectArrayToSerializableArray(input);
        Assertions.assertEquals(0, result.length);
    }

    @Test
    void convertObjectToSerializable_withSerializableObject_shouldReturnSerializable()
            throws RemoteServiceRequestParameterSerializableException {
        String input = "test";
        Serializable result = SerializableConverterUtils.convertObjectToSerializable(input);
        assertInstanceOf(String.class, result);
    }

    @Test
    void convertObjectToSerializable_withNullObject_shouldReturnNull()
            throws RemoteServiceRequestParameterSerializableException {
        Object input = null;
        Serializable result = SerializableConverterUtils.convertObjectToSerializable(input);
        Assertions.assertNull(result);
    }

    @Test
    void convertObjectToSerializable_withNonSerializableObject_shouldThrowException() {
        Object input = new Object();
        Assertions.assertThrows(
                RemoteServiceRequestParameterSerializableException.class,
                () -> SerializableConverterUtils.convertObjectToSerializable(input));
    }

    @Test
    void testConvertObjectArrayToSerializableArray_withNullInput() {
        Object[] input = null;
        Serializable[] res = SerializableConverterUtils.convertObjectArrayToSerializableArray(input);
        assertEquals(0, res.length);
    }
}