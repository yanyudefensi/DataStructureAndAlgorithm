package ThirdPart;

import java.util.LinkedList;

import jdk.nashorn.api.tree.Tree;

public class threePointFive {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int x) {
            this.val = x;
        }
    }
    //序列化函数的方法
    String SEP = ",";
    String NULL = "#";

    public String seriallize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serialize(root, sb);
        return sb.toString();
    }
    public void serialize(TreeNode root, StringBuilder sb) {
        if(root == null) {
            sb.append(NULL).append(SEP);
            return;
        }

        // 前序遍历的位置
        sb.append(root.val).append(SEP);

        serialize(root.left, sb);
        serialize(root.right, sb);
    }
    
    //思考一下如何反序列化二叉树
    public TreeNode deserialize(String data) {
        LinkedList<String> nodes = new LinkedList<>();
        for(String s: data.split(SEP)) {
           nodes.addLast(s);
        }
        return deserialize(nodes);
    }
    private TreeNode deserialize(LinkedList<String> nodes) {
        if(nodes == null) {
            return null;
        }
        // 前序遍历的位置
        String first = nodes.removeFirst();
        if(first.equals(NULL)) return null;
        TreeNode root = new TreeNode(Integer.parseInt(first));
        root.left = deserialize(nodes);
        root.right = deserialize(nodes);
        return root;            
    }

    //后序遍历
    public void serializeEnd(TreeNode root, StringBuilder sb) {
        if(root == null) sb.append(NULL).append(SEP);
        serializeEnd(root.left, sb);
        serializeEnd(root.left, sb);
        sb.append(root.val).append(SEP);
    }

    public TreeNode deserializeEnd(String data) {
        LinkedList<String> nodes = new LinkedList<>();
        for(String s : data.split(SEP)) {
            nodes.addLast(s);
        }
        return deserializeEnd(nodes);
         
    }
    private TreeNode deserializeEnd(LinkedList<String> nodes) {
        if(nodes.isEmpty()) return null;
        TreeNode root = new TreeNode(Integer.parseInt(nodes.removeLast());
        root.right = deserializeEnd(nodes);
        root.left = deserializeEnd(nodes);
        return root;
    }

    //层次遍历解法
    public String serializeGradation(TreeNode root) {
        if (root == null) return "";
        StringBuilder sb = new StringBuilder();
        //初始化队列，将root加入队列
        Queue<TreeNode> q = new LinkedList<TreeNode>();
        q.offer(root);
        while (!q.isEmpty()) {
            TreeNode cur = q.poll();
            if (cur == null) { 
                sb.append(NULL).append(SEP);
                continue;
            }
            sb.append(cur.val).append(SEP);
            q.offer(cur.left);
            q.offer(cur.right);                 
        }
        return sb.toString();
         
    }
    public TreeNode deserializeGradation(String data) {
        if (data.isEmpty()) return null;
        String[] nodes = data.split(SEP);
        TreeNode root = new TreeNode(Integer.parseInt(nodes[0]));
        //队列q记录父亲节点，将root加入到序列中
        q.offer(root);

        for(i = 0; i < nodes.length; i++) {
            // 队列中存入的都是父亲节点
            TreeNode parent = q.poll();
            // 左侧字节点的值
            String left = nodes[i++];
            if (!left.equals(NULL)) {
                parent.left = new TreeNode(Integer.parseInt(left));
            } else {
                parent.left = null;
            }
            String right = nodes[i++];
            if (!right.equals(NULL)) {
                parent.right = new TreeNode(Integer.parseInt(right));
            } else {
                parent.right = null;
            }
        }
        return root;
        
    }

    
}
