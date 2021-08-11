package FirstPart;
import java.util.*;

import javax.swing.tree.TreeNode;

public class onePointFour {

    //BFS
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) {this.val = x;}
    }
    
    //二叉树的最小高度
    public int minDepth(TreeNode root) {
        if(root == null) return 0;
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(root);
        //将depth初始化为1
        int depth = 1;

        while(!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode curr = queue.poll();
                //判断是否到达终点
                if (curr.left == null && curr.right == null) {
                    return depth;
                }
                // 将curr的相邻节点加入队列
                if (curr.left != null) {
                    queue.add(curr.left);
                }
                if (curr.right != null) {
                    queue.add(curr.right);
                }
            }
            depth++;
        }

        return depth;
    }

    //死亡数字算法
    String plusOne(String s, int j) {
        char[] ch = s.toCharArray();
        if(ch[j] == '9') {
            ch[j] = '0';
        } else {
            int ch2int = Character.digit(ch[j], 10);
            ch2int += 1;
             // char转int没有直接方法，只能使用unicode强制转换了
            ch[j] = (char) (ch2int + 48);
        }
        return new String(ch);
    }

    String minusOne(String s, int j) {
        char[] ch = s.toCharArray();
        if (ch[j] == '0') {
            ch[j] = '0';
        } else {
            int ch2int = Character.digit(ch[j], 10);
            ch2int -= 1;
            ch[j] = (char)(ch2int + 48);
        }
        return new String(ch);
    }

    public int openLock(String[] deadends, String target) {
        Set<String> deads = new HashSet<String>();
        for(String s : deadends) deads.add(s);
        Set<String> visited = new HashSet<String>();
        Queue<String> q = new LinkedList<String>();
        //从起点开始启动广度优先搜索
        int step = 0;
        q.add("0000");
        visited.add("0000");

        while (!q.isEmpty()) {
            int qSize = q.size();
            for(int i = 0; i < qSize; i++) {
                String cur = q.poll();
                if(deads.contains(cur)) continue;
                if(target.equals(cur)) return step;
                // 将一个节点的未遍历相邻节点加入队列
                for(int j = 0; j < 4; j++) {
                    String up = plusOne(cur, j);
                    if(!visited.contains(up)) {
                        q.add(up);
                        visited.add(up);
                    }
                    String down = minusOne(cur, j);
                    if(!visited.contains(down)) {
                        q.add(down);
                        visited.add(down);
                    }
                }
            }
            step++;

        }
        return -1;
    }

    public int openLockFast(String[] deadends, String target) {
        Set<String> deads = new HashSet<String>();
        for(String dead : deadends) deads.add(dead);
        // 用集合不用队列，可以快速判断元素是否存在
        Set<String> q1 = new HashSet<String>();
        Set<String> q2 = new HashSet<String>();
        Set<String> visited = new HashSet<String>();
        //初始化起点与终点
        q1.add("0000");
        q2.add(target);
        int step = 0;

        while(!q1.isEmpty() && !q2.isEmpty()) {
            // 使用temp存储q1的扩散结果
            Set<String> temp = new HashSet<String>();

            for(String cur : q1) {
                if(deads.contains(cur)) continue;
                if(q2.contains(cur)) return step;
                visited.add(cur);

                for(int i = 0; i < 4; i++) {
                    String up = plusOne(cur, i);
                    if(!visited.contains(up)) {
                        temp.add(up);
                        visited.add(up);
                    }
                    String down = minusOne(cur, i);
                    if(!visited.contains(down)) {
                        temp.add(down);
                        visited.add(down);
                    }
                }
            }
            q1 = q2;
            q2 = temp;
            step++;
        }
        return -1;

    }
}
