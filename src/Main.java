import algorithm.Algorithm;

public class Main {

    public static void main(String[] args) {

        long start = System.nanoTime();
        Algorithm algorithm = new Algorithm();
        algorithm.init();
        long elapsedTime = System.nanoTime() - start;
        System.out.println("TIME: " + elapsedTime);

    }
}