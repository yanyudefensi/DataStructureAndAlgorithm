package FirstPart;

import java.util.*;
import java.lang.Integer;


public class onePointFive {

    public static void main(String[] args) {
        System.out.println(Integer.MIN_VALUE);
    }


    public static class ListNode {
        int value;
        ListNode next;

        ListNode(int x) {
            this.value = x;
        }
    }

    // 快慢指针判断是否有环
    public boolean hasCycle(ListNode head) {
        ListNode fast, slow;
        fast = slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }
    /**
     * 检测到环的话，返回环起点
     * @param head
     * @return
     */
    public ListNode detectCycle(ListNode head) {
        
        ListNode fast, slow;
        fast = slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                break;
            }
        }
        // 已经检测到环,开始确定位置
        slow = head;
        while (slow != fast) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }

    //寻找链表中点
    public ListNode findMidPoint(ListNode head) {
        ListNode fast, slow;
        fast = slow = head;
        while(fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }
    //寻找单链表的倒数第k个元素
    public ListNode lastKElement(ListNode head, int k) {
        ListNode slow, fast;
        slow = fast = head;
        while(k > 0) {
            fast = fast.next;
            k--;
        }
        while(fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }
    //左右指针的常用算法
    public int binarySearch(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while(left <= right) {
            int mid = (left + right) / 2;
            if(nums[mid] == target) {
                return mid;
            }
            else if(nums[mid] < target) {
                left = mid + 1;
            }
            else if(nums[mid] > target) {
                right = mid - 1;
            }
        }
        return -1;
    }
    
    public int[] twoSum(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while(left < right) {
            int sum = nums[left] + nums[right];
            if(sum == target) {
                return new int[]{left + 1, right + 1};
            } else if(sum < target) {
                left ++;
            } else if(sum > target) {
                right --;
            }
        }
        return new int[]{-1, -1};
    }

}
