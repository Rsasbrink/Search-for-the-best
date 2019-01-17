package nl.hva.ict.ss.textsearch;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BackwardsSearchTest {
    protected BackwardsSearch searchEngine;

    @Before
    public void setup() {
        searchEngine = new BackwardsSearch();
    }

    @Test
    public void findSingleOccurrence() {
        int index = searchEngine.findLocation("needle", "whereistheneedleinthishaystack");

        assertEquals("whereisthe".length(), index);
    }

    @Test
    public void cantFindOccurrence() {
        int index = searchEngine.findLocation("needle", "thereisnothinginthishaystack");

        assertEquals(-1, index);
    }
}