package top.mxzero.service;

import top.mxzero.model.Category;
import java.util.List;

public interface CategoryService {
    /**
     * 全部分类信息
     * @return 以List封装分类对象
     */
    public List<Category> list();

    /**
     * 获取单个分类信息
     * @param id 分类ID
     * @return 返回对应的model对象
     */
    public Category get(Integer id);
}
