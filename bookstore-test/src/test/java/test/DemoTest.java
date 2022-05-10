package test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.mxzero.StartTestApplication;
import top.mxzero.common.PasswordUtils;
import top.mxzero.service.CategoryService;

@SpringBootTest(classes = StartTestApplication.class)
public class DemoTest {

    @Autowired
    private CategoryService service;
    @Test
    public void test(){
        service.list().forEach(System.out::println);
    }

    @Test
    public void testPassword(){
        String mixin6879 = PasswordUtils.getSecurityPassword("admin");
        System.out.println(mixin6879);
    }
}
