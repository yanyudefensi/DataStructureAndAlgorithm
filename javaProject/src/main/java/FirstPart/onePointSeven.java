package FirstPart;

import java.util.*;

public class onePointSeven {
    public static void main(String[] args) {
        //目前发现特别大数据量时出错
        System.out.println(minWindow("ADOBECODEBANC","ABC"));
        System.out.println(checkInclusion("oow","helloworld"));
        
    }

    public static String minWindow(String s, String t) {
        int slen = s.length();
        int tlen = t.length();
        HashMap<Character,Integer> need = new HashMap<Character,Integer>();
        HashMap<Character,Integer> window = new HashMap<Character,Integer>();
        for(int i = 0; i < tlen; i++) {
            char c = t.charAt(i);
            need.put(c,need.getOrDefault(c, 0) + 1);
        }
        int left = 0, right = 0, valid = 0;
        //记录最小覆盖字符串的起始索引和长度
        int start = 0, len = Integer.MAX_VALUE;
        while(right < slen) {
            //c是移入窗口的字符串
            char c = s.charAt(right);
            right++;
            if(need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if(window.get(c) == need.get(c)) {
                    valid++;
                }
            }
            //判断左侧窗口是否收缩
            while(valid == need.size()) {
                if(right - left < len) {
                    start = left;
                    len = right - left;
                }
                // d是将移出窗口的字符
                char d = s.charAt(left);
                // 左移窗口
                left++;
                if(need.containsKey(d)) {
                    if((window.get(d)) == need.get(d)) {
                        valid--;
                    }
                    window.put(d, window.getOrDefault(d, 0) - 1);
                }
            }
        }
        // 返回最小覆盖字符串
        return len == Integer.MAX_VALUE ? "" : s.substring(start, start+len);

    }

    // 字符串排列
    public static boolean checkInclusion(String t, String s) {
        HashMap<Character,Integer> need = new HashMap<Character,Integer>();
        HashMap<Character,Integer> window = new HashMap<Character,Integer>();
        int slen = s.length();
        int tlen = t.length();
        for(int i = 0; i < tlen; i++) {
            char c = t.charAt(i);
            need.put(c,need.getOrDefault(c, 0) + 1);
        }
        int left = 0; int right = 0; int valid = 0;
        while(right < slen) {
            char c = s.charAt(right);
            right++;
            if(need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if(window.get(c) == need.get(c)) {
                    valid++;
                }
            }

            while(right - left >= tlen) {
                if(valid == need.size()) {
                    return true;
                }
                char d = s.charAt(left);
                left++;
                if(need.containsKey(d)) {
                    window.put(d, window.getOrDefault(d, 0) - 1);
                    if(window.get(d) == need.get(d)) {
                        valid--;
                    }
                }
            }
            
        }
        return false;
    }

    // 找所有字母异位词
    public Vector<Integer> findAnagrams(String s, String t) {
        HashMap<Character,Integer> need = new HashMap<Character,Integer>();
        HashMap<Character,Integer> window = new HashMap<Character,Integer>();
        int slen = s.length();
        int tlen = t.length();
        for(int i = 0; i < tlen; i++) {
            char c = t.charAt(i);
            need.put(c, need.getOrDefault(c, 0) + 1);
        }
        int left = 0; int right = 0; int valid = 0;
        Vector<Integer> res = new Vector<Integer>();
        while(right < slen) {
            char c = s.charAt(right);
            right++;
            if(need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if(window.get(c) == need.get(c)) {
                    valid++;
                }
            }
            // 判断左侧窗口是否要收缩
            while(right - left > tlen) {
                if(valid == need.size()) {
                    res.add(left);
                }
                char d = s.charAt(left);
                left++;
                if(need.containsKey(d)) {
                    window.put(c, window.getOrDefault(c, 0) - 1);
                    if(window.get(c) == need.get(c)) {
                        valid--;
                    }
                }
            }
        }
        return res;

    }
    

    public int lengthOfLongestSubstring(String s) {
        HashMap<Character, Integer> window = new HashMap<Character, Integer>();
        int left = 0; int right = 0; int res = 0;
        int slen = s.length();
        while(right < slen) {
            char c = s.charAt(right);
            right++;
            window.put(c, window.getOrDefault(c,0) + 1);

            while(window.get(c) > 1) {
                char d = s.charAt(left);
                left++;
                window.put(c, window.getOrDefault(c, 0) - 1);
            }
            // 在这里更新答案
            res = Integer.max(res, right - left);
        }
        return res;

    }


}
