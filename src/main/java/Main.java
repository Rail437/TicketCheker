package main.java;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            File file1 = new File("src/resources/ticket/2.png");
            BufferedImage one = ImageIO.read(file1);

            File file = new File("src/resources/ticket/small.png");
            BufferedImage source = ImageIO.read(file);
            BufferedImage result = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
            File output = new File("src/resources/ticket/small2.png");
            Color[][] bufferArr = new Color[16][16];
            for (int i = 0; i < one.getWidth(); i++) {
                for (int j = 0; j < one.getHeight(); j++) {
                    bufferArr[i][j] = new Color(one.getRGB(i,j));
                }
            }

            for (int x = 0; x < source.getWidth(); x++) {
                for (int y = 0; y < source.getHeight(); y++) {
                    Color firstPixel = new Color(source.getRGB(x, y));
                    if (bufferArr[0][0].equals(firstPixel)) {
                        int buffX = x;
                        int buffY = y;

                        for (int i = 0; i < one.getWidth(); i++) {
                            for (int j = 0; j < one.getHeight();j++) {
                                if(buffX + i < source.getWidth() & buffY + j < source.getHeight()) {
                                    if(!bufferArr[i][j].equals(firstPixel)){
                                        break;
                                    }
                                    if (i == 15 && j == 15 & bufferArr[i][j].equals(firstPixel)) {
                                        Color newColor = new Color(0, 200, 0);
                                        result.setRGB(i + buffX, j + buffY, newColor.getRGB());
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            ImageIO.write(result, "png", output);
        } catch (IOException e) {
            System.out.println("Файл не найден");
        }
    }
}
