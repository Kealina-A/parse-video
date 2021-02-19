import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import org.bytedeco.javacv.FrameGrabber.Exception;

public class VideoUtil {

    public static void main(String[] args) {
        //1.要读取的视频文件路径
        String videoPath = "./test.mp4";
        //2.图片要保存的目录位置
        String picPath = "./pic/";

        File video = new File(videoPath);
        getVideoPic(video, picPath);
    }

    /**
     * 根据读取到的视频文件，获取视频中的每一帧图片
     * @param video 视频文件
     * @param picPath 图片的保存路径
     */
    public static void getVideoPic(File video,String picPath){
        //1.根据一个视频文件，创建一个按照视频中每一帧进行抓取图片的抓取对象
        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(video);
        try {
            ff.start();
            //抓每一帧图片
            //2.先知道这个视频一共有多少帧
            int length = ff.getLengthInFrames();
//            System.out.println(length);
            //3.读取视频中每一帧图片
            Frame frame = null;
            for(int i=0;i<length;i++){
                frame = ff.grabFrame();
//        System.out.println(frame);
                if(frame.image == null){
//                    System.out.println("空"+i);
                    continue;
                }
                //将获取的帧，存储为图片
                Java2DFrameConverter converter = new Java2DFrameConverter();//创建一个帧-->图片的转换器
                BufferedImage image = converter.getBufferedImage(frame);//转换
                String img = picPath+i+".jpg";
//                System.out.println(img);
                File picFile = new File(img);
                //将图片保存到目标文件中
                ImageIO.write(image, "jpg", picFile);
            }


            ff.stop();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
