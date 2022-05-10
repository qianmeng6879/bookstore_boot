package top.mxzero.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.mxzero.model.Category;

import java.util.List;

@Mapper
public interface CategoryMapper {
    public List<Category> findAll();

    public Category findById(Integer id);
}
