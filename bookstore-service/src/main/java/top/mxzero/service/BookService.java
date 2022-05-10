package top.mxzero.service;


import top.mxzero.model.Book;

import java.util.Map;

public interface BookService extends BaseService<Book, Long> {
    /**
     * 根据图书分类查询
     * @param column 模糊匹配的字段
     * @param keyword 字段值
     * @param current 当前页
     * @param size 每页大小
     * @param category 图书分类
     * @return 以Map封装数据
     * keys:
     *  current 当前页
     *  size 每页大小
     *  total 数据总数
     *  records 数据行
     */
    public Map<String,Object> list(String column, String keyword, Integer current, Integer size, Integer category);

}
