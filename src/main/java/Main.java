import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String videoPath = "./IMG_8717.MOV";
    private static final String picPath = "./pic/";

    public static void main(String[] args) throws IOException {

        VideoUtil.grabberVideoFramer(videoPath, picPath);

        List<String> files = FileUtil.getFiles(picPath);
        List<String> x = new ArrayList<>();
        for (String file : files) {
            String read = QrcodeUtil.read(file);
            if ("".equals(read)) {
                continue;
            }
            if (x.contains(read)) {
                continue;
            }
            x.add(read);
        }

        StringBuffer hex = new StringBuffer();
        for (String s : x) {
            hex.append(s);
        }


        byte[] bytes = HexUtil.hex2Bytes(hex.toString());
        try (FileOutputStream stream = new FileOutputStream("./xx.txt")) {
            stream.write(bytes);
        }
    }

}
