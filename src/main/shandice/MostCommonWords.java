package shandice;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

/**
 * This class contains a methods for finding the most common words in a file.
 *
 * @author Shandice Johnson 2016
 */
public class MostCommonWords {
    // ----------
    // public
    // ----------
    /**
     * Finds the k most common words of in a given file filtered by words of a given length.
     *
     * @param file              to run analysis on
     * @param k                 the top k most common words
     * @param minimumCharacters the minimum amount of characters a word must have to be considered
     *                          during analysis
     * @return FixedPriorityQueue<WordCount> representing the word occurrences and the word
     */
    public FixedPriorityQueue<WordCount> findTopMostCommonWordsWithMinLength(File file, int k, int minimumCharacters) throws IOException {
        FixedPriorityQueue<WordCount> mostCommonWords = new FixedPriorityQueue<>(k);
        HashMap<String, WordCount> wordMap = new HashMap<>();

        BufferedReader br = null;

        try {
            br = Files.newBufferedReader(file.toPath());
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split(" ");

                for (String word : words) {
                    String scrubbed = word.replaceAll("[^A-Za-z0-9]", "");
                    if (scrubbed.length() < minimumCharacters) {
                        continue;
                    }

                    WordCount wc;
                    if (wordMap.containsKey(scrubbed)) {
                        wc = wordMap.get(scrubbed);
                        wc.incrementCount();
                    } else {
                        wc = new WordCount(scrubbed, 1);
                    }

                    wordMap.put(scrubbed, wc);
                    mostCommonWords.add(wc);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                br.close();
            }
        }

        return mostCommonWords;
    }

    /**
     * Finds the most common word in a given file
     *
     * @param file              to run analysis on
     * @param minimumCharacters the minimum amount of characters a word must have to be considered
     *                          during analysis
     * @return FixedPriorityQueue<WordCount> representing the word occurrences and the word
     */
    public FixedPriorityQueue<WordCount> findMostCommonWord(File file, int minimumCharacters) throws IOException {
        return findTopMostCommonWordsWithMinLength(file, 1, minimumCharacters);
    }

    /**
     * Finds the k most common words of in a given file
     *
     * @param file to run analysis on
     * @param k    the top k most common words
     * @return FixedPriorityQueue<WordCount> representing the word occurrences and the word
     */
    public FixedPriorityQueue<WordCount> findTopMostCommonWords(File file, int k) throws IOException {
        return findTopMostCommonWordsWithMinLength(file, k, 0);
    }

    //// ============================================================
    // Inner Classes
    // ============================================================

    /**
     * a {@code Comparable} class that associates a word with a count. Comparisons performed on the count
     */
    public class WordCount implements Comparable{
        // ============================================================
        // Fields
        // ============================================================
        private String word;
        private int count;

        /**
         * Given a word and count, the a new WordCount instance is created
         * @param word word associated with count
         */
        public WordCount(String word, int count){
            this.word = word;
            this.count = count;
        }

        // ----------
        // public
        // ----------
        public int incrementCount(){
            count++;
            return count;
        }
        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        @Override
        public int compareTo(Object o) {
            WordCount wordCount = (WordCount) o;

            if(count > wordCount.getCount()) {
                return 1;
            }
            else if(count < wordCount.getCount()) {
                return -1;
            }
            else {
                return 0;
            }
        }

        @Override
        public String toString(){
            return word;
        }
    }
}
