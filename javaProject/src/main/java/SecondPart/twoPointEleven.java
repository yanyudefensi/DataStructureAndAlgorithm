package SecondPart;

import java.util.*;

import org.apache.commons.collections4.map.MultiKeyMap;

public class twoPointEleven {

    public static void main(String[] args) {
        System.out.println(maxA(7));
    }

    private static int dp(
            int operateNumber, int aNumber, int pasteNumber, MultiKeyMap<Integer, Integer> memo) {
        // base case
        if (operateNumber <= 0) {
            return aNumber;
        }

        if (memo.containsKey(operateNumber, aNumber, pasteNumber)) {
            return memo.get(operateNumber, aNumber, pasteNumber);
        }
        memo.put(
                operateNumber,
                aNumber,
                pasteNumber,
                Math.max(
                        dp(operateNumber - 1, aNumber + 1, pasteNumber, memo),
                        Math.max(
                                dp(operateNumber - 1, aNumber + pasteNumber, pasteNumber, memo),
                                dp(operateNumber - 2, aNumber, aNumber, memo))));

        return memo.get(operateNumber, aNumber, pasteNumber);
    }

    public static int maxA(int n) {
        List<Integer> ID = new LinkedList<>();
        MultiKeyMap memo = new MultiKeyMap();
        return dp(n, 0, 0, memo);
    }

    public static int maxB(int n) {
        int[] dp = new int[n + 1];
        //base case
        dp[0] = 0;
        for (int i = 1; i <= n; i++) {
            // 按a键
            dp[i] = dp[i - 1] + 1;
            // 按cv键
            for (int j = 2; j < i; j++) {
                // 全选并复制dp[j - 2], 连续粘贴 i - j次
                // 屏幕上一共 dp[j - 2] * (i - j + 1)个A
                dp[i] = Math.max(dp[i], dp[j - 2] * (i - j + 1));
            }
        }
        return dp[n];
    }


}
