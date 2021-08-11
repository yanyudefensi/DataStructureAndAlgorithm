package SecondPart;

import java.util.HashMap;

public class twoPointNinthteen {
  static int result = 0;

  public static void main(String[] args) {
    int[] nums = { 1, 3, 1, 4, 2 };
    int target = 5;
    System.out.println(findTargetSumWays(nums, target));
    System.out.println(findTargetSumWay(nums, target));
  }

  public static int findTargetSumWays(int[] nums, int target) {
    if (nums.length == 0)
      return 0;
    backTrack(nums, 0, target);
    return result;
  }

  // 一个纯粹的回溯算法
  private static void backTrack(int[] nums, int i, int rest) {
    // base case
    if (i == nums.length) {
      if (rest == 0) {
        // 说明恰好凑出target
        result++;
      }
      return;
    }
    // 给nums[i]选择-号
    rest += nums[i];
    backTrack(nums, i + 1, rest);
    rest -= nums[i];
    // 给nums[i]选择+号
    rest -= nums[i];
    backTrack(nums, i + 1, rest);
    rest += nums[i];
  }

  // 消除重叠子问题,完全是动态规划的思想
  private static HashMap<String, Integer> memo = new HashMap<>();

  public static int findTargetSumWay(int[] nums, int target) {
    if (nums.length == 0)
      return 0;
    return dp(nums, 0, target);
  }

  private static int dp(int[] nums, int i, int rest) {
    // base case
    if (i == nums.length) {
      if (rest == 0) {
        return 1;
      }
      return 0;
    }
    // 把它们转化为字符串才能作为哈希表的键
    String key = Integer.toString(i) + "," + Integer.toString(rest);
    if (memo.containsKey(key))
      return memo.get(key);
    // 还是穷举
    int result = dp(nums, i + 1, rest - nums[i]) + dp(nums, i + 1, rest + nums[i]);
    memo.put(key, result);
    return result;
  }

  public static int findTargetSumWay2(int[] nums, int target) {
    int sum = 0;
    for (int n : nums) {
      sum += n;
    }
    if (sum < target || (sum + target) % 2 == 1) {
      return 0;
    }
    return subset(nums, (sum + target) / 2);

  }

  public static int subset(int[] nums, int sum) {
    int n = nums.length;
    int[][] dp = new int[n + 1][sum + 1];
    // base case
    for (int i = 0; i <= n; i++) {
      //什么都不装是唯一的装法
      dp[i][0] = 1;
    }

    for (int i = 1; i <= n; i++) {
      for (int j = 0; j <= sum; j++) {
        if (j >= nums[i - 1]) {
          // 两种选择的结果之和
          dp[i][j] = dp[i - 1][j] + dp[i - 1][j - nums[i - 1]];
        } else {
          // 背包空间不足
          dp[i][j] = dp[i - 1][j];
        }
      }
    }
    return dp[n][sum];
  }

  public static int subsets(int[] nums, int target) {
    int n = nums.length;
    int[] dp = new int[target + 1];
    // base case
    dp[0] = 1;
    for(int i = 1; i <= n; i++) {
      for(int j = 0; j <= target; j++) {
        if(j >= nums[i - 1]) {
          dp[j] = dp[j] + dp[j - nums[i - 1]];
        } else {
          dp[j] = dp[j];
        }
      }
    }
    return dp[target];
  }
  
}
