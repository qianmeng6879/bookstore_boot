package top.mxzero.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.mxzero.common.ResponseData;
import top.mxzero.model.Category;
import top.mxzero.service.CategoryService;
import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 图书分类列表
     * @return 以统一格式返回数据
     */
    @GetMapping("/categories")
    public Object categoryList(){
        List<Category> categoryList = categoryService.list();
        ResponseData responseData = new ResponseData();
        responseData.setMessage("商品分类信息");
        responseData.setStatus(200);
        responseData.setResult(categoryList);
        return responseData;
    }
}
