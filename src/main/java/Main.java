import java.io.File;
import java.util.ArrayList;

public class Main {

    private static final String videoPath = "./test.mp4";
    private static final String picPath = "./";

    public static void main(String[] args) {

        File video = new File(videoPath);
        VideoUtil.getVideoPic(video, picPath);

        ArrayList<String> files = FileUtil.getFiles(picPath);
        for (String file : files) {
            QrcodeUtil.read(file);
        }
    }


}
