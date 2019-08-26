package com.aszoke.assignment.issuesubmitter.csv;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class RegexBasedFilteringCsvReaderDecoratorTest {

    @Mock
    private CsvReader csvReader;
    @Mock
    private Pattern pattern;

    @InjectMocks
    private RegexBasedFilteringCsvReaderDecorator underTest;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Ignore("java.util.regex.Pattern is final therefore cannot be mocked.")
    @Test
    public void test() {
        String matched = "matched";
        String unmatched = "unmatched";
        ArrayList<String> lines = new ArrayList<>();
        lines.add(matched);
        lines.add(unmatched);
        when(csvReader.readLines()).thenReturn(lines);
        Matcher matcherTrue = mock(Matcher.class);
        when(matcherTrue.matches()).thenReturn(true);
        when(pattern.matcher(matched)).thenReturn(matcherTrue);
        Matcher matcherFalse = mock(Matcher.class);
        when(matcherFalse.matches()).thenReturn(false);
        when(pattern.matcher(unmatched)).thenReturn(matcherFalse);

        List<String> actual = underTest.readLines();

        verify(csvReader).readLines();
        verify(pattern.matcher(matched));
        verify(matcherTrue.matches());
        verify(pattern.matcher(unmatched));
        verify(matcherFalse.matches());
        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertEquals(matched, actual.get(0));
    }
}