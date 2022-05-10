package top.mxzero.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mxzero.common.exception.CustomException;
import top.mxzero.mapper.BookMapper;
import top.mxzero.mapper.CategoryMapper;
import top.mxzero.model.Book;
import top.mxzero.service.BookService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    public boolean add(Book book) {
        if (bookMapper.findOne("isbn", book.getIsbn()) != null) {
            throw new CustomException("isbn编号已存在。");
        }

        if (categoryMapper.findById(book.getCategoryId()) == null) {
            throw new CustomException("分类编号不存在。");
        }

        return bookMapper.doCreate(book) > 0;
    }

    @Override
    public boolean update(Book model) {
        return bookMapper.doUpdate(model) > 0;
    }

    @Override
    public boolean remove(Long id) {
        return bookMapper.doRemove(id) > 0;
    }

    @Override
    public Book get(Long id) {
        return bookMapper.findById(id);
    }

    @Override
    public Map<String, Object> list(Integer current, Integer size) {
        List<Book> records = bookMapper.findAll(null, null, current, size);
        Map<String, Object> map = new HashMap<>();
        map.put("data", records);
        map.put("current", current);
        map.put("size", records.size());
        map.put("total", bookMapper.getCount(null, null));
        return map;
    }

    @Override
    public Map<String, Object> list(String column, String keyword, Integer current, Integer size, Integer category) {
        List<Book> records = bookMapper.findAllByCategory(column, keyword, current, size, category);
        int total = bookMapper.getCountByCategory(column, keyword, category);
        Map<String, Object> map = new HashMap<>();
        map.put("data", records);
        map.put("current", current == null ? 0 : 1);
        map.put("size", records.size());
        map.put("total", total);
        return map;
    }
}
