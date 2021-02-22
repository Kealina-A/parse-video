package main;


import main.util.FileUtil;
import main.util.HexUtil;
import main.util.QrcodeUtil;
import main.util.VideoUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static final String videoPath = "./IMG_8744.MOV";
    private static final String picPath = "./pic/";
    private static final String targetFile = "./xx.txt";

    public static void main(String[] args) {

        long start =System.currentTimeMillis();
        FileUtil.deleteFile(picPath);
        FileUtil.createDir(picPath);

        VideoUtil.grabberVideoFramer(videoPath, picPath);
        println("解析视频耗时：%d ms",(Long)(System.currentTimeMillis()-start));

        List<String> dataArr = getDataArr();
        String hex = convert2HexStr(dataArr);
        convertHex2File(hex);
        println("总共耗时：%d ms",(Long)(System.currentTimeMillis()-start));
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
        System.out.println("解析出来的所有数据");
        for (String s : dataArr) {
            System.out.println(s);
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
            verifyData(split);
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
    private static void verifyData(String[] splits) {
        try {
            String hex = splits[0];
            String num = splits[1];
            String crcStr = splits[2];

            //　验证crc
            int crc = Integer.parseInt(crcStr);
            int count= 0 ;
            while(count<hex.length()){
                int result = Integer.parseInt(hex.substring(count, count + 2),16);
                count+=2;
                crc1 ^= result;
            }
            if (crc1!=crc) {
                println("crc错误 原数据：hex[%s],num:[%s],crc:[%s], 求得的：crc[%d] ",hex,num,crcStr, Integer.valueOf(crc1));
//            throw new RuntimeException("crc错误");
            }
        } catch(Exception e) {
            //　验证序号
            System.out.println("解析数据错误 "+ Arrays.toString(splits));
        }

    }

    private static void println(String var1, Object... var2) {
        System.out.printf(var1,var2);
        System.out.println();
    }
}
