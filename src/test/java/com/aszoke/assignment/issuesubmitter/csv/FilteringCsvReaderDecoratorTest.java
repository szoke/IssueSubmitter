package com.aszoke.assignment.issuesubmitter.csv;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class FilteringCsvReaderDecoratorTest {

    private FilteringCsvReaderDecorator underTest;

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldThrowExceptionWhenCsvReaderIsNull() {
        createUnderTest(null, Collections.emptyList());
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldThrowExceptionWhenFiltersIsNull() {
        createUnderTest(mock(CsvReader.class), null);
    }

    @Test
    public void testReadLinesShouldReturnEmptyListWhenDecoratedReaderReturnsNoLinesAndThereAreNoFilters() {
        CsvReader csvReader = mock(CsvReader.class);
        when(csvReader.readLines()).thenReturn(Collections.emptyList());
        FilteringCsvReaderDecorator underTest = createUnderTest(csvReader, Collections.emptyList());

        List<String> actual = underTest.readLines();

        assertNotNull(actual);
        assertEquals(0, actual.size());
    }

    @Test
    public void testReadLinesShouldReturnEmptyListWhenDecoratedReaderReturnsNoLinesAndNoneOfTheFiltersMatch() {
        CsvReader csvReader = mock(CsvReader.class);
        when(csvReader.readLines()).thenReturn(Collections.emptyList());
        Filter filter1 = mock(Filter.class);
        Filter filter2 = mock(Filter.class);
        when(filter1.matches(anyString())).thenReturn(false);
        when(filter2.matches(anyString())).thenReturn(false);
        List<Filter> filters = createFilters(filter1, filter2);
        FilteringCsvReaderDecorator underTest = createUnderTest(csvReader, filters);

        List<String> actual = underTest.readLines();

        verifyZeroInteractions(filter1);
        verifyZeroInteractions(filter2);
        assertNotNull(actual);
        assertEquals(0, actual.size());
    }

    @Test
    public void testReadLinesShouldReturnEmptyListWhenDecoratedReaderReturnsNoLinesAndSomeFiltersMatch() {
        CsvReader csvReader = mock(CsvReader.class);
        when(csvReader.readLines()).thenReturn(Collections.emptyList());
        Filter filter1 = mock(Filter.class);
        Filter filter2 = mock(Filter.class);
        when(filter1.matches(anyString())).thenReturn(true);
        when(filter2.matches(anyString())).thenReturn(false);
        List<Filter> filters = createFilters(filter1, filter2);
        FilteringCsvReaderDecorator underTest = createUnderTest(csvReader, filters);

        List<String> actual = underTest.readLines();

        verifyZeroInteractions(filter1);
        verifyZeroInteractions(filter2);
        assertNotNull(actual);
        assertEquals(0, actual.size());
    }

    @Test
    public void testReadLinesShouldReturnEmptyListWhenDecoratedReaderReturnsNoLinesAndAllFiltersMatch() {
        CsvReader csvReader = mock(CsvReader.class);
        when(csvReader.readLines()).thenReturn(Collections.emptyList());
        Filter filter1 = mock(Filter.class);
        Filter filter2 = mock(Filter.class);
        when(filter1.matches(anyString())).thenReturn(true);
        when(filter2.matches(anyString())).thenReturn(true);
        List<Filter> filters = createFilters(filter1, filter2);
        FilteringCsvReaderDecorator underTest = createUnderTest(csvReader, filters);

        List<String> actual = underTest.readLines();

        verifyZeroInteractions(filter1);
        verifyZeroInteractions(filter2);
        assertNotNull(actual);
        assertEquals(0, actual.size());
    }

    @Test
    public void testReadLinesShouldReturnEmptyListWhenDecoratedReaderReturnsSomeLinesAndThereAreNoFilters() {
        CsvReader csvReader = mock(CsvReader.class);
        String line1 = "line1";
        String line2 = "line2";
        List<String> lines = createLines(line1, line2);
        when(csvReader.readLines()).thenReturn(lines);
        FilteringCsvReaderDecorator underTest = createUnderTest(csvReader, Collections.emptyList());

        List<String> actual = underTest.readLines();

        assertNotNull(actual);
        assertEquals(2, actual.size());
        assertEquals(line1, actual.get(0));
        assertEquals(line2, actual.get(1));
    }

    @Test
    public void testReadLinesShouldReturnEmptyListWhenDecoratedReaderReturnsSomeLinesAndNoneOfTheFiltersMatch() {
        CsvReader csvReader = mock(CsvReader.class);
        String line1 = "line1";
        String line2 = "line2";
        List<String> lines = createLines(line1, line2);
        when(csvReader.readLines()).thenReturn(lines);
        Filter filter1 = mock(Filter.class);
        Filter filter2 = mock(Filter.class);
        when(filter1.matches(anyString())).thenReturn(false);
        when(filter2.matches(anyString())).thenReturn(false);
        List<Filter> filters = createFilters(filter1, filter2);
        FilteringCsvReaderDecorator underTest = createUnderTest(csvReader, filters);

        List<String> actual = underTest.readLines();

        verify(filter1).matches(line1);
        verify(filter1).matches(line2);
        verifyZeroInteractions(filter2);
        assertNotNull(actual);
        assertEquals(0, actual.size());
    }

    @Test
    public void testReadLinesShouldReturnEmptyListWhenDecoratedReaderReturnsSomeLinesAndSomeFiltersMatch() {
        CsvReader csvReader = mock(CsvReader.class);
        String line1 = "line1";
        String line2 = "line2";
        List<String> lines = createLines(line1, line2);
        when(csvReader.readLines()).thenReturn(lines);
        Filter filter1 = mock(Filter.class);
        Filter filter2 = mock(Filter.class);
        when(filter1.matches(anyString())).thenReturn(true);
        when(filter2.matches(anyString())).thenReturn(false);
        List<Filter> filters = createFilters(filter1, filter2);
        FilteringCsvReaderDecorator underTest = createUnderTest(csvReader, filters);

        List<String> actual = underTest.readLines();

        verify(filter1).matches(line1);
        verify(filter2).matches(line1);
        verify(filter1).matches(line2);
        verify(filter2).matches(line2);
        assertNotNull(actual);
        assertEquals(0, actual.size());
    }

    @Test
    public void testReadLinesShouldReturnEmptyListWhenDecoratedReaderReturnsSomeLinesAndAllFiltersMatch() {
        CsvReader csvReader = mock(CsvReader.class);
        String line1 = "line1";
        String line2 = "line2";
        List<String> lines = createLines(line1, line2);
        when(csvReader.readLines()).thenReturn(lines);
        Filter filter1 = mock(Filter.class);
        Filter filter2 = mock(Filter.class);
        when(filter1.matches(anyString())).thenReturn(true);
        when(filter2.matches(anyString())).thenReturn(true);
        List<Filter> filters = createFilters(filter1, filter2);
        FilteringCsvReaderDecorator underTest = createUnderTest(csvReader, filters);

        List<String> actual = underTest.readLines();

        verify(filter1).matches(line1);
        verify(filter2).matches(line1);
        verify(filter1).matches(line2);
        verify(filter2).matches(line2);
        assertNotNull(actual);
        assertEquals(2, actual.size());
        assertEquals(line1, actual.get(0));
        assertEquals(line2, actual.get(1));
    }

    private FilteringCsvReaderDecorator createUnderTest(CsvReader csvReader, Collection<Filter> filters) {
        return new FilteringCsvReaderDecorator(csvReader, filters);
    }

    private List<String> createLines(String... lines) {
        return Arrays.asList(lines);
    }

    private List<Filter> createFilters(Filter... filters) {
        return Arrays.asList(filters);
    }
}