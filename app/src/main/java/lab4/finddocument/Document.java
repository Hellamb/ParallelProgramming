package lab4.finddocument;

public record Document(String name, String text) {
    @Override
    public String toString() {
        return "Document{" +
                "name='" + name + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
