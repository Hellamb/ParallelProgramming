package lab4.sizeanalyzer;

public record AnalyzeResult(int totalWords, int maxLength, int minLength, double avgLength){
    @Override
    public String toString() {
        return "AnalyzeResult{" +
                "totalWords=" + totalWords +
                ", maxLength=" + maxLength +
                ", minLength=" + minLength +
                ", avgLength=" + avgLength +
                '}';
    }
}
