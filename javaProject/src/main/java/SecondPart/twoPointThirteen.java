package SecondPart;

import java.util.HashMap;
import java.util.Vector;

public class twoPointThirteen {

    public static void main(String[] args) {

        System.out.println(superEggDropPlus(5, 200));
        System.out.println(superEggDropOther(5, 200));
    }

    public static int superEggDropPlus(int K, int N) {
        HashMap<Integer, Integer> memo = new HashMap<>();
        return dp(K, N, memo);
    }

    private static int dp(int K, int N, HashMap<Integer, Integer> memo) {

        int res = Integer.MAX_VALUE;

        if (memo.containsKey(100 * K + N)) {
            return memo.get(100 * K + N);
        } else {
            if (K == 1) {
                res = N;
            } else if (N == 0) {
                res = 0;
            } else {
                // 二分搜索代替线性搜索
                int left = 1, right = N;
                while (left <= right) {
                    int mid = (left + right) / 2;
                    int broken = dp(K - 1, mid - 1, memo);
                    int notBroken = dp(K, N - mid, memo);
                    if (broken > notBroken) {
                        right = mid - 1;
                        res = Math.min(res, broken + 1);
                    } else {
                        left = mid + 1;
                        res = Math.min(res, notBroken + 1);
                    }
                }
            }
        }

        memo.put(100 * K + N, res);
        return res;
    }

    public static int superEggDropOther(int K, int N) {
        int[][] dp = new int[K + 1][N + 1];
        //base case
        //dp[0][...] = 0, dp[..][0] = 0
        //Java 默认初始化数组都为0
        int m = 0;
        while (dp[K][m] < N) {
            m++;
            for (int k = 1; k <= K; k++) {
                dp[k][m] = dp[k][m - 1] + dp[k - 1][m - 1] + 1;
            }
        }
        return m;
    }
}
