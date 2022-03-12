package main.java;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void mainM(String[] args) throws InterruptedException {
        BlackAndWhiteMixer mixer = new BlackAndWhiteMixer();
        ExecutorService executorService = Executors.newFixedThreadPool(6);
        for (int i = 0; i < 90; i++) {
            int finalI = i + 1;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    mixer.start("src/resources/numbers/"+ finalI +".png");
                }
            });
        }
        Thread.sleep(50000);
        executorService.shutdown();
    }
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Map<Integer, Integer> result = new HashMap();
        try {
            MyCollable myCollable = new MyCollable("src/resources/numbers/1.png");
            Integer call = myCollable.call();
            System.out.println("1: "+ call);
            /*File file = new File("src/resources/ticket/203205.png");
            BufferedImage source = ImageIO.read(file);
            List<MyCollable> tasks = new ArrayList<>();

//            tasks.add(new MyCollable("src/resources/ticket/1.png"));

            for (int i = 51; i < 71; i++) {
                tasks.add(new MyCollable("src/resources/numbers/"+ i +".png"));
            }
            try {
                List<Future<Integer>> futures = executorService.invokeAll(tasks);
                for (int i = 0; i < 20; i++) {
                    result.put( i + 50,futures.get(i).get());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            result.entrySet().stream()
                    .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                    .forEach(System.out::println);*/
        } catch (IOException e) {
            System.out.println("Файл не найден");
        } catch (Exception e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }

}