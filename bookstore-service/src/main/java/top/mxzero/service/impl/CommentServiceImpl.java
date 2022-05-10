package top.mxzero.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mxzero.mapper.CommentMapper;
import top.mxzero.model.Comment;
import top.mxzero.service.CommentService;

import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper mapper;

    @Override
    public Comment get(Long id) {
        return null;
    }

    @Override
    public Map<String,Object> list(Integer current, Integer size) {
        return null;
    }

    @Override
    public boolean add(Comment comment) {
        return false;
    }

    @Override
    public boolean update(Comment model) {
        return false;
    }

    @Override
    public boolean remove(Long id) {
        return false;
    }

    @Override
    public boolean remove(Long id, Long userId) {
        return false;
    }

    @Override
    public List<Comment> listByBookId(Long bookId) {
        return null;
    }
}
