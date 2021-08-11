package SecondPart;

public class twoPointSix {
    public static void main(String[] args) {
//        minDistanceChange("haveagoodlunch", "haveagooddinner");
        minDistance("haveagoodlunch", "haveagooddinner");
    }
    // 求最短编辑距离
    public static int minDistance(String s1, String s2) {
       int m = s1.length(), n = s2.length();
       int [][] dp = new int[m + 1][n + 1];
       // base case
        for(int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }
        for(int j = 1; j <= n; j++) {
            dp[0][j] = j;
        }
        //自底部而上求解
        for(int i = 1; i <= m; i++) {
            for(int j = 1; j <= n; j++) {
                if(s1.charAt(i - 1) == s2.charAt(j -1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = min(dp[i][j - 1] + 1, dp[i -1][j] + 1, dp[i - 1][ j -1] + 1);
                }
            }
        }
        System.out.println(dp[m][n]);
        return dp[m][n];

    }
    // 执行编辑距离操作
    private static class Node {
        /*
        0 none, 1 insert, 2 delete, 3 replace
         */
        int val;
        int choice;
        Node(int val, int choice) {
            this.val = val;
            this.choice = choice;
        }
    }
     public static int minDistanceChange(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        Node[][] dp = new Node[m+1][n+1];
        // s1 change s2 只需要一个字符
        for(int i = 0; i <= m; i++)
            dp[i][0] = new Node(i, 2);
        for(int j = 1; j <= n; j++)
            dp[0][j] = new Node(j,1);

        for(int i = 1; i <= m; i++)
            for(int j = 1; j <= n; j++)
                // 两个字符相同，什么都不需要做
                if(s1.charAt(i-1) == s2.charAt(j-1)) {
                    Node node = dp[i -1][j - 1];
                    dp[i][j] = new Node(node.val, 0);
                } else {
                    dp[i][j] = minNode(dp[i - 1][j], dp[i][j - 1], dp[i - 1][j - 1]);
                    dp[i][j].val++;
                }
        // 最后根据dp table 反推具体操作过程并且打印
        printResult(dp, s1, s2);
        System.out.println(dp[m][n].val);
        return dp[m][n].val;

    }
    // delete, insert, replace
    private static Node minNode(Node a, Node b, Node c) {
        if (a == null) {
            System.out.println(b.val + "and" + b.choice);
            System.out.println(c.val + "and" + c.choice);
            return null;
        }
        Node res = new Node(a.val, 2);
        if(res.val > b.val) {
            res.val = b.val;
            res.choice = 1;
        }
        if(res.val > c.val) {
            res.val = c.val;
            res.choice = 3;
        }
        return res;
    }
    private static void printResult(Node[][] dp, String s1, String s2) {
        int rows = dp.length;
        int cols = dp[0].length;
        int i = rows - 1, j = cols - 1;
        System.out.println("Change s1=" + s1 + " to s2=" + s2 + ":\n");
        while(i != 0 && j != 0) {
            char c1 = s1.charAt(i - 1);
            char c2 = s2.charAt(j - 1);
            int choice = dp[i][j].choice;
            System.out.println("s1[" + (i - 1) + "]:");
            switch(choice) {
                case 0:
                    // 跳过，两个指针同时前进
                    System.out.println("skip'" + c1 + "'");
                    i--; j--;
                    break;
                case 1:
                    // 将s2[j]插入s1[i],则s2指针前进
                    System.out.println("insert'" + c2 + "'");
                    j--;
                    break;
                case 2:
                    System.out.println("delete'" + c1 + "'");
                    i--;
                    break;
                case 3:
                    System.out.println("replace'" + c1 + "'" + "with '" + c2 + "'");
                    i--; j--;
                    break;

            }
        }
        while (i > 0) {
            System.out.println("s1[" + (i - 1) + "]:");
            System.out.println("delete '" + s1.charAt(i - 1) + "'");
            i--;
        }
        while (j > 0) {
            System.out.println("s1[0]:");
            System.out.println("insert'" + s2.charAt(j - 1) + "'");
            j--;
        }
    }


    private static int min(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }
}
