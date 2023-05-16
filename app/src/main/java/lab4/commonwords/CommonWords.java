package lab4.commonwords;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import static java.util.Arrays.asList;

public class CommonWords {

    public Set<String> search(String... texts) {
        List<List<String>> textWords = Arrays
                .stream(texts)
                .map(text -> asList(text.trim().toLowerCase(Locale.ROOT).split("\\s+"))).toList();

        var findingForks = new ArrayList<FindWord>();
        var forkJoinPool = new ForkJoinPool();

        for (int i = 0; i < textWords.size(); i++) {
            for (int j = i + 1; j < textWords.size(); j++) {
                FindWord findWord = new FindWord(textWords.get(i), textWords.get(j), 20);
                findingForks.add(findWord);
                forkJoinPool.invoke(findWord);
            }
        }

        var foundWords = new HashSet<String>();
        findingForks.forEach(fork -> {
            foundWords.addAll(fork.join());
        });

        return foundWords;
    }

    private class FindWord extends RecursiveTask<List<String>> {
        private List<String> text1;
        private List<String> text2;
        private int threshold;

        public FindWord(List<String> text1, List<String> text2, int threshold) {
            this.text1 = text1;
            this.text2 = text2;
            this.threshold = threshold;
        }

        @Override
        protected List<String> compute() {
            if (text2.size() < threshold) {
                List<String> commonWords = new ArrayList<>();
                text1.forEach(word1 -> {
                    text2.forEach(word2 -> {
                        if (word1.equals(word2)) {
                            commonWords.add(word1);
                        }
                    });
                });
                return commonWords;
            }
            var mid = text2.size() / 2;
            var splitLeft = new FindWord(text1, text2.subList(0, mid), threshold);
            var splitRight = new FindWord(text1, text2.subList(mid, text2.size()), threshold);
            ForkJoinTask.invokeAll(splitLeft, splitRight);

            var leftList = splitLeft.join();
            var rightList = splitRight.join();
            leftList.addAll(rightList);
            return leftList;
        }
    }
}
