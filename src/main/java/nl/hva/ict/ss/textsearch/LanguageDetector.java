package nl.hva.ict.ss.textsearch;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LanguageDetector {

    private String content; // Once an instance is created this will hold the complete content of the file.
    private String docContent = "";
    HashMap<Character, Integer> letterFrequency = new HashMap<Character, Integer>();

    public LanguageDetector(InputStream input) {
        Scanner sc = new Scanner(input);
        sc.useDelimiter("\\Z"); // EOF marker
        content = sc.next();
        sc.close();
    }

    public boolean isEnglish() {
        Scanner docScanner = new Scanner(content);
        docScanner.useDelimiter("\\Z"); // EOF marker

        while (docScanner.hasNextLine()) {
            String line = docScanner.nextLine();
            Pattern pattern = Pattern.compile("/\\/\\*|\\*\\/|\\*.*/gm");
            Matcher m = pattern.matcher(line);
            boolean b = m.matches();
            if (b) {
                docContent += line + "\n";
            }
        }
        docScanner.close();
        docContent = convertToLowerCase(docContent);
        getLetterFrequency(docContent);
        System.out.println(letterFrequency);
        System.out.println(docContent);
        return true;
    }

    public void getLetterFrequency(String stringContent) {
        for (int i = 0; i < stringContent.length(); i++) {
            char c = stringContent.charAt(i);
            // Regex match to check if char is letter
            if (Character.toString(c).matches("[a-z]")) {
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
