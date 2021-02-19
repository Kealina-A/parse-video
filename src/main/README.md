# 反解析视频里的二维码

## 运行步骤
1. 准备一个已经录制好的视频，放到该项目目录下
2. 修改main里的路径信息。
3. 运行

## 解析逻辑
1. 将视频解析成一帧一帧保存成图片
2. 将图片（二维码的解析，非二维码或者无法解析的忽略）解析成内容拼接(内容的16进制的字符串) 
3. 处理内容(16进制字符串转二进制)，写成文件。

ps: 视频图片可能出现缺失图或者重复图，程序做好判断过滤即可

## 项目说明 
- 使用相应依赖
```
 <dependencies>
        <!-- 解析视频相应的包　-->
        <dependency>
            <groupId>org.bytedeco</groupId>
            <artifactId>javacv</artifactId>
            <version>1.4.3</version>
        </dependency>
        <dependency>
            <groupId>org.bytedeco.javacpp-presets</groupId>
            <artifactId>ffmpeg-platform</artifactId>
            <version>4.0.2-1.4.3</version>
        </dependency>

        <!-- 二维码相应包　-->
        <dependency>
            <groupId>com.google.zxing</groupId>
            <artifactId>core</artifactId>
            <version>3.4.1</version>
        </dependency>
        <dependency>
            <groupId>com.google.zxing</groupId>
            <artifactId>javase</artifactId>
            <version>3.3.3</version>
        </dependency>
    </dependencies>
```
