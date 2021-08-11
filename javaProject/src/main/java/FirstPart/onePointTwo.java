package FirstPart;

import java.util.Arrays;
import java.util.HashMap;

public class onePointTwo {
    public int fib(int n) {
        if(n == 0) return 0;
        //将备忘录初始化为0
        int[] memo = new int[n];
        Arrays.fill(memo, 0);
        return helper(memo, n);

    }
    private int helper(int[] memo, int n) {
        if(n == 1 || n == 2) return 1;
        if (memo[n] != 0) {
            return memo[n];
        }
        memo[n] = helper(memo, n-1) + helper(memo, n-2);
        return memo[n];
    }
    // 这里也可以试试从下到上的解法
    public int fibdown(int n) {
        if(n == 0) return 0;
        if (n == 1 || n == 2) {
            return 1;
        }
        int[] dp = new int[n];
        Arrays.fill(dp, 0);
        for(int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }
    // 状态压缩
    public int fibBest(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        int prev = 1, curr = 1;
        for(int i = 3; i <= n; i++) {
            int sum = prev + curr;
            curr = sum;
            prev = curr;
        }
        return curr;
    }
    // 凑零钱问题，还是两个方向的解法
    public int coinChange(int[] coins, int amount) {
        HashMap<Integer,Integer> memo = new HashMap<Integer, Integer>();
        return dp(memo, coins, amount);
    }
    private int dp(HashMap<Integer, Integer> memo, int[] coins, int n) {
        if(n == 0) return 0;
        if(n < 0) return -1;
        if(memo.containsKey(n)) return memo.get(n);
        int res = Integer.MAX_VALUE;
        for(int coin : coins) {
            int subProblem = dp(memo, coins, n - coin);
            if (subProblem == -1) continue;
            res = Math.min(res, subProblem);
        }
        memo.put(n, res != Integer.MAX_VALUE ? res : -1);
        return res != Integer.MAX_VALUE ? res : -1;
    }
    // 另一个解法
    public int coinChangedown(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        // base case
        dp[0] = 0;
        for(int i = 0; i < coins.length; i++) {
           for(int coin: coins) {
               if(i - coin < 0) continue;
               dp[i] = Math.min(dp[i], dp[i - coin] + 1);
           }
        }
        return (dp[amount] != amount + 1) ? dp[amount] : -1;
    }



}
