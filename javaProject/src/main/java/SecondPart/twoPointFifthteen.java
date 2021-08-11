package SecondPart;

public class twoPointFifthteen {
    public static void main(String[] arg) {
        int[] wt = {2, 1, 3}, vt = {4, 2, 3};
        System.out.println(knapsack(4, 3, wt, vt));
    }

    public static int knapsack(int W, int N, int[] wt, int[] vt) {
        int[][] dp = new int[N + 1][W + 1];

        for (int n = 1; n <= N; n++) {
            for (int w = 1; w <= W; w++) {
                if (w - wt[n - 1] < 0) {
                    dp[n][w] = dp[n - 1][w];
                } else {
                    dp[n][w] = Math.max(dp[n - 1][w], dp[n - 1][w - wt[n - 1]] + vt[n - 1]);
                }
            }
        }
        return dp[N][W];
    }
}
