package SecondPart;

import java.util.*;

public class twoPointTen {

    public boolean isMatch(String s, String p) {
        char[] sArray = s.toCharArray();
        char[] pArray = p.toCharArray();
        HashMap<String,Boolean> memo = new HashMap<String, Boolean>();
        
        return dp(sArray, 0, pArray, 0, memo);
    }

    private boolean dp(char[] sArray, int i, char[] pArray, int j, HashMap<String, Boolean> memo) {

        // base case
        int m = sArray.length, n = pArray.length;
        if(j == n) {
            return i == m;
        }
        if(i == m) {
            //如果能匹配空串，一定是字符和*成对出现
            if ((n - j) % 2 == 1) {
                return false;
            }
            //检查是否为x*y*z*这种形式
            for(; j + 1 < pArray.length; j += 2) {
                if(pArray[j + 1] != '*') {
                    return false;
                }
            }
            return true;
        }
        String key = Integer.toString(i) + "," + Integer.toString(j);
        if(memo.containsKey(key)) return memo.get(key);

        boolean res = false;

        if (sArray[i] == pArray[j] || pArray[j + 1] == '.') {
            // pattern .
            if (j < pArray.length - 1 && pArray[j + 1] == '*') {
                // has *, pattern 0 or n
                res = dp(sArray, i, pArray, j + 2, memo) || dp(sArray, i + 1, pArray, j, memo);
            } else {
                res = dp(sArray, i + 1, pArray, j + 1, memo);
            }

        } else {
            // no .
            if (j < pArray.length - 1 && pArray[j + 1] == '*') {
                // has *， pattern 0
                res = dp(sArray, i, pArray, j + 2, memo);
            } else {
                // no *, cancel
                res = false;
            }
        }
        // 将结果记入备忘录
        memo.put(key, res);
        return res;
    }

}
