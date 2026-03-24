package juzhen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 矩阵相关算法 练习
 *
 * @summary JuzhenSolutionV2
 * @author: bf
 * @Copyright (c) 2026/3/23, © 拜耳作物科学（中国）有限公司
 * @since: 2026/3/23 15:48
 */
public class JuzhenSolutionV2 {

    /**
     * 36. 有效的数独
     * <p>
     * 题目意思：就是判断 一个数字，在 每行，每列，还有自己的 1- 9 的小矩阵里面是不是唯一
     * <p>
     * 题目解析：我们判断 三个 情况的 数字是否唯一，所以我们使用 三个 boolean 的数组进行记录即可，如果有任何一个已经有值了,那么
     * 只要有一个已经包含了，那么他就不是 有效的数独了
     */
    public boolean isValidSudoku(char[][] board) {
        int n = board.length;
        int m = board[0].length;

        // 记录 行 列 子矩阵
        boolean[][] row = new boolean[n][m];
        boolean[][] col = new boolean[n][m];
        boolean[][] box = new boolean[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                char c = board[i][j];
                if (c == '.') {
                    continue;
                }

                int num = c - '1';
                // 这个 子位置 很重要
                int boxIndex = (i / 3) * 3 + (j / 3);

                if (row[i][num] || col[j][num] || box[boxIndex][num]) {
                    return false;
                }

                row[i][num] = col[j][num] = box[boxIndex][num] = true;
            }
        }

        return true;
    }


    /**
     * 54. 螺旋矩阵
     * <p>
     * 题目意思：给定一个矩阵，我们按照 从左到右， 从上到下，从右到左，从下到上，不断的遍历，然后拿到遍历之后的值
     * <p>
     * 题目解析：一个核心的点，就是我们需要找到4个边界，然后没走完一个，就需要更新一下边界，然后继续遍历
     * <p>
     * 头：0
     * 左：0
     * 尾：height - 1
     * 右：length - 1
     *
     * @param matrix
     * @return
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        // 不断的遍历即可

        int n = matrix.length;
        int m = matrix[0].length;

        int sum = n * m;
        List<Integer> res = new ArrayList<>(sum);

        // 我们设置4 个临界点，用来做遍历的 灵界条件
        int top = 0;
        int bottom = n - 1;
        int left = 0;
        int right = m - 1;

        while (sum > 0) {
            // 左 - 右
            for (int i = left; i <= right; i++) {
                res.add(matrix[top][i]);
                sum--;
            }
            top++;

            if (sum == 0) {
                break;
            }

            // 上 - 下
            for (int i = top; i <= bottom; i++) {
                res.add(matrix[i][right]);
                sum--;
            }
            right--;


            // 右 - 左
            for (int i = right; i >= left; i--) {
                res.add(matrix[bottom][i]);
                sum--;
            }
            bottom--;


            // 下 - 上
            for (int i = bottom; i >= top; i--) {
                res.add(matrix[i][left]);
                sum--;
            }
            left++;

        }

        return res;
    }

    /**
     * 48. 旋转图像
     * <p>
     * 题目意思：将 二维矩阵 进行一次旋转
     * <p>
     * 题目解析：先转置 （i, j） -> (j, i) 在旋转，注意 转置，只需要转置 上半区，或者下半区 即可
     */
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;

        // 先转置
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        // 再旋转
        for (int i = 0; i < n; i++) {
            swap(matrix[i]);
        }

    }

    private void swap(int[] nums) {
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int temp = nums[right];
            nums[right] = nums[left];
            nums[left] = temp;
            left++;
            right--;
        }
    }

    /**
     * 73. 矩阵置零
     * <p>
     * 题目意思：有一个二维矩阵，当其中 一个地方 有 0 ，那么就需要将 这个的 行 和 列 全部设置为 0 ，
     * 就是 i j = 0 那么 i 行 j 列 全部设置为 0
     * <p>
     * 题目解析：
     * 1：暴力解法，使用 两个数组，分别计算哪一行 那一列有 0 ，然后重新遍历，遇到是这行，这里的 就设置为空
     * 2：原地解法：题目的要求是需要原地解法, 那么我就就将 第一行 和 第一列，用来存储 i j 的结果，然后再次遍历即可，
     * 注意，一定要提前遍历计算看 第一行 第一列 有没有 0 ，好用再 后面的 数据修正
     */
    public void setZeroes(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;

        boolean firstRow = false;
        boolean firstCol = false;

        // 遍历第一行
        for (int i = 0; i < n; i++) {
            if (matrix[i][0] == 0) {
                firstRow = true;
            }
        }

        // 第一列
        for (int j = 0; j < m; j++) {
            if (matrix[0][j] == 0) {
                firstCol = true;
            }
        }

        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                if (matrix[i][j] == 0) {
                    // 我们在 第一行 和 第一列 设置 成 0
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }

        // 再次遍历
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }

        // 遍历 第一行 和 第一列，填充内部数据
        if (firstRow) {
            for (int i = 0; i < n; i++) {
                matrix[i][0] = 0;
            }
        }
        if (firstCol) {
            Arrays.fill(matrix[0], 0);
        }

    }

    /**
     * 289. 生命游戏
     * <p>
     * 题目意思：简单的来说，就是 1 是活细胞，0是死细胞，但是 如果我 一个活细胞周围细胞太多了，那么他就会变成 死细胞，
     * 如果 死细胞
     * 活细胞周围活细胞数 定位为 total
     * 1：total < 2 | total > 3 , 当前细胞死亡 1 - >0
     * 2：total = 2 | 3, 当前细胞存活 1 -> 1
     * 3:total = 3, 如果当前是 死细胞 0 -> 1
     * <p>
     * 所以 我们就需要 挨个遍历 行 列 -1 - 1， 然后统计 每一个周围的 活细胞数
     * <p>
     * 如果是 1 -> 0 | 0 -> 1  的情况，需要在原地做一个记录 四个状态
     * 活
     * 死
     * 活 - 死
     * 死 - 活
     *
     * @param board
     */
    public void gameOfLife(int[][] board) {
        /*
             使用四个数值，来表示，四种状态
             0：死
             1：活
             2：活 - 死
             3：死 - 活
         */

        int n = board.length;
        int m = board[0].length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // 开始遍历
                int total = 0;

                // 我们需要遍历周围的八个数据
                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {
                        if (k == 0 && l == 0) {
                            // 是自己这个点，直接放行
                            continue;
                        }

                        int i1 = k + i;
                        int j1 = j + l;

                        if (i1 >= 0 && j1 >= 0 && i1 < n && j1 < m) {
                            // 注意这里 1 是活细胞，如果是 2 ，他也是活细胞，因为当前是活细胞，是后面才变成死细胞
                            if (board[i1][j1] == 1 || board[i1][j1] == 2) {
                                total++;
                            }
                        }
                    }
                }

                // 记录 2 3 的状态
                if (board[i][j] == 1) {
                    // 活细胞 变 死细胞
                    if (total > 3 || total < 2) {
                        board[i][j] = 2;
                    }
                } else {
                    // 死细胞 变活细胞
                    if (total == 3) {
                        board[i][j] = 3;
                    }
                }
            }
        }

        // 重新遍历，如果是 2 3 将它们变成相应的活细胞或者死细胞
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == 2) {
                    board[i][j] = 0;
                } else if (board[i][j] == 3) {
                    board[i][j] = 1;
                }
            }
        }
    }

}
