import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class QrcodeUtil {

    public static String read(String path) {
        try {
            MultiFormatReader multiFormatReader = new MultiFormatReader();

            //指定二维码路径
            File file = new File(path);
            BufferedImage image = ImageIO.read(file);

            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));

            //二维码相关参数
            HashMap hints = new HashMap();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

            Result result = multiFormatReader.decode(binaryBitmap, hints);

            //输出解析结果
            System.out.println("解析结果("+path+")： " + result.toString());
//            System.out.println("二维码格式:" + result.getBarcodeFormat());
//            System.out.println("二维码文本内容：" + result.getText());
            return result.getText();
        } catch (NotFoundException e) {
//            System.out.println("不可解析文件");
//            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        read("./0.jpg");
    }
}
