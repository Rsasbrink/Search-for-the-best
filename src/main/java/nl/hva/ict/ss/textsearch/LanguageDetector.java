package nl.hva.ict.ss.textsearch;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class LanguageDetector {

    private String content; // Once an instance is created this will hold the complete content of the file.
    private String docContent = "";
    private int docContentLength;
    HashMap<Character, Double> letterFrequency = new HashMap<>();
    HashMap<Character, Double> dutchLetterFrequency = new HashMap<>();
    HashMap<Character, Double> EnglishLetterFrequency = new HashMap<>();
    int englishScore;
    int dutchScore;

    public LanguageDetector(InputStream input) {
        Scanner sc = new Scanner(input);
        sc.useDelimiter("\\Z"); // EOF marker
        content = sc.next();
        sc.close();
        setupDutchLetterFrequency();
        setupEnglishLetterFrequency();

    }

    public boolean isEnglish() {
        setupDoc();
        getLetterFrequency(docContent);

        System.out.println("letter \t dutch \t\t\t text \t\t\t\t english ");
        letterFrequency.entrySet().forEach((pair) -> {
            Character key = pair.getKey();
            double value = pair.getValue();
            Double dutchValue = dutchLetterFrequency.get(key);
            Double englishValue = EnglishLetterFrequency.get(key);
            double closest = closer(dutchValue, englishValue, value);
            String dutchIndicator = "";            
            String englishIndicator = "";

            if (dutchValue == closest) {
                dutchScore++;
                dutchIndicator = "<-";
            } else if (englishValue == closest) {
                englishScore++;
                englishIndicator = "->";
            }
            System.out.println(key + " \t " + dutchValue + " \t\t\t " +dutchIndicator+value+englishIndicator+ " \t\t\t " + englishValue);
            
        }
        );
        System.out.println("dutch; " + dutchScore + " english; " + englishScore);
        return englishScore > dutchScore;
    }

    public void getLetterFrequency(String stringContent) {
        for (int i = 0; i < stringContent.length(); i++) {
            char c = stringContent.charAt(i);
            // Regex match to check if char is letter
            if (Character.toString(c).matches("[a-z]")) {
                docContentLength++;
                Double val = letterFrequency.get(c);
                if (val != null) {
                    letterFrequency.put(c, val + 1);
                } else {
                    letterFrequency.put(c, 1.0);
                }
            }

        }
        letterFrequency.entrySet().forEach((pair) -> {
            double value = pair.getValue();
            DecimalFormat df = new DecimalFormat("###.##");
            double percentage = value / docContentLength * 100;
            percentage = Math.round(percentage * 100.0) / 100.0;
            letterFrequency.put(pair.getKey(), percentage);
        });
    }

    public String convertToLowerCase(String content) {
        String lowerContent;
        lowerContent = content.toLowerCase();
        return lowerContent;
    }

    private void setupDoc() {
        Scanner docScanner = new Scanner(content);
        docScanner.useDelimiter("\\Z"); // EOF marker

        // Scan again for the javadoc and exclude the code itself;
        while (docScanner.hasNextLine()) {
            String line = docScanner.nextLine().trim();
            Pattern pattern = Pattern.compile("\\/\\*|\\*\\/|\\*.*");
            Matcher m = pattern.matcher(line);
            boolean match = m.matches();
            if (match) {
                docContent += line + "\n";
            }
        }
        // remove * from javadoc
        docContent = docContent.replace("*", "");
        // remove / from javadoc
        docContent = docContent.replace("/", "");

        docScanner.close();
        docContent = convertToLowerCase(docContent);
    }

    // Function to check which frquency is closer to the text it's frequency
    // https://codereview.stackexchange.com/questions/86754/given-two-int-values-return-the-one-closer-to-10/86755
    private double closer(double dutch, double english, double text) {
        double calcDutch = dutch - text;
        double calcEnglish = english - text;
        if (Math.abs(calcDutch) == Math.abs(calcEnglish)) {
            return 0;
        } else if ((dutch >= text || english >= text) && dutch < english) {
            return dutch;
        } else if ((dutch >= text || english >= text) && english < dutch) {
            return english;
        } else if (calcEnglish > calcDutch && Math.abs(dutch) != Math.abs(english)) {
            return english;
        } else if ((calcDutch < 0 || calcEnglish < 0) && (calcDutch > calcEnglish && Math.abs(dutch) != Math.abs(english))) {
            return dutch;
        } else {
            return closer(dutch, english, text);
        }
    }

    private void setupDutchLetterFrequency() {
        dutchLetterFrequency.put('e', Math.round(18.91 * 100.0) / 100.0);
        dutchLetterFrequency.put('n', Math.round(10.032 * 100.0) / 100.0);
        dutchLetterFrequency.put('a', Math.round(7.486 * 100.0) / 100.0);
        dutchLetterFrequency.put('t', Math.round(6.79 * 100.0) / 100.0);
        dutchLetterFrequency.put('i', Math.round(6.499 * 100.0) / 100.0);
        dutchLetterFrequency.put('r', Math.round(6.411 * 100.0) / 100.0);
        dutchLetterFrequency.put('o', Math.round(6.063 * 100.0) / 100.0);
        dutchLetterFrequency.put('d', Math.round(5.933 * 100.0) / 100.0);
        dutchLetterFrequency.put('s', Math.round(3.73 * 100.0) / 100.0);
        dutchLetterFrequency.put('l', Math.round(3.568 * 100.0) / 100.0);
        dutchLetterFrequency.put('g', Math.round(3.403 * 100.0) / 100.0);
        dutchLetterFrequency.put('v', Math.round(2.85 * 100.0) / 100.0);
        dutchLetterFrequency.put('h', Math.round(2.380 * 100.0) / 100.0);
        dutchLetterFrequency.put('k', Math.round(2.248 * 100.0) / 100.0);
        dutchLetterFrequency.put('m', Math.round(2.213 * 100.0) / 100.0);
        dutchLetterFrequency.put('u', Math.round(1.99 * 100.0) / 100.0);
        dutchLetterFrequency.put('b', Math.round(1.584 * 100.0) / 100.0);
        dutchLetterFrequency.put('p', Math.round(1.57 * 100.0) / 100.0);
        dutchLetterFrequency.put('w', Math.round(1.52 * 100.0) / 100.0);
        dutchLetterFrequency.put('j', Math.round(1.46 * 100.0) / 100.0);
        dutchLetterFrequency.put('z', Math.round(1.39 * 100.0) / 100.0);
        dutchLetterFrequency.put('c', Math.round(1.242 * 100.0) / 100.0);
        dutchLetterFrequency.put('f', Math.round(0.805 * 100.0) / 100.0);
        dutchLetterFrequency.put('x', Math.round(0.036 * 100.0) / 100.0);
        dutchLetterFrequency.put('y', Math.round(0.035 * 100.0) / 100.0);
        dutchLetterFrequency.put('q', Math.round(0.009 * 100.0) / 100.0);
    }

    private void setupEnglishLetterFrequency() {
        EnglishLetterFrequency.put('e', Math.round(12.702 * 100.0) / 100.0);
        EnglishLetterFrequency.put('t', Math.round(9.056 * 100.0) / 100.0);
        EnglishLetterFrequency.put('a', Math.round(8.167 * 100.0) / 100.0);
        EnglishLetterFrequency.put('o', Math.round(7.507 * 100.0) / 100.0);
        EnglishLetterFrequency.put('i', Math.round(6.966 * 100.0) / 100.0);
        EnglishLetterFrequency.put('n', Math.round(6.749 * 100.0) / 100.0);
        EnglishLetterFrequency.put('s', Math.round(6.327 * 100.0) / 100.0);
        EnglishLetterFrequency.put('h', Math.round(6.094 * 100.0) / 100.0);
        EnglishLetterFrequency.put('r', Math.round(5.987 * 100.0) / 100.0);
        EnglishLetterFrequency.put('d', Math.round(4.253 * 100.0) / 100.0);
        EnglishLetterFrequency.put('l', Math.round(4.025 * 100.0) / 100.0);
        EnglishLetterFrequency.put('c', Math.round(2.782 * 100.0) / 100.0);
        EnglishLetterFrequency.put('u', Math.round(2.758 * 100.0) / 100.0);
        EnglishLetterFrequency.put('m', Math.round(2.406 * 100.0) / 100.0);
        EnglishLetterFrequency.put('w', Math.round(2.360 * 100.0) / 100.0);
        EnglishLetterFrequency.put('f', Math.round(2.228 * 100.0) / 100.0);
        EnglishLetterFrequency.put('g', Math.round(2.015 * 100.0) / 100.0);
        EnglishLetterFrequency.put('y', Math.round(1.974 * 100.0) / 100.0);
        EnglishLetterFrequency.put('p', Math.round(1.929 * 100.0) / 100.0);
        EnglishLetterFrequency.put('b', Math.round(1.492 * 100.0) / 100.0);
        EnglishLetterFrequency.put('v', Math.round(0.978 * 100.0) / 100.0);
        EnglishLetterFrequency.put('k', Math.round(0.772 * 100.0) / 100.0);
        EnglishLetterFrequency.put('j', Math.round(0.153 * 100.0) / 100.0);
        EnglishLetterFrequency.put('x', Math.round(0.150 * 100.0) / 100.0);
        EnglishLetterFrequency.put('q', Math.round(0.095 * 100.0) / 100.0);
        EnglishLetterFrequency.put('z', Math.round(0.074 * 100.0) / 100.0);
    }

}
