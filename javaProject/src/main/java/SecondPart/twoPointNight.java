package SecondPart;

import java.util.*;

public class twoPointNight {

    public int minIntersection(String s) {
        int n = s.length();
        char[] sArray = s.toCharArray();
        int[][] dp = new int[n][n];
        Arrays.fill(dp, 0);
        for (int i = n - 2; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                if (sArray[i] == sArray[j]) {
                    dp[i][j] = dp[i + 1][j - 1];
                }
                if (sArray[i] != sArray[j]) {
                    dp[i][j] = Math.min(dp[i + 1][j], dp[i][j - 1]) + 1;
                }
            }
        }
        return dp[0][n - 1];

    }

    public int bestMinIntersection(String s) {
        int n = s.length();
        char[] sArray = s.toCharArray();
        int[] dp = new int[n];
        Arrays.fill(dp, 0);

        int temp = 0;
        for (int i = n - 2; i >= 0; i--) {
            int pre = 0;
            for (int j = i + 1; j < n; j++) {
                temp = dp[j];
                if (sArray[i] == sArray[j]) {
                    dp[j] = pre;
                } else {
                    dp[j] = Math.min(dp[j], dp[j - 1]) + 1;
                }
                pre = temp;
            }
        }
        return dp[n - 1];
    }

}
