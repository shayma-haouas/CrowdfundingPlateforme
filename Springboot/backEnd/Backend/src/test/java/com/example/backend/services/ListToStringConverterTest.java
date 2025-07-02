package com.example.backend.Service;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListToStringConverterTest {

    private final ListToStringConverter converter = new ListToStringConverter();

    @Test
    void testConvertToDatabaseColumn_NullOrEmpty() {
        assertNull(converter.convertToDatabaseColumn(null));
        assertNull(converter.convertToDatabaseColumn(List.of()));
    }

    @Test
    void testConvertToDatabaseColumn_NormalList() {
        List<String> list = List.of("apple", "banana", "cherry");
        assertEquals("apple,banana,cherry", converter.convertToDatabaseColumn(list));
    }

    @Test
    void testConvertToEntityAttribute_NullOrEmpty() {
        assertTrue(converter.convertToEntityAttribute(null).isEmpty());
        assertTrue(converter.convertToEntityAttribute("").isEmpty());
    }

    @Test
    void testConvertToEntityAttribute_NormalString() {
        List<String> expected = List.of("apple", "banana", "cherry");
        assertEquals(expected, converter.convertToEntityAttribute("apple,banana,cherry"));
    }
}
