import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String videoPath = "./1613836003205777.mp4";
    private static final String picPath = "./pic/";
    private static final String targetFile = "./xx.txt";

    public static void main(String[] args) throws IOException {

        long start =System.currentTimeMillis();

        VideoUtil.grabberVideoFramer(videoPath, picPath);
        System.out.printf("解析视频耗时：%d ms",System.currentTimeMillis()-start);

        List<String> dataArr = getDataArr();
        String hex = convert2HexStr(dataArr);
        convertHex2File(hex);
        System.out.printf("总共耗时：%d ms",System.currentTimeMillis()-start);
    }

    /**
     * 获取数据数组
     */
    private static List<String> getDataArr () {
        List<String> files = FileUtil.getFiles(picPath);
        List<String> dataArr = new ArrayList<>();
        for (String file : files) {
            String read = QrcodeUtil.read(file);
            if ("".equals(read)) {
                continue;
            }
            if (dataArr.contains(read)) {
                continue;
            }
            dataArr.add(read);
        }
        return dataArr;
    }

    /**
     * 将16进制数据拼接成完整的数据
     */
    private static String convert2HexStr(List<String> dataArr) {
        StringBuilder hex = new StringBuilder();
        for (String s : dataArr) {
            String[] split = s.split("\\|");
            String data = split[0];
            verifyData(data,split[2]);
            hex.append(data);
        }
        return hex.toString();
    }

    /**
     * 将16进制内容转成文件
     */
    private static void convertHex2File (String hex) {
        byte[] bytes = HexUtil.hex2Bytes(hex);
        try (FileOutputStream stream = new FileOutputStream(Main.targetFile)) {
            stream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 验证crc数据
     */
    public static int crc1 = 0xff;
    private static void verifyData(String hex,String crcStr) {
        int crc = Integer.parseInt(crcStr);

        int count= 0 ;

        while(count<hex.length()){
            int result = Integer.parseInt(hex.substring(count, count + 2),16);
            count+=2;
            crc1 ^= result;
        }
        if (crc1!=crc) {
            System.out.printf("crc错误 data: %s crc: %d",hex,crc1);
            throw new RuntimeException("crc错误");
        }
    }

}
