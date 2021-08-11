package SecondPart;

public class twoPointFour {
    public class TreeNode {
        int value;
        TreeNode left;
        TreeNode right;
        public void TreeNode(int x) { this.value = x;}
    }
    //求二叉树的最大值
    public int Maxval(TreeNode root) {
        if (root == null) return -1;
        int left = Maxval(root.left);
        int right = Maxval(root.right);
        return Math.max(root.value, Math.max(left, right));
    }
}
