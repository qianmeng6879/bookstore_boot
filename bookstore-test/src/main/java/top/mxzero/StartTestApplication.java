package top.mxzero;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.mxzero.service.CategoryService;

@SpringBootApplication
@RestController
public class StartTestApplication {
    @Autowired
    private CategoryService service;

    public static void main(String[] args) {
        SpringApplication.run(StartTestApplication.class,args);
    }


    @RequestMapping("/home")
    public Object home(){
        return service.list();
    }
}
