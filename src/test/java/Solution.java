import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {
    /**
     * @param n 棋盘大小
     * @return 合法的放置
     */
    public List<List<String>> solveNQueens(int n) {
        //结果
        List<List<String>> result = new ArrayList<>();
        // ‘.’ 表示空格， ‘Q’表示皇后
        char [][] board = new char[n][n];//棋盘，用二位数组表示
        //初始化棋盘为空
        for(int i=0; i<n; i++){
            Arrays.fill(board, '.');
        }
        backtrack(result, board, 0);
        return null;
    }

    private void backtrack(List<List<String>> result, char[][] board, int row) {
        if (row == board.length){
            result.add(charToList(board));
            return;
        }
        for (int col=0;col<board.length;col++){
            //不合法继续下一列
            if (!isValid(board, row, col)){
                continue;
            }
            //合法则将棋盘放上Q
            board[row][col] = 'Q';
            //递归放置下一行的皇后
            backtrack(result, board, row+1);
            //撤销选择，回退到上一步
            board[row][col] = '.';
        }
    }

    public List charToList(char[][] board) {
        List<String> list = new ArrayList<>();
        for (char[] c : board) {
            list.add(String.valueOf(c));
        }
        return list;
    }

    /* 判断是否可以在 board[row][col] 放置皇后 */
    public boolean isValid(char[][] board, int row, int col) {
        int n = board.length;
        // 检查列是否有皇后冲突
        for (int i = 0; i < n; i++) {
            if (board[i][col] == 'Q') {
                return false;
            }
        }

        // 检查右上方是否有皇后冲突
        for (int i = row - 1, j = col + 1; i >=0 && j < n; i--, j++) {
            if (board[i][j] == 'Q') {
                return false;
            }
        }

        // 检查左上方是否有皇后冲突
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 'Q') {
                return false;
            }
        }
        return true;
    }
}
