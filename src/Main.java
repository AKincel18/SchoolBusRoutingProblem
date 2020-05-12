import algorithm.MainAlgorithm;
import algorithm.bruteforce.BruteForceAlgorithm;
import algorithm.nearestneighbour.NearestNeighbourAlgorithm;

public class Main {

    public static void main(String[] args) {

        bruteForceAlgorithm();
        nearestNeighbourAlgorithm();

    }

    private static void bruteForceAlgorithm(){
        long start = System.nanoTime();
        MainAlgorithm bruteForceAlgorithm = new BruteForceAlgorithm();
        bruteForceAlgorithm.startAlgorithm();
        long elapsedTime = System.nanoTime() - start;
        System.out.println("bruteForceAlgorithm time: " + elapsedTime);
        bruteForceAlgorithm.printSumOfDistance();
    }

    private static void nearestNeighbourAlgorithm(){
        long start = System.nanoTime();
        MainAlgorithm nearestNeighbourAlgorithm = new NearestNeighbourAlgorithm();
        nearestNeighbourAlgorithm.startAlgorithm();
        long elapsedTime = System.nanoTime() - start;
        System.out.println("nearestNeighbourAlgorithm time: " + elapsedTime);
        nearestNeighbourAlgorithm.printSumOfDistance();
    }
}