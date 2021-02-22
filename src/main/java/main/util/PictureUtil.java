package main.util;

import java.awt.image.BufferedImage;

public class PictureUtil {


    public static void reverseColor(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int r = img.getRGB(x, y);

                int red = (r >> 16) & 0x0ff;
                int green = (r >> 8) & 0x0ff;
                int blue = r & 0x0ff;

                red = 255 - red;
                green = 255 - green;
                blue = 255 - blue;

                r = (red << 16) | (green << 8) | blue;
                img.setRGB(x, y, r);
            }
        }
    }
}
