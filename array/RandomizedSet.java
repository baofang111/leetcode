package array;

import java.util.*;

/**
 * 380. O(1) 时间插入、删除和获取随机元素
 * <p>
 * 该题目的核心 是 0（1） 时间 达到我们的要求，
 * 普通的 list 删除做不到唯一
 * set 做不到随机
 * <p>
 * 所以我们需要用两个 结合起来
 * 用 list 存数据，职场 1 的随机访问
 * 用 map 存索引
 *
 * @summary RandomizedSet
 * @author: bf
 * @Copyright (c) 2026/3/19, © 拜耳作物科学（中国）有限公司
 * @since: 2026/3/19 17:35
 */
public class RandomizedSet {

    List<Integer> nums;
    Map<Integer, Integer> indexMap;
    Random random;


    public RandomizedSet() {
        nums = new ArrayList<>();
        indexMap = new HashMap<>();
        random = new Random();
    }

    public boolean insert(int val) {
        if (indexMap.containsKey(val)) {
            return false;
        }

        nums.add(val);
        indexMap.put(val, nums.size() - 1);
        return true;
    }

    /**
     * 移除需要做到 0 1 ，就需要, 使用 map 直接拿到 val 对应的 index，然后使用最前或者最后的值去 填充
     *
     * @param val
     * @return
     */
    public boolean remove(int val) {
        if (!indexMap.containsKey(val)) {
            return false;
        }

        // 我们取出 val 的值，然后使用 nums 的最后一位元素，再次填充到该位置
        Integer index = indexMap.get(val);
        Integer last = nums.getLast();

        // 替换值
        nums.set(index, last);
        indexMap.put(last, index);

        // 删除值
        nums.removeLast();
        indexMap.remove(val);

        return true;
    }

    public int getRandom() {
        return nums.get(random.nextInt(nums.size()));
    }

}
