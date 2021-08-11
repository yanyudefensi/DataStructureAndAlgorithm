package ThirdPart;

public class threePointFour {

    //普通二叉树，返回节点数
    public int countNodes(threePointThree.TreeNode root) {
        if (root == null) return 0;
        return 1 + countNodes(root.left) + countNodes(root.right);
    }
    //满二叉树
    public int countNodes1(threePointThree.TreeNode root) {
        int h = 0;
        // 计算树的高度
        while (root != null) {
            root = root.left;
            h++;
        }
        return (int) Math.pow(2, h) - 1;
    }
    // 完全二叉树
    public int countNodes2(threePointThree.TreeNode root) {
        threePointThree.TreeNode l = root, r = root;
        int hl = 0, hr = 0;
        // 记录左右子树高度
        while (l != null) {
            l = l.right;
            hl++;
        }
        while (r != null) {
            r = r.right;
            hr++;
        }
        //如果左右子树高度相同，则是满二叉树
        if (hl == hr) {
            return (int) Math.pow(2, hl) - 1;
        } else {
            return 1 + countNodes2(root.left) + countNodes2(root.right);
        }
    }

}
