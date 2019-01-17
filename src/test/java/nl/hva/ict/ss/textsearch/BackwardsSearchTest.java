package nl.hva.ict.ss.textsearch;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BackwardsSearchTest {
    protected BackwardsSearch searchEngine;
    String pattern = "needle";
    String textMoreLeft = "neeedeeeelnneeeldleneedllllleeewhereistheneedleinthishaystackneedllllleee";
    String textMoreRight = "needllllleeewhereistheneedleinthishaystackneedllllleee";
    String text = "whereistheneedleinthishaystack";
    String textFail = "thereisnothinginthishaystack";

    @Before
    public void setup() {
        searchEngine = new BackwardsSearch();
    }

    @Test
    public void findSingleOccurrenceRight() {
        int index = searchEngine.findLocationViaRight(pattern, text);
        assertEquals("whereisthe".length(), index);
    }

    @Test
    public void cantFindOccurrenceRight() {
        int index = searchEngine.findLocationViaRight(pattern, textFail);
        assertEquals(textFail.length(), index);
    }
    
    
    @Test
    public void findSingleOccurrenceLeft() {
        int index = searchEngine.findLocationViaLeft(pattern, text);
        assertEquals("whereisthe".length(), index);
    }

    @Test
    public void cantFindOccurrenceLeft() {
        int index = searchEngine.findLocationViaLeft(pattern, textFail);
        assertEquals(textFail.length(), index);
    }
        
    @Test
    public void leftSearchMoreThanRight() {
        int indexLeft = searchEngine.findLocationViaLeft(pattern, textMoreLeft);
        int indexRight = searchEngine.findLocationViaRight(pattern, textMoreLeft);
        int countLeft = searchEngine.getComparisonsForLastSearchLeft();
        int countRight = searchEngine.getComparisonsForLastSearchRight();
        System.out.println("LEFT RIGHT: " + countLeft + " | " + countRight);
        assertTrue(countLeft > countRight);        
    }
    
    @Test
    public void rightSearchMoreThanLeft() {
        int indexLeft = searchEngine.findLocationViaLeft(pattern, textMoreRight);
        int indexRight = searchEngine.findLocationViaRight(pattern, textMoreRight);
        int countLeft = searchEngine.getComparisonsForLastSearchLeft();
        int countRight = searchEngine.getComparisonsForLastSearchRight();
        System.out.println("LEFT RIGHT: " + countLeft + " | " + countRight);
        assertTrue(countLeft < countRight);        
    }

    @Test
    public void equalCount() {
        int indexLeft = searchEngine.findLocationViaLeft(pattern, text);
        int indexRight = searchEngine.findLocationViaRight(pattern, text);
        int countLeft = searchEngine.getComparisonsForLastSearchLeft();
        int countRight = searchEngine.getComparisonsForLastSearchRight();
        System.out.println("LEFT RIGHT: " + countLeft + " | " + countRight);
        assertEquals(countLeft, countRight);        
    }    
}