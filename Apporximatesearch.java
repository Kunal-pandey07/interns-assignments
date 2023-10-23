import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ApproximateSearch {
    private static List<String> dictionary = new ArrayList<>();

    public static void main(String[] args) {
        loadDictionary("dictionary.txt"); // Replace "dictionary.txt" with your file path
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter a search query: ");
            String query = scanner.nextLine();

            List<String> suggestions = findApproximateMatches(query, 5); // Change '5' to the desired number of suggestions
            if (suggestions.isEmpty()) {
                System.out.println("No suggestions found.");
            } else {
                System.out.println("Suggestions:");
                for (String suggestion : suggestions) {
                    System.out.println(suggestion);
                }
            }
        }
    }

    private static void loadDictionary(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                dictionary.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> findApproximateMatches(String query, int k) {
        List<String> suggestions = new ArrayList<>();
        int minDistance = Integer.MAX_VALUE;

        for (String word : dictionary) {
            int distance = calculateLevenshteinDistance(query, word);
            if (distance < minDistance) {
                suggestions.clear();
                minDistance = distance;
            }
            if (distance == minDistance) {
                suggestions.add(word);
            }
        }

        return suggestions.subList(0, Math.min(k, suggestions.size()));
    }

    private static int calculateLevenshteinDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j], Math.min(dp[i][j - 1], dp[i - 1][j - 1]));
                }
            }
        }
        return dp[m][n];
    }
}
