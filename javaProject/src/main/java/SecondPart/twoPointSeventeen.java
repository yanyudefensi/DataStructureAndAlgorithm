package SecondPart;

public class twoPointSeventeen {
    public static void main(String[] args) {
        int amount = 5;
        int[] coins = {1,2,5};
        System.out.println(change(amount,coins));
        System.out.println(changePlus(amount, coins));

    }

    public static int change(int amount, int[] coins) {
        int n = coins.length;
        int[][] dp = new int[n + 1][amount + 1];
        // base case
        for (int i = 0; i <= n; i++) {
            dp[i][0] = 1;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= amount; j++) {
                if (j - coins[i - 1] < 0) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i][j - coins[i - 1]] + dp[i - 1][j];
                }
            }
        }
        return dp[n][amount];
    }

    public static int changePlus(int amount, int[] coins) {
        int n = coins.length;
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j <= amount; j++) {
                if (j - coins[i] >= 0) {
                    dp[j] = dp[j - coins[i]] + dp[j];
                }
            }
        }
        return dp[amount];
    }
}
