package algorithm;

import java.util.*;

public class Permutation {

    private List<List<Integer>> permutation = new ArrayList<>();

    public Permutation(List<Integer> a, int size, int n) {
        heapPermutation(a, size, n);
    }

    public List<List<Integer>> getPermutation() {
        return permutation;
    }

    //Generating permutation using Heap Algorithm
    public void heapPermutation(List<Integer> a, int size, int n)
    {
        // if size becomes 1 then prints the obtained
        // permutation
        if (size == 1) {
            permutation.add(new ArrayList<>(a));
        }



        for (int i=0; i<size; i++)
        {
            heapPermutation(a, size-1, n);

            // if size is odd, swap first and last
            // element
            if (size % 2 == 1)
            {
                Collections.swap(a, 0, size - 1);
            }

            // If size is even, swap ith and last
            // element
            else
            {
                Collections.swap(a, i, size - 1);
            }
        }
    }


}