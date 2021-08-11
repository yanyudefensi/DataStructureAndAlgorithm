package SecondPart;
import java.util.*;

public class twoPointOne {

    // 最长递增子序列,用心去体会,n^2
    public int lenghthOFLIS(int[] nums) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp,1);
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if(nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        int res = 0;
        // 要重新遍历一遍数组，找到最长的递增子序列的长度
        for (int i = 0; i < dp.length; i++) {
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    // 最长递增子序列nlogn解法
    public int lengthOFILSBest(int[] nums) {
        int[] top = new int[nums.length];
        // 牌堆初始化为0
        int piles = 0;
        for (int i = 0; i < nums.length; i++) {
            // 要处理的扑克牌
            int poker = nums[i];

            // 搜索左侧边界的二分搜索
            int left = 0; int right = piles;
            while(left < right) {
                int mid = (left + right) / 2;
                if(top[mid] > poker) {
                    right = mid;
                } else if(top[mid] < poker) {
                    left = mid + 1;
                } else if(top[mid] == poker) {
                    right = mid;
                }
            }
            // 没有找到合适的牌堆，新建一堆
            if(left == piles) piles++;
            top[left] = poker;
        }
        return piles;
    }
}
