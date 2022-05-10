package test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.mxzero.StartBookStoreApplication;
import top.mxzero.mapper.OrderMapper;

@SpringBootTest(classes = StartBookStoreApplication.class)
public class TestOrderMapper {
    @Autowired
    private OrderMapper mapper;


    @Test
    public void testMapper(){
    }
}
