package SecondPart;

public class twoPointThree {

    public int maxSubArray(int[] nums) {
        int n = nums.length;
        if(n == 0) return 0;
        int[] dp = new int[n];
        for(int i = 0; i < n; i++) {
            dp[i] = Math.max(nums[i], nums[i] + dp[i - 1]);
        }
        int res = Integer.MIN_VALUE;
        for(int i = 0; i < n; i++) {
            res = Math.max(res, dp[i]);
        }
        return res;
    }
    public int maxSubArray2(int[] nums) {
        int n = nums.length;
        if(n == 0) return 0;
        int dp_0 = nums[0];
        int dp_1 = 0, res = dp_1;

        for(int i = 0; i < n; i++) {
            dp_1 = Math.max(nums[i], dp_0 + nums[i]);
            dp_0 = dp_1;
            res = Math.max(res, dp_1);
        }
        return res;
    }

}
