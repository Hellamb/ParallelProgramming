package lab4.finddocument;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import static lab4.commonwords.CommonWords.searchCommonWords;

public class DocumentFinder {
    public static List<Document> findByKeyWords(String keyWords, List<Document> documents) {
        var forkJoinPool = new ForkJoinPool();
        var findDocuments = new FindDocuments(keyWords, documents, 2);
        forkJoinPool.invoke(findDocuments);
        return findDocuments.join();
    }

    private static class FindDocuments extends RecursiveTask<List<Document>> {
        private String keyWords;
        private List<Document> documents;
        private int threshold;

        public FindDocuments(String keyWords, List<Document> documents, int threshold) {
            this.keyWords = keyWords;
            this.documents = documents;
            this.threshold = threshold;
        }

        @Override
        protected List<Document> compute() {
            if (documents.size() <= threshold) {
                var foundDocuments = new ArrayList<Document>();
                documents.forEach(document -> {
                    var foundWords = new HashSet<String>();
                    foundWords.addAll(searchCommonWords(keyWords, document.name()));
                    foundWords.addAll(searchCommonWords(keyWords, document.text()));
                    if (foundWords.containsAll(Arrays.stream(keyWords.trim().toLowerCase(Locale.ROOT).split("\\s+")).toList())) {
                        foundDocuments.add(document);
                    }
                });
                return foundDocuments;
            }

            var mid = documents.size()/2;

            var splitLeft = new FindDocuments(keyWords, documents.subList(0, mid), threshold);
            var splitRight = new FindDocuments(keyWords, documents.subList(mid, documents.size()), threshold);

            invokeAll(splitLeft, splitRight);

            var left = splitLeft.join();
            left.addAll(splitRight.join());
            return left;
        }
    }
}
