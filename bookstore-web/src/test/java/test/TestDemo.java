package test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.mxzero.StartBookStoreApplication;
import top.mxzero.mapper.CartMapper;

@SpringBootTest(classes = StartBookStoreApplication.class)
public class TestDemo {

    @Autowired
    private CartMapper mapper;


    @Test
    public void test(){
    }
}
