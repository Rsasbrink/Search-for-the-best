package nl.hva.ict.ss.textsearch;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

public final class LanguageDetector {

    private String content; // Once an instance is created this will hold the complete content of the file.
    HashMap<Character, Integer> letterFrequency = new HashMap<>();

    public LanguageDetector(InputStream input) {
        Scanner sc = new Scanner(input);
        sc.useDelimiter("\\Z"); // EOF marker
        content = sc.next();

    }

    public boolean isEnglish() {
        content = convertToLowerCase(content);
        getLetterFrequency();
        System.out.println(letterFrequency);
        return true;
    }

    public void getLetterFrequency() {
        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            if (Character.isLetter(c)) {
                Integer val = letterFrequency.get(c);
                if (val != null) {
                    letterFrequency.put(c, new Integer(val + 1));
                } else {
                    letterFrequency.put(c, 1);
                }
            }

        }

    }

    public String convertToLowerCase(String content) {
        String lowerContent;
        lowerContent = content.toLowerCase();
        return lowerContent;
    }

    // Put your own code here and integrate it with the test class.
}
