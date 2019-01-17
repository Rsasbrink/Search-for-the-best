package nl.hva.ict.ss.textsearch;

import java.io.InputStream;
import java.util.Scanner;

public class LanguageDetector {
    private String content; // Once an instance is created this will hold the complete content of the file.

    public LanguageDetector(InputStream input) {
        Scanner sc = new Scanner(input);
        sc.useDelimiter("\\Z"); // EOF marker
        content = sc.next();
    }

    // Put your own code here and integrate it with the test class.
}
