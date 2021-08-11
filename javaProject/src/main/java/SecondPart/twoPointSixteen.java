package SecondPart;

public class twoPointSixteen {
    public static void main(String[] args) {
        int[] nums = {1, 3, 2, 5};
        System.out.println(canPartitionPlus(nums));
    }

    public static boolean canPartition(int[] nums) {
        int sum = 0;
        for (int num : nums) sum += num;
        if (sum % 2 != 0) return false;
        int n = nums.length;
        sum = sum / 2;
        // 构建dp数组
        boolean[][] dp = new boolean[n + 1][sum + 1];
        // base case
        for (int i = 0; i <= n; i++) {
            dp[i][0] = true;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= sum; j++) {
                if (j - nums[i - 1] < 0) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums[i - 1]];
                }
            }
        }
        return dp[n][sum];
    }

    public static boolean canPartitionPlus(int[] nums) {
        int sum = 0;
        for (int num : nums) sum += num;
        if (sum % 2 != 0) return false;
        int n = nums.length;
        sum = sum / 2;
        //构建dp
        boolean[] dp = new boolean[sum + 1];
        dp[0] = true;
        for (int i = 1; i <= n; i++) {
            for (int j = sum; j >= sum; j--) {
                if (j - nums[i - 1] >= 0) {
                    dp[j] = dp[j] || dp[j - nums[i - 1]];
                }
            }
        }
        return dp[sum];
    }
}
