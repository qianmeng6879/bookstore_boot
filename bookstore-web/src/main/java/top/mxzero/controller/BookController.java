package top.mxzero.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.mxzero.jwt.annotation.JWTAuthentication;
import top.mxzero.common.ResponseData;
import top.mxzero.common.exception.CustomException;
import top.mxzero.model.Book;
import top.mxzero.service.BookService;
import top.mxzero.vo.BookVO;
import top.mxzero.vo.PageVO;

import javax.validation.Valid;
import java.util.Map;
import java.util.Objects;

@Log4j2
@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    /**
     * 获取图书信息
     * @return 图书列表信息
     */
    @GetMapping("/books")
    public Object bookList(
            PageVO pageVO,
            @RequestParam(value = "category", required = false) Integer category
    ) {
        if (pageVO.getSize() != null && pageVO.getSize() > 50) {
            pageVO.setSize(50);
        }
        if(pageVO.getSize() == null){
            pageVO.setSize(10);
        }

        if(pageVO.getKeyword() != null && pageVO.getColumn() == null){
            pageVO.setColumn("title");
        }
        Map<String, Object> result = bookService.list(pageVO.getColumn(), pageVO.getKeyword(), pageVO.getCurrent(), pageVO.getSize(), category);
        ResponseData responseData = new ResponseData();
        responseData.setResult(result);
        responseData.setMessage("图书信息。");
        responseData.setStatus(200);
        return responseData;
    }

    /**
     * 新增图书
     * @param bookVO 图书VO对象
     * @return 新增成功返回该图书完整信息
     */
    @JWTAuthentication(roles = {"ADMIN"}, permission = {"book:add"})
    @PostMapping("/books")
    public Object addBook(@Valid @RequestBody BookVO bookVO){
        Book book = new Book();
        BeanUtils.copyProperties(bookVO,book);
        bookService.add(book);
        ResponseData responseData = new ResponseData();
        responseData.setStatus(200);
        responseData.setMessage("图书增加。");
        responseData.setResult(book);
        return responseData;
    }

    /**
     * 根据图书ISBN编号获取图书信息
     * @param id 图书编号
     * @return 返回该图书对象
     */
    @GetMapping("/books/{id}")
    public Object getBookDetail(@PathVariable Long id){
        Book book = bookService.get(id);
        ResponseData responseData = new ResponseData();
        responseData.setMessage("图书详细信息");
        responseData.setStatus(200);
        responseData.setResult(book);
        return responseData;
    }

    /**
     * 根据ISBN编号修改图书信息
     * @param id 图书ISBN编号
     * @param bookVO 图书VO对象
     * @return 返回修改后的图书对象
     */
    @JWTAuthentication(roles = "ADMIN", permission = "book:change")
    @PutMapping("/books/{id}")
    public Object editBook(@PathVariable Long id, @RequestBody BookVO bookVO){
        if(!Objects.equals(id, bookVO.getId())){
            throw new CustomException("图书编号不一致");
        }
        Book book = new Book();
        BeanUtils.copyProperties(bookVO,book);
        boolean b = bookService.update(book);
        ResponseData responseData = new ResponseData();
        responseData.setStatus(200);
        if(b){
            responseData.setResult(book);
            responseData.setMessage("图书信息修改成功。");
        }else {
            responseData.setMessage("图书信息修改失败。");
        }

        return responseData;
    }

    /**
     * 根据图书编号删除对应的图书信息
     * @param id 图书编号
     */
    @JWTAuthentication(roles = "ADMIN", permission = "book:remove")
    @DeleteMapping("/books/{id}")
    public Object removeBook(@PathVariable Long id){
        boolean b = bookService.remove(id);
        ResponseData responseData = new ResponseData();
        responseData.setStatus(204);
        responseData.setResult(b);
        return responseData;
    }
}
