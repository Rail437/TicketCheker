package main.java;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class MyCollable implements Callable<Integer> {
    private List<File> file = List.of(
            new File("src/resources/ticket/203205.png"),
            new File("src/resources/ticket/234506.png"),
            new File("src/resources/ticket/234603.png"));
    private File img;

    public MyCollable(String img) {
        this.img = new File(img);
    }

    @Override
    public Integer call() throws Exception {
        BufferedImage picture = ImageIO.read(img);
        int count = 0;
        for (int i = 0; i < 3; i++) {
            count += findSubimageInt(ImageIO.read(file.get(i)), picture, file.get(i).getName());
            System.out.println(file.get(i).getName() + " проверено. count= " + count);
        }
        return count;
    }

    public static int findSubimageInt(BufferedImage im1, BufferedImage im2, String name) {
        int w1 = im1.getWidth();
        int w2 = im2.getWidth();
        int h1 = im1.getHeight();
        int h2 = im2.getHeight();
        assert (w2 <= w1 && h2 <= h1);
        int count = 0;
        Color newColor = new Color(0, 255, 0);
        BufferedImage result = new BufferedImage(im1.getWidth(), im1.getHeight(), im1.getType());
        // brute-force search through whole image (slow...)
        for (int x = 0; x < w1 - w2; x++) {
            for (int y = 0; y < h1 - h2; y++) {
                result.setRGB(x,y, im1.getRGB(x,y));
                double comp = compareImages(im1.getSubimage(x, y, w2, h2), im2);
                if (comp <= 0.029) {
                    result.setRGB(x, y, newColor.getRGB());
                    count++;
                }
            }
        }

        try {
            File output = new File("copy"+name);
            ImageIO.write(result, "png", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Finds the a region in one image that best matches another, smaller, image.
     */
    public static ArrayList<Integer> findSubimage(BufferedImage im1, BufferedImage im2) {
        int w1 = im1.getWidth();
        int w2 = im2.getWidth();
        int h1 = im1.getHeight();
        int h2 = im2.getHeight();
        assert (w2 <= w1 && h2 <= h1);
        int bestX;
        int bestY;
        double lowestDiff = Double.POSITIVE_INFINITY;
        ArrayList<Integer> result = new ArrayList<>();
        // brute-force search through whole image (slow...)
        for (int x = 0; x < w1 - w2; x++) {
            for (int y = 0; y < h1 - h2; y++) {
                double comp = compareImages(im1.getSubimage(x, y, w2, h2), im2);
                if (comp <= 0.02) {
                    bestX = x;
                    bestY = y;
                    System.out.println("bestX: " + bestX + " bestY: " + bestY + " comp: " + comp);
                    result.add(bestX);
                    result.add(bestY);
                }
                if (comp < lowestDiff) {
                    lowestDiff = comp;
                }
            }
        }
        // output similarity measure from 0 to 1, with 0 being identical
        return result;
    }

    /**
     * Determines how different two identically sized regions are.
     */

    public static double compareImages(BufferedImage im1, BufferedImage im2) {
        assert (im1.getHeight() == im2.getHeight() && im1.getWidth() == im2.getWidth());
        double variation = 0.0;
        for (int x = 0; x < im1.getWidth(); x++) {
            for (int y = 0; y < im1.getHeight(); y++) {
                variation += compareARGB(im1.getRGB(x, y), im2.getRGB(x, y)) / Math.sqrt(2);
            }
        }
        return variation / (im1.getWidth() * im1.getHeight());
    }

    /**
     * Calculates the difference between two ARGB colours (BufferedImage.TYPE_INT_ARGB).
     */
    public static double compareARGB(int rgb1, int rgb2) {
        double r1 = ((rgb1 >> 16) & 0xFF) / 255.0;
        double r2 = ((rgb2 >> 16) & 0xFF) / 255.0;
        double g1 = ((rgb1 >> 8) & 0xFF) / 255.0;
        double g2 = ((rgb2 >> 8) & 0xFF) / 255.0;
        double b1 = (rgb1 & 0xFF) / 255.0;
        double b2 = (rgb2 & 0xFF) / 255.0;
        double a1 = ((rgb1 >> 24) & 0xFF) / 255.0;
        double a2 = ((rgb2 >> 24) & 0xFF) / 255.0;
        // if there is transparency, the alpha values will make difference smaller
        return a1 * a2 * Math.sqrt((r1 - r2) * (r1 - r2) + (g1 - g2) * (g1 - g2) + (b1 - b2) * (b1 - b2));
    }
}
