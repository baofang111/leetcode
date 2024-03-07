package DynamicPlan;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @summary Solution
 * @author: bf
 * @Copyright (c) 2024/3/6, © 拜耳作物科学（中国）有限公司
 * @since: 2024/3/6 10:23
 */
public class Solution {

    /**
     * 杨辉三角
     *  暴力解法，遍历一遍即可
     * @param numRows
     * @return
     */
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> res = new LinkedList<>();

        for (int i = 0; i < numRows; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                if (j == 0 || j == i) {
                    row.add(1);
                } else {
                    row.add(res.get(i - 1).get(j - 1) + res.get(i - 1).get(j));
                }
            }
            res.add(row);
        }

        return res;
    }

}
