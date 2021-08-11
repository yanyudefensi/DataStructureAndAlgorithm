package ThirdPart;

public class threePointSix {
  public class ThreeNode {
    int val;
    ThreeNode left;
    ThreeNode right;

    ThreeNode(int x) {
      this.val = x;
    }
  }

  public ThreeNode lowestCommonAncestor(ThreeNode root, ThreeNode p, ThreeNode q) {
    // base case
    if (root == null) return null;
    if (root == p || root == q) return root;

    ThreeNode left = lowestCommonAncestor(root.left, p, q);
    ThreeNode right = lowestCommonAncestor(root.right, p, q);

    if (left != null && right != null) {
      return root;
    }
    if (left == null && right == null) {
      return null;
    }
    return left == null ? right : left;
  }
}
