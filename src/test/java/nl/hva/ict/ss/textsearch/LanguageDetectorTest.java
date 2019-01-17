package nl.hva.ict.ss.textsearch;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class LanguageDetectorTest {
    private LanguageDetector detector;

    @Before
    public void setup() {
        detector = new LanguageDetector(getClass().getResourceAsStream("/edu/princeton/cs/algs4/Huffman.java"));
         
   }
    // Add your tests here. They are allowed to NOT use assertXxxx... :-)
    @Test
    public void cantFindOccurrence() {
     detector.isEnglish();
    }
}