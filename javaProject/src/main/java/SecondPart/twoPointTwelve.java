package SecondPart;

import java.util.*;

import org.apache.commons.collections4.map.MultiKeyMap;

public class twoPointTwelve {

    /**
     *
     * @param K the egg number
     * @param N the floor number
     * @return
     */
    private static int dp(int K, int N, MultiKeyMap<Integer,Integer> memo) {
        // base case
        if(N == 0) return 0;
        if(K == 1) return N;

        if(memo.containsKey(K, N)) return memo.get(K, N);

        int res = Integer.MAX_VALUE;
        // 穷举选择
        for(int i = 1; i <= N + 1; i++) {
            res = Math.min(res, Math.max(dp(K, N - i, memo), dp(K - 1, i - 1, memo)));
        }
        memo.put(K, N, res);
        return res;
    }
    public static int superEggdrop(int K, int N) {
        MultiKeyMap<Integer,Integer> memo = new MultiKeyMap<>();
        return dp(K, N, memo);
    }


}
