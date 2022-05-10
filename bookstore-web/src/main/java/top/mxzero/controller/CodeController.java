package top.mxzero.controller;

import lombok.Cleanup;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@RestController
public class CodeController {
    /**
     * 图形验证码获取
     * @return 图像流
     * @throws IOException 图像找不到，图像流加载错误
     */
    @RequestMapping(value = "/code", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE})
    public BufferedImage image() throws IOException {
        @Cleanup InputStream inputStream = new FileInputStream("E:\\Pictures\\Camera Roll\\img2.jpg");
        return ImageIO.read(inputStream); // 实现了文件加载
    }
}
