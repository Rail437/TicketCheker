package main.java;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        Map<Integer, Integer> result = new HashMap();
        try {
            File file = new File("src/resources/ticket/203205.png");
            BufferedImage source = ImageIO.read(file);
            List<MyCollable> tasks = new ArrayList<>();

            tasks.add(new MyCollable("src/resources/ticket/1.png"));
            tasks.add(new MyCollable("src/resources/ticket/2.png"));
            tasks.add(new MyCollable("src/resources/ticket/3.png"));
            tasks.add(new MyCollable("src/resources/ticket/4.png"));
            tasks.add(new MyCollable("src/resources/ticket/5.png"));
            tasks.add(new MyCollable("src/resources/ticket/6.png"));
            tasks.add(new MyCollable("src/resources/ticket/7.png"));
            tasks.add(new MyCollable("src/resources/ticket/8.png"));
            tasks.add(new MyCollable("src/resources/ticket/9.png"));
            tasks.add(new MyCollable("src/resources/ticket/10.png"));
            tasks.add(new MyCollable("src/resources/ticket/11.png"));
            tasks.add(new MyCollable("src/resources/ticket/12.png"));
            tasks.add(new MyCollable("src/resources/ticket/13.png"));
            tasks.add(new MyCollable("src/resources/ticket/14.png"));
            tasks.add(new MyCollable("src/resources/ticket/15.png"));
            tasks.add(new MyCollable("src/resources/ticket/16.png"));
            tasks.add(new MyCollable("src/resources/ticket/17.png"));
            tasks.add(new MyCollable("src/resources/ticket/18.png"));
            tasks.add(new MyCollable("src/resources/ticket/19.png"));
            tasks.add(new MyCollable("src/resources/ticket/20.png"));

            try {
                List<Future<Integer>> futures = executorService.invokeAll(tasks);

                for (int i = 0; i < 20; i++) {
                    result.put( i + 1,futures.get(i).get());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            result.entrySet().stream()
                    .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                    .forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("Файл не найден");
        }
        executorService.shutdown();
    }

}
/*

    BufferedImage result = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
    File output = new File("src/resources/ticket/small2.png");

    Color newColor = new Color(0, 200, 0);

            for (int x = 0; x < source.getWidth(); x++) {
        for (int y = 0; y < source.getHeight(); y++) {
        result.setRGB(x,y, source.getRGB(x,y));
        }
        }
        ArrayList<Integer> rr = findSubimage(source,one);
        for (int i = 0; i < rr.size(); i+=2) {
        result.setRGB(rr.get(i),rr.get(i+1),newColor.getRGB());
        }

        ImageIO.write(result, "png", output);
*/
