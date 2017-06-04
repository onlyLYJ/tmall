package tmall;

import org.apache.commons.lang.StringUtils;
import tmall.util.ImageUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class test {

    //@Test
    public void testRemove() {

        String uri = "http://www.baidu.com/eclipse.java.com/www.baidu.com";
        String contextPath = "www.baidu.com";
        String left = StringUtils.remove(uri, contextPath);

        System.out.println(left);
    }

    //@Test
    public void testResizeImage() throws IOException {

        File pic = new File("E:/2.bmp");
        //ImageUtil.resizeImage(pic , 800, 600, new File("E:/2.jpg"));

        BufferedImage img = ImageUtil.change2jpg(pic);
        ImageIO.write(img, "jpg", new File("E:/3.jpg"));
    }

}
