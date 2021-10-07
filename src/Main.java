import algorithm.MainAlgorithm;
import algorithm.bruteforce.BruteForceAlgorithm;
import algorithm.nearestneighbour.NearestNeighbourAlgorithm;

public class Main {

    private static final MainAlgorithm bruteForceAlgorithm = new BruteForceAlgorithm();
    private static final MainAlgorithm nearestNeighbourAlgorithm = new NearestNeighbourAlgorithm();

    public static void main(String[] args) {

        bruteForceAlgorithm.startAlgorithm();
        nearestNeighbourAlgorithm.startAlgorithm();

        bruteForceAlgorithm.printSumOfDistance();
        nearestNeighbourAlgorithm.printSumOfDistance();

        bruteForceAlgorithm.printAlgorithmTime();
        nearestNeighbourAlgorithm.printAlgorithmTime();
    }
}