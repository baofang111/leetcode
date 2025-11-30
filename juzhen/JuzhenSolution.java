package juzhen;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

/**
 * 矩阵相关算法
 *
 * @summary JuzhenSolution
 * @author: bf
 * @Copyright (c) © 拜耳作物科学（中国）有限公司
 * @since: 2025/11/30 11:08:38
 */
public class JuzhenSolution {

    public static void main(String[] args) {
        System.out.println((3 / 3) * 3 + (3 / 3));
    }


    /**
     * 36 - 有效的数独
     * <p>
     * 判断一个 9×9 的数独是否有效，需要满足：
     * 每一行不能出现重复数字
     * 每一列不能出现重复数字
     * 每个 3×3 小格不能出现重复数字
     * <p>
     * 关键点：
     * 数独里空格用 '.' 表示，可以忽略。
     * 只需要判断当前填入是否有效，不需要解数独。
     *
     * @param board
     * @return
     */
    public boolean isValidSudoku(char[][] board) {
        // 该题目的核心就是 我们要判断出来 每一行 i 每一列 j 还有每一个 3*3 的单独小数组中没有出现过 该 重复数字
        // 所以我们适用 row col box 三个二维数组分别标识这一存储结果
        boolean[][] row = new boolean[9][9];
        boolean[][] col = new boolean[9][9];
        boolean[][] box = new boolean[9][9];

        // 开始遍历二位数独数组 去判断
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                char c = board[i][j];
                if (c == '.') {
                    continue;
                }

                // 获取该数据应该占用的 位置，1-9 --> 0-8
                int num = c - '1';
                // 这一步非常重要，是确实我们 3 * 3 的小数独的具体 index
                int boxIndex = (i / 3) * 3 + (j / 3);

                // 下面判断即可
                if (row[i][num] || col[j][num] || box[boxIndex][num]) {
                    return false;
                }

                // 标记出现
                row[i][num] = col[j][num] = box[boxIndex][num] = true;
            }
        }
        return true;
    }


    /**
     * 54 - 螺旋矩阵
     * 给你一个 m 行 n 列的矩阵 matrix ，请按照 顺时针螺旋顺序 ，返回矩阵中的所有元素。
     * <p>
     * 解法：这题的核心就是 不断的 先 行 后列的遍历
     * 以 第一行作为开始
     * row 0 - row.length --> 固定柱 row.length 然后 开始往下面遍历列 col - clo.length
     * 然后继续固定 clo.length 不断的迭代
     * <p>
     * 我们需要维护 四个边界，这个非常重要
     * top = 0
     * bottom = m - 1
     * left = 0
     * right = n - 1
     * 循环顺序：
     * 从左到右 → top 行
     * 从上到下 ↓ right 列
     * 从右到左 ← bottom 行（前提：top <= bottom）
     * 从下到上 ↑ left 列（前提：left <= right）
     * 每遍历一行/一列，就收缩边界：
     * <p>
     * top++
     * bottom--
     * left++
     * right--
     *
     * @param matrix
     * @return
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        // 该问题的核心 我们确定 上下左右 四个边界，然后 按照 从左到右 从上到下 从 右到左 从下到上，去遍历即可，然后过改变我们的编辑
        List<Integer> res = new ArrayList<>();
        if (matrix == null) {
            return res;
        }

        // 列长
        int m = matrix.length;
        // 行长
        int n = matrix[0].length;

        int top = 0;
        int bottom = m - 1;
        int left = 0;
        int right = n - 1;

        // 总数，直接遍历完即可
        int num = m * n;

        while (num > 0) {
            // 从左往右
            for (int i = left; i <= right; i++) {
                res.add(matrix[top][i]);
                num--;
            }
            // 上边界往下移动
            top++;
            if (num == 0) {
                break;
            }

            // 从上往下
            for (int i = top; i <= bottom; i++) {
                res.add(matrix[i][right]);
                num--;
            }
            // 右边界向左移动
            right--;
            if (num == 0) {
                break;
            }

            // 从右往左
            for (int i = right; i >= left; i--) {
                res.add(matrix[bottom][i]);
                num--;
            }
            // 下边界往上移动
            bottom--;
            if (num == 0) {
                break;
            }

            // 从下往上
            for (int i = bottom; i >= top; i--) {
                res.add(matrix[i][left]);
                num--;
            }
            // 左边界往右移动
            left++;
            if (num == 0) {
                break;
            }

        }

        return res;
    }

    /**
     * 48 - 旋转图像
     * 给定一个 n × n 的二维矩阵 matrix 表示一个图像。请你将图像顺时针旋转 90 度。
     * <p>
     * 你必须在 原地 旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要 使用另一个矩阵来旋转图像。
     * <p>
     * 思路： 对矩阵 先 转置（只需要转换 上三角 区域即可） 再 每行反转
     * <p>
     * 转置 就是 将矩阵的 matrix[i][j] 与 matrix[j][i] 互换
     *
     * @param matrix
     */
    public void rotate(int[][] matrix) {
        if (matrix == null) {
            return;
        }

        int n = matrix.length;
        // 先转换 只需要转换 上三角 或者 下三角即可
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        // 再反转 每行翻转
        for (int[] row : matrix) {
            reverseRow(row);
        }

    }

    /**
     * 反转数组
     *
     * @param row
     */
    private void reverseRow(int[] row) {
        int left = 0;
        int right = row.length - 1;
        while (left < right) {
            int temp = row[left];
            row[left] = row[right];
            row[right] = temp;
            left++;
            right--;
        }
    }

    /**
     * 73 - 矩阵置 0
     * 给定一个 m x n 的矩阵，如果一个元素为 0 ，则将其所在行和列的所有元素都设为 0 。请使用 原地 算法
     * <p>
     * 解法：
     * 该题 提到了原地解法，如果不是原地解法的华，我们二次遍历，第一次记录 0 所在的 行和列
     * 第二次对于这些行和列 全部置 0 即可
     * <p>
     * 原地解法的话，我们就需要 适用 当前二维数组的本身来进行标记
     * 1：我们先记录 第一行 第一列 是否需要进行设置为 0
     * 2：然后 我们遍历 该数组，将 第一列和 第一行 左右 0 的标记位置
     * 3：然后继续遍历 和 第一行和第一列中的 0 作为比较，如果是 0 就全部设置 = 0
     * 4：在判断 第一行 第一列 是否需要进行设置 0 的操作
     *
     * @param matrix
     */
    public void setZeroes(int[][] matrix) {
        boolean firstRowZero = false;
        boolean firstColZero = false;

        int m = matrix.length;
        int n = matrix[0].length;

        // 遍历row
        for (int i = 0; i < m; i++) {
            if (matrix[i][0] == 0) {
                firstRowZero = true;
                break;
            }
        }

        for (int i = 0; i < n; i++) {
            if (matrix[0][i] == 0) {
                firstColZero = true;
                break;
            }
        }

        // 使用 第一行 和 第一列 进行数据的存储, 一定要从第二行和第二列开始遍历
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == 0) {
                    // 第一行 和 第一列的该位置 设置成 0
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }

        // 再次遍历
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }

        // 处理第一行和第一列的情况
        if (firstRowZero) {
            for (int i = 0; i < m; i++) {
                matrix[i][0] = 0;
            }
        }
        if (firstColZero) {
            for (int i = 0; i < n; i++) {
                matrix[0][i] = 0;
            }
        }
    }


    /**
     * 暴力解法
     *
     * @param matrix
     */
    public void setZeroes2(int[][] matrix) {
        // 暴力解法
        int m = matrix.length;
        int n = matrix[0].length;

        boolean[] row = new boolean[m];
        boolean[] col = new boolean[n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    row[i] = true;
                    col[j] = true;
                }
            }
        }

        // 二次遍历
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (row[i] || col[j]) {
                    matrix[i][j] = 0;
                }
            }
        }
    }

    /**
     * 289 - 生命游戏
     * 对于每一个细胞（格子）：
     * <p>
     * 活细胞死亡条件
     * 如果活细胞周围活细胞 < 2 → 死亡（孤独）
     * 如果活细胞周围活细胞 > 3 → 死亡（过度拥挤）
     * 活细胞保持生存
     * 如果活细胞周围活细胞 = 2 或 3 → 保持活着
     * 死亡细胞复活条件
     * 如果死亡细胞周围活细胞 = 3 → 复活（繁殖）
     * 注意：所有格子同时更新，不是逐个格子更新。
     *
     * @param board
     */
    public void gameOfLife(int[][] board) {
        /*
            原地算法的话，这题非常巧妙，我们在遍历矩阵的时候，首先需要判断 当前值 的 周围 8 个元素有几个活细胞
            然后我们还需要记住 当前点，具体是 要变成 活细胞，还是死细胞，所以我们这里需要4个状态
            0：死细胞
            1：活细胞
            2：活 - 死
            3：死 - 活
            因为需要原地更改，所以我们需要记录 2 3 的状态，做 最后状态的设置，这样才不会影响到之前数据的统计和判断
         */

        int m = board.length;
        int n = board[0].length;

        // step1: 遍历矩阵，并且获取到每个节点周围 8个节点的细胞状态，判断该节点是属于 0 1 2 3 中的哪个状态
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                // step2：统计当前节点周围的细胞状态，存活多少细胞熟练
                int liveCount = 0;

                // 周围遍历
                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        if (x == 0 && y == 0) {
                            // 过滤自己
                            continue;
                        }

                        // 具体对应在矩阵中的 index 行列未知
                        int rowIndex = i + x;
                        int colIndex = j + y;

                        // 临界条件判断
                        if (rowIndex >= 0 && colIndex >= 0 && rowIndex < m && colIndex < n) {
                            // 如果周围有 活细胞，那么就 + 1
                            // 注意 如果周围的细胞状态是 2 ，也就是即将活变死，那么他当前还是应该是活着的，也需要进行 + 1
                            if (board[rowIndex][colIndex] == 1 || board[rowIndex][colIndex] == 2) {
                                liveCount++;
                            }
                        }
                    }
                }

                // step3: 根据存活状态判断
                if (board[i][j] == 1) {
                    // 当前是存活状态, 独孤 + 过度拥挤
                    if (liveCount < 2 || liveCount > 3) {
                        board[i][j] = 2;
                    }
                } else {
                    // 当前是 死亡状态
                    if (liveCount == 3) {
                        board[i][j] = 3;
                    }
                }
            }
        }

        // 再次遍历，设置最终状态
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 2) {
                    board[i][j] = 0;
                } else if (board[i][j] == 3) {
                    board[i][j] = 1;
                }
            }
        }
    }

}
