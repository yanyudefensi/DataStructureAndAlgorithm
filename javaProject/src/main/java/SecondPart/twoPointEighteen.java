package SecondPart;

import java.util.Arrays;
import java.util.HashMap;

public class twoPointEighteen {
  public static void main(String[] args) {}

  public static int rob(int[] nums) {
    int n = nums.length;
    int[] dp = new int[n + 2];
    for (int i = n - 1; i >= 0; i--) {
      dp[i] = Math.max(nums[i] + dp[i + 2], dp[i + 1]);
    }
    return dp[0];
  }

  public static int robOther(int[] nums) {
    int[] memo = new int[nums.length];
    Arrays.fill(memo, -1);
    return dp(nums, 0, memo);
  }

  private static int dp(int[] nums, int start, int[] memo) {
    if (start < 0 || start >= nums.length) {
      return 0;
    }
    if (memo[start] != -1) return memo[start];
    int res = Math.max(dp(nums, start + 1, memo), dp(nums, start + 2, memo) + nums[start]);
    memo[start] = res;
    return res;
  }

  // 首尾不能同时取钱的情况
  public static int robRange(int[] nums, int start, int end) {
    // 仅仅计算闭区间[start, end] 的最优结果
    int n = nums.length;
    int dpI1 = 0, dpI2 = 0;
    int dpI = 0;
    for (int i = end; i >= start; i--) {
      dpI = Math.max(dpI1, nums[i] + dpI2);
      dpI2 = dpI1;
      dpI1 = dpI;
    }
    return dpI;
  }

  public static int rob2(int[] nums) {
    int n = nums.length;
    if (n == 1) return nums[0];
    return Math.max(robRange(nums, 0, n - 2), robRange(nums, 1, n - 1));
  }

  // 树形排列的情况
  private class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    public void TreeNode(int x) {
      this.val = x;
    }
  }

  public static int rob3(TreeNode root) {
    HashMap<TreeNode, Integer> memo = new HashMap<TreeNode, Integer>();
    return help(root, memo);
  }

  public static int help(TreeNode root, HashMap<TreeNode, Integer> memo) {
    if (root == null) return 0;
    if (memo.containsKey(root)) return memo.get(root);
    int firstCheck =
        root.val
            + (root.left == null ? 0 : help(root.left.left, memo))
            + help(root.left.right, memo)
            + (root.right == null ? 0 : help(root.right.right, memo))
            + help(root.right.left, memo);
    int secondCheck =
        (root.right == null ? 0 : help(root.right, memo))
            + (root.left == null ? 0 : help(root.left, memo));

    int res = Math.max(firstCheck, secondCheck);
    memo.put(root, res);
    return res;
  }
}
