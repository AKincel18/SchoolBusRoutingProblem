import algorithm.MainAlgorithm;

public class Main {

    public static void main(String[] args) {

        long start = System.nanoTime();
        MainAlgorithm mainAlgorithm = new MainAlgorithm();
        mainAlgorithm.bruteForceAlgorithm();
        long elapsedTime = System.nanoTime() - start;
        System.out.println("TIME: " + elapsedTime);

    }
}