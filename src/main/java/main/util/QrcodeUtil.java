package main.util;

import com.google.zxing.*;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QrcodeUtil {

    public static String read(String path) {
        try {
            //指定二维码路径
            BufferedImage image = ImageIO.read(new File(path));
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
            //二维码相关参数
            Map<DecodeHintType, Object> hints = new HashMap<>();
            hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
            Result result = new MultiFormatReader().decode(binaryBitmap, hints);

            //输出解析结果
//            System.out.println("解析结果("+path+")： " + result.toString());
//            System.out.println("二维码格式:" + result.getBarcodeFormat());
            System.out.println("二维码文本内容("+path+"):" + result.getText());
            return result.getText();
        } catch (NotFoundException e) {
            System.out.println("不可解析文件("+path+")");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) throws IOException {
        read("./pic/101.jpg");
    }
}
