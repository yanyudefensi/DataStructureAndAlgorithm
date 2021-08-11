package ThirdPart;

import java.util.Stack;

public class threePointSeven {
  public int[] nextGreaterElement(int[] nums) {
    int[] ans = new int[nums.length];
    Stack<Integer> s = new Stack<Integer>();
    for (int i = nums.length - 1; i >= 0; i--) {
      while (!s.empty() && s.peek() <= nums[i]) {
        s.pop();
      }
      ans[i] = s.empty() ? -1 : s.peek();
      s.push(nums[i]);
    }
    return ans;
  }

  //求多少天才能得到一个温和的气温
  public int[] dailyTemperatures(int[] T) {
    int[] ans = new int[T.length];
    Stack<Integer> s = new Stack<Integer>();
    for (int i = T.length - 1; i >= 0; i--) {
      while (!s.empty() && T[s.peek()] <= T[i]) {
        s.pop();
      }
      ans[i] = s.empty() ? 0 : (s.peek() - i);
      s.push(i);
    }
    return ans;
  }
  //处理循环数组的技巧
  public int[] nextGreaterElements(int[] nums) {
    int[] res = new int[nums.length];
    Stack<Integer> s = new Stack<Integer>();
    //假装这个数组长度翻倍了
    for(int i = 2*nums.length - 1; i >= 0; i--) {
      while (!s.empty() && s.peek() <= nums[i]) {
        s.pop();
      }
      res[i % nums.length] = s.empty() ? -1 : s.peek();
      s.push(nums[i % nums.length]);
     }
    return res;
  }
}
