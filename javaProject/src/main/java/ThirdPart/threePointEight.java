package ThirdPart;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class threePointEight {
    class MonotonicQueue {
        LinkedList<Integer> q = new LinkedList<>();
        public void push(int n) {
            while(!q.isEmpty() && q.getLast() <= n) {
                q.pollLast();
            }
            q.addLast(n);
        }
        public void pop(int n) {
            if (n == q.getFirst()) {
                q.pollFirst();
            }
        }
        public int max() {
            return q.getFirst();
        }
    }

    public int[] maxSlidingWindow(int[] nums, int k) {
       MonotonicQueue window = new MonotonicQueue();
       List<Integer> res = new ArrayList<>();
       for(int i = 0; i < nums.length; i++) {
	        if (i < k - 1) {
	            window.push(nums[i]);
            } else {
	            //窗口向前滑动，并加入新的数字
                window.push(nums[i]);
                res.add(window.max());
                //移出旧的数字
                window.pop(i - k + 1);
            }
       }
       int[] arr = new int[res.size()];
       for(int i = 0; i < res.size(); i++) {
           arr[i] = res.get(i);
       }
       return arr;
    }
}
