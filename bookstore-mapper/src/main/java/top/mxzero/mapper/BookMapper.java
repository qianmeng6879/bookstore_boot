package top.mxzero.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.mxzero.model.Book;

import java.util.List;

@Mapper
public interface BookMapper extends BaseMapper<Book, Long> {

    /**
     * 根据某个分类查询图书信息
     * @param column 模糊匹配的字段名称
     * @param keyword 字段值
     * @param current 当前页
     * @param size 每页大小
     * @param categoryId 图书分类ID
     * @return 图
     */
    public List<Book> findAllByCategory(@Param("column") String column, @Param("keyword") String keyword, @Param("current") Integer current, @Param("size") Integer size, @Param("categoryId") Integer categoryId);

    /**
     * 统计某个分类的图书数量
     * @param column 模糊匹配的字段名称
     * @param keyword 字段值
     * @param categoryId 图书分类ID
     * @return 记录数量
     */
    public int getCountByCategory(@Param("column") String column, @Param("keyword") String keyword, @Param("categoryId")Integer categoryId);

}
