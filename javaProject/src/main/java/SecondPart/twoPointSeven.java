package SecondPart;

import java.util.Arrays;

public class twoPointSeven {
    public int longestPalindromeSubseq(String s) {
        int n = s.length();
        char[] sArray = s.toCharArray();
        int[][] dp = new int[n][n];
        Arrays.fill(dp, 0);
        for (int i = 0; i < n; i++) {
            dp[i][i] = 1;
        }
        //反着遍历保证正确的状态转移
        for(int i = n - 2; i >= 0; i--) {
            for(int j = i + 1; j < n; j++) {
                if(sArray[i] == sArray[j]) {
                    dp[i][j] = dp[i - 1][j - 1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[0][n-1];

    }
}
