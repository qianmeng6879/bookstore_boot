package top.mxzero.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mxzero.mapper.CategoryMapper;
import top.mxzero.model.Category;
import top.mxzero.service.CategoryService;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper mapper;

    @Override
    public List<Category> list() {
        return mapper.findAll();
    }

    @Override
    public Category get(Integer id) {
        return mapper.findById(id);
    }
}
