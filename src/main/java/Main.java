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

            for (int x = 0; x < source.getWidth(); x++) {
                for (int y = 0; y < source.getHeight(); y++) {
                    Color firstPixel = new Color(source.getRGB(x, y));
                    if (new Color(one.getRGB(0,0)).equals(firstPixel)) {

                        for (int i = 0; i < one.getWidth(); i++) {
                            for (int j = 0; j < one.getHeight();j++) {
                                if(x + i < source.getWidth() & y + j < source.getHeight()) {
                                    if(!new Color(one.getRGB(i,j)).equals(firstPixel)){
                                        break;
                                    }
                                    if (i == 15 && j == 15 & new Color(one.getRGB(i,j)).equals(firstPixel)) {
                                        Color newColor = new Color(0, 200, 0);
                                        result.setRGB(i + x, j + y, newColor.getRGB());
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
