package FirstPart;

import java.util.LinkedList;

public class onePointOne {
    void traverse(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            // 迭代访问arr
        }
    }

    class ListNode {
        int val;
        ListNode next;
    }

    void traverse(ListNode head) {
        for(ListNode p = head; p != null; p = p.next) {
            //迭代遍历 p.val                                                                              `
        }
    }
    void traverse2(ListNode head) {
        traverse2(head.next);
        // 后续遍历
    }

    class TreeNode {
        int val;
        TreeNode left, right;
    }
    void traverseTree(TreeNode root) {
        //
        traverseTree(root.left);
        //
        traverseTree(root.right);
        //
    }
    class NTreeNode {
        int val;
        NTreeNode[] children;
    }
    void traverseNTree(NTreeNode root) {
        for(NTreeNode child : root.children)
            traverseNTree(child);
    }

    void backTrack(int[] nums, LinkedList<Integer> track) {
        for (int i = 0; i < nums.length; i++) backTrack(nums, track);
    }
}
