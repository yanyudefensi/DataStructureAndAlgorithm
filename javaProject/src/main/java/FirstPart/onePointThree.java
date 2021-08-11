package FirstPart;
import java.util.*;

public class onePointThree {
    List<List<Integer>> res = new LinkedList<>();
    public List<List<Integer>> permuteList(int[] nums) {
        LinkedList<Integer> track = new LinkedList<>();
        backtrack(nums, track);
        return res;
    }
    private void backtrack(int[] nums, LinkedList<Integer> track) {
        //触发结束条件
        if(track.size() == nums.length) {
            res.add(new LinkedList<>(track));
            return;

        }
        for (int i = 0; i < nums.length; i++) {
            //排除不合法选择
            if(track.contains(nums[i])) continue;
            //做选择
            track.add(nums[i]);
            //进入到下一层决策树
            backtrack(nums, track);
            track.removeLast();
        }
    }

    //来到著名的八皇后问题
    Vector<Vector<String>> eightRes;
    public Vector<Vector<String>> solveNQueens(int n) {
        // '.' 表示空, 'Q' 表示皇后, 初始化棋盘
        Vector<String> board = new Vector<>();
        for (int i = 0; i < n; i++) {
            board.add(new String("."));
        }
        backtrackNQueens(board, 0);
        return eightRes;
    }
    /*
    board 中小于row的那些行已经成功放置好皇后
    row行的所有列都是放置皇后的选择
    row 超过board的最后一行，说明棋盘已经放满了
     */
    private void backtrackNQueens(Vector<String> board, int row) {
        if(row == board.size()) {
            eightRes.add(board);
            return;
        }
        int n = board.get(row).length();
        for (int col = 0; col < n; col++) {
            if(!isValid(board, row, col)) continue;
            StringBuilder changeString = new StringBuilder(board.get(row));
            changeString.replace(col, col, "Q");
            String newString = changeString.toString();
            board.set(row, newString);
            // 下一行决策
            backtrackNQueens(board, row + 1);
            changeString.replace(col, col, ".");
            board.set(row, new String(changeString.toString()));

        }
    }

    public boolean isValid(Vector<String> board, int row, int col) {
        int n = board.size();
        for(int i = 0; i < row; i++) {
            if(board.get(i).charAt(col) == 'Q') {
                return false;
            }
        }
        //检查右上方是否有皇后互相冲突
        for(int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
            if (board.get(i).charAt(col) == 'Q') {
                return false;
            }
        }
        //检查左上方是否有皇后互相冲突
        for(int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board.get(i).charAt(col) == 'Q') {
                return false;
            }
        }
        return true;
    }

}
