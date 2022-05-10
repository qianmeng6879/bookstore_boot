package top.mxzero.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.mxzero.model.Comment;

import java.util.List;

@Mapper
public interface CommentMapper {

    /**
     * 新建评论
     * @param comment 评论实体对象
     * @return 返回新增记录数量
     */
    public int doCreate(Comment comment);

    /**
     * 根据评论ID删除
     * @param id 评论的ID号
     * @return 返回删除的记录数量
     */
    public int doRemove(Long id);


    /**
     * 根据图书编号查询评论数据
     * @param bookId 图书编号
     * @return 以List形式返回评论实体对象
     */
    public List<Comment> findAllByBookId(Long bookId);
}
