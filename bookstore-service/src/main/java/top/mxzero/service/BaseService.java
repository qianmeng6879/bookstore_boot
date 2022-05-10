package top.mxzero.service;

import java.util.Map;

/**
 * 基本业务接口
 * @param <T> 操作model数据类型
 * @param <K>操作model的主键类型
 */
public interface BaseService<T, K> {

    /**
     * 根据主键获取对应信息
     * @param id 主键值
     * @return 以对应的模型对象返回
     */
    public T get(K id);


    /**
     * 获取列表信息，分页获取信息
     * @param current 当前页码
     * @param size 每页大小
     * @return 以Map封装对应的模型对象
     * keys:
     *  current 当前页
     *  size 每页大小
     *  page 总页数
     *  total 总记录数
     *  records 数据行
     */
    public Map<String, Object> list(Integer current, Integer size);


    /**
     * 新增一条记录
     * @param model 对应的模型对象
     * @return 新增成功返回true,否则返回false
     */
    public boolean add(T model);


    /**
     * 根据ID修改模型数据
     * @param model 对应的模型对象
     * @return 修改成功返回true,否则返回false
     */
    public boolean update(T model);


    /**
     * 根据主键删除记录
     * @param id 主键值
     * @return 删除成功返回true
     */
    public boolean remove(K id);

}
