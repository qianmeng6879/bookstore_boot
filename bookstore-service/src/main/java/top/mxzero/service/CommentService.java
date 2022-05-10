package top.mxzero.service;

import top.mxzero.model.Comment;

import java.util.List;

public interface CommentService extends BaseService<Comment, Long> {
    public boolean add(Comment comment);

    public boolean remove(Long id, Long userId);

    public List<Comment> listByBookId(Long bookId);

}
