import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VideoUtil {

    /**
     * 将视频文件帧处理并以“jpg”格式进行存储。
     * 依赖FrameToBufferedImage方法：将frame转换为bufferedImage对象
     *
     * @param videoFileName（视频文件的路径）
     */
    public static void grabberVideoFramer(String videoFileName, String picPath) {
        //Frame对象
        Frame frame = null;
        //标识
        int flag = 0;

        FFmpegFrameGrabber fFmpegFrameGrabber = new FFmpegFrameGrabber(videoFileName);
        try {
            //开始转换
            fFmpegFrameGrabber.start();
            int ftp = fFmpegFrameGrabber.getLengthInFrames();
            while (flag <= ftp) {
                //文件绝对路径+名字
                String fileName = picPath + flag + ".jpg";
                //文件储存对象
                File outPut = new File(fileName);
                //获取帧
                try {
                    frame = fFmpegFrameGrabber.grabImage();
                } catch (FrameGrabber.Exception e) {
                    e.printStackTrace();
                }
                //根据条件生产图片flag值可以任意修改，根据1s=24帧或者30帧
                if (frame != null) {
                    BufferedImage bufferedImage = frameToBufferedImage(frame);
                    PictureUtil.reverseColor(bufferedImage);
                    ImageIO.write(bufferedImage, "jpg", outPut);
                    System.out.println("保存成功 "+fileName);
                }
                flag++;
            }
            //  转换结束
            fFmpegFrameGrabber.stop();
        } catch (IOException ignored) { }
    }

    /**
     * 创建BufferedImage对象
     */
    public static BufferedImage frameToBufferedImage(Frame frame) {
        Java2DFrameConverter converter = new Java2DFrameConverter();
        return converter.getBufferedImage(frame);
    }

}
