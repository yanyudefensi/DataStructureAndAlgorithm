package ThirdPart;

public class threePointThree {
  public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    public TreeNode(int x) {
      this.val = x;
    }
  }

  // 如何将所有节点+1
  void plusOne(TreeNode root) {
    if (root == null) return;
    root.val += 1;
    plusOne(root.left);
    plusOne(root.right);
  }

  boolean isSameTree(TreeNode root1, TreeNode root2) {
    // 都为空的话就是相同
    if (root1 == null && root2 == null) return true;
    // 一个空一个非空显然不同
    if (root1 == null || root2 == null) return false;
    if (root1.val != root2.val) return false;

    return isSameTree(root1.left, root2.left) && isSameTree(root1.right, root2.right);
  }

  // 判断BST的合法性
  boolean isValidBST(TreeNode root) {
    return isValidBST(root, null, null);
  }

  boolean isValidBST(TreeNode root, TreeNode min, TreeNode max) {
    if (root == null) return true;
    if (min != null && root.val <= min.val) return false;
    if (max != null && root.val >= max.val) return false;
    return isValidBST(root.left, min, root) && isValidBST(root.right, max, root);
  }

  boolean isInBST(TreeNode root, int target) {
    if (root == null) return false;
    if (root.val == target) return true;
    return isInBST(root.left, target) || isInBST(root.right, target);
  }

  // 其实可以根据二叉树的特点,排除一边
  boolean isInBSTPro(TreeNode root, int target) {
    if (root == null) return false;
    if (root.val == target) return true;
    if (root.val > target) {
      return isInBSTPro(root.left, target);
    } else {
      return isInBSTPro(root.right, target);
    }
  }

  TreeNode insertBST(TreeNode root, int val) {
    // 找到空的位置就插入新节点
    if (root == null) return new TreeNode(val);
    // 如果已经存在，则不要插入
    if (root.val == val) return root;
    // val小应该插入到左子树上
    if (root.val > val) {
      root.left = insertBST(root.left, val);
    } else {
      root.right = insertBST(root.right, val);
    }
    return root;
  }

  TreeNode deleteBST(TreeNode root, int val) {
    if (root == null) return null;
    if (root.val == val) {
      if (root.left == null) return root.right;
      if (root.right == null) return root.left;
      // 处理当root有两个孩子的情况
      TreeNode minNode = getMin(root.right);
      // 实际上是交换root和minNode的位置完成删除，这里简化交换了node的值
      root.val = minNode.val;
      root.right = deleteBST(root.right, minNode.val);
    } else if (root.val > val) {
      root.left = deleteBST(root.left, val);
    } else {
      root.right = deleteBST(root.right, val);
    }
    return root;
  }

  TreeNode getMin(TreeNode node) {
    while (node.left != null) {
      node = node.left;
    }
    return node;
  }

  public void BST(TreeNode root, int target) {
    if (root.val == target) {

    }
    if (root.val < target) {
      BST(root.right, target);
    }
    if (root.val > target) {
      BST(root.left, target);
    }
  }

  public TreeNode insertIntoBST(TreeNode root, int val) {
    // 找到空的位置插入新的节点
    if (root == null) return new TreeNode(val);
    if (root.val == val) {
      return root;
    }
    if (root.val < val) {
      root.right = insertBST(root.right, val);
    }
    if (root.val > val) {
      root.left = insertBST(root.left, val);
    }
    return root;
  }
  }
