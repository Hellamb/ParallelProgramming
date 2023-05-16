package lab4.finddocument;


import java.util.List;

import static lab4.finddocument.DocumentFinder.findByKeyWords;

public class Main {
    public static void main(String[] args) {
        var documents = List.of(
                new Document("My document name 1", "My png document text content 1"),
                new Document("My txt document name 2", "My document text content 2"),
                new Document("My document name 3", "My document text content 3"),
                new Document("My pdf document name 4", "My document text content 4"),
                new Document("My document name 5", "My txt document text content 5"),
                new Document("My document png name 6", "My txt document text content 6")
        );

        System.out.println(findByKeyWords("png txt", documents));
    }
}
