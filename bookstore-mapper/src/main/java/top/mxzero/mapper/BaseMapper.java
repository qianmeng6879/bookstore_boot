package top.mxzero.mapper;


import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 基本Mapper接口
 * @param <T> 对应模型对象类型
 * @param <K> 模型的主键类型
 */
public interface BaseMapper<T,K> {
    /**
     * 数据插入操作
     * @param model 操作的model类
     * @return 返回受影响的数据行数
     */
    public int doCreate(T model);

    /**
     * 数据删除操作
     * @param id 主键值
     * @return 返回受影响的数据行数
     */
    public int doRemove(K id);

    /**
     * 数据修改操作
     * @param model 操作的model类
     * @return 返回受影响的数据行数
     */
    public int doUpdate(T model);

    /**
     * 根据主键获取单个对象
     * @param id 主键值
     * @return 以对应的model对象返回
     */
    public T findById(K id);

    /**
     * 根据指定的字段查找数据
     * @param column
     * @param keyword
     * @return
     */
    public T findOne(@Param("column") String column, @Param("keyword") String keyword);

    /**
     * 模糊查询、分页
     * @param column 模糊查询的字段
     * @param keyword 字段值
     * @param current 当前页
     * @param size 每页大小
     * @return 以List返回对应的model对象
     */
    public List<T> findAll(@Param("column") String column, @Param("keyword") String keyword, @Param("current") Integer current, @Param("size") Integer size);

    public int getCount(String column, String value);
}
