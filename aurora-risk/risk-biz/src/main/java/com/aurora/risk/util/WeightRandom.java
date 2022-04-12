package com.aurora.risk.util;

import org.springframework.util.CollectionUtils;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author: Nick
 * @create: 2022-03-28 18:21
 **/
public class WeightRandom<T> {

  private final TreeMap<Integer, T> weightMap;

  /**
   * 创建权重随机获取器
   *
   * @param <T> 权重随机获取的对象类型
   * @return {@link WeightRandom}
   */
  public static <T> WeightRandom<T> create() {
    return new WeightRandom<>();
  }

  /**
   * 构造
   */
  private WeightRandom() {
    weightMap = new TreeMap<>();
  }

  /**
   * 增加对象
   *
   * @param obj 对象
   * @param weight 权重
   */
  public void add(T obj, int weight) {
    if (weight > 0) {
      int lastWeight = (this.weightMap.size() == 0) ? 0 : this.weightMap.lastKey();
      // 权重累加
      this.weightMap.put(weight + lastWeight, obj);
    }
  }

  /**
   * 清空权重表
   */
  public void clear() {
    this.weightMap.clear();
  }

  /**
   * 获取随机对象
   *
   * @return 随机对象
   */
  public T getObject() {
    int randomWeight = (int) (100 * Math.random());
    SortedMap<Integer, T> tailMap = this.weightMap.tailMap(randomWeight, false);
    if (CollectionUtils.isEmpty(tailMap)){
      return null;
    }
    return this.weightMap.get(tailMap.firstKey());
  }

}
