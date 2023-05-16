package lab4.sizeanalyzer;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class WordLengthAnalyzer {
    public static AnalyzeResult syncAnalyze(String text) {
        String[] words = extractWords(text);
        return analyze(words);
    }

    public static AnalyzeResult asyncAnalyze(String text) {
        String[] words = extractWords(text);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        var forkAnalyzer = new ForkWordLengthAnalyzer(words, words.length / 10);
        forkJoinPool.invoke(forkAnalyzer);
        return forkAnalyzer.join();
    }

    private static String[] extractWords(String text) {
        return text.trim().split("\\s+");
    }

    private static AnalyzeResult analyze(String[] words) {
        int totalWords = words.length;
        int sumLength = 0;
        int maxLength = 0;
        int minLength = Integer.MAX_VALUE;

        for (String word : words) {
            int length = word.length();

            sumLength += length;
            maxLength = Math.max(maxLength, length);
            minLength = Math.min(minLength, length);
        }

        double averageLength = (double) sumLength / totalWords;

        return new AnalyzeResult(totalWords, maxLength, minLength, averageLength);
    }

    private static class ForkWordLengthAnalyzer extends RecursiveTask<AnalyzeResult> {
        private final String[] words;
        private final int threshold;

        public ForkWordLengthAnalyzer(String[] words, int threshold) {
            this.words = words;
            this.threshold = threshold;
        }

        @Override
        protected AnalyzeResult compute() {
            if (words.length <= threshold || words.length == 1) {
                return analyze(words);
            } else {
                int mid = words.length / 2;
                ForkWordLengthAnalyzer leftTask = new ForkWordLengthAnalyzer(Arrays.copyOfRange(words, 0, mid), threshold);
                ForkWordLengthAnalyzer rightTask = new ForkWordLengthAnalyzer(Arrays.copyOfRange(words, mid, words.length), threshold);

                invokeAll(leftTask, rightTask);

                AnalyzeResult leftResult = leftTask.join();
                AnalyzeResult rightResult = rightTask.join();

                return mergeResults(leftResult, rightResult);
            }
        }

        private AnalyzeResult mergeResults(AnalyzeResult left, AnalyzeResult right) {
            int totalWords = left.totalWords() + right.totalWords();
            int minLength = Math.min(left.minLength(), right.minLength());
            int maxLength = Math.max(left.maxLength(), right.maxLength());
            double avgLength = (left.totalWords() * left.avgLength() + right.totalWords() * right.avgLength()) / totalWords;
            return new AnalyzeResult(totalWords, maxLength, minLength, avgLength);
        }
    }
}
