package algorithm;

import model.Pupil;

import java.util.*;

public class Permutation {

    private List<List<Pupil>> permutation = new ArrayList<>();

    //private Integer sum =0;

    public Permutation(List<Pupil> a, int size) {
        heapPermutation(a, size, size);
    }

    public List<List<Pupil>> getPermutation() {
        return permutation;
    }

    //Generating permutation using Heap Algorithm
    public void heapPermutation(List<Pupil> a, int size, int n)
    {
        // if size becomes 1 then prints the obtained
        // permutation
        if (size == 1) {
            permutation.add(new ArrayList<>(a));
            //System.out.println(sum); sum++;
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