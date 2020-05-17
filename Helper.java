import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Helper{

    public static boolean[] init_sieve(int n){
        // classical sieve algorithm that returns is_prime array 
        // complexity: O(Nlog(log(N)))
        boolean []is_prime = new boolean[n+1];
        Arrays.fill(is_prime, true);
        for(int i=2;i*i<=n;i++)
        {
            if (is_prime[i])
                for(int j=i; i*j<=n; j++)
                    is_prime[i*j] = false;
        }
        return is_prime;
    }

    public static void print_arr(Integer arr[]){
        /*prints an array like: 1 : 90 | 2 : 89 | 3 : 30 |*/
        for(int i=0;i<arr.length;i++)
            System.out.printf("%d : %d | ",i,arr[i]);
            System.out.println();
    }

    public static Integer[] index_sort(Object arr[], IndexComparator comparator){
        // returns a new array with indices of initial array  based on array values sorted
        // complexity: O(Nlog(N))
        int n = arr.length;
        Integer []sorted_indices= new Integer[n];
        for(int i=0; i<n; i++)
            sorted_indices [i] = i;
        Arrays.sort(sorted_indices, comparator);
        return sorted_indices;
    }

    public static Integer[] index_sort(Integer arr[], IntComparator comparator){
        // returns int array with indices of initial int array  based on array values sorted
        // complexity: O(Nlog(N))
        int n = arr.length;
        Integer []sorted_indices= new Integer[n];
        for(int i=0; i<n; i++)
            sorted_indices [i] = i;
        Arrays.sort(sorted_indices, comparator);
        return sorted_indices;
    }

    public static void main(String args[])
    {
        boolean []is_prime = init_sieve(100);
        Integer []arr = {89,26,890,1,-5};
        Integer []sorted = index_sort(arr, new IntComparator(arr));
        print_arr(sorted);
    }
}

class IntComparator implements Comparator<Integer> {
    int []arr = null;
    IntComparator(Integer[] test_arr){
        // Complexity: O(N)
        int n = test_arr.length;
        this.arr = new int[n];
        for(int i=0;i<n;i++)
            this.arr[i] = test_arr[i];
    }
    @Override
    public int compare(Integer index_a, Integer index_b) {
        return this.arr[index_a] -  this.arr[index_b];
    }
}

abstract class IndexComparator implements Comparator<Integer> {
    static Object []arr = null;
    IndexComparator(Object[] test_arr){
        // Complexity: O(N)
        int n = arr.length;
        arr = new Object[n];
        for(int i=0;i<n;i++)
            arr[i] = test_arr[i];
    }
    @Override
    public int compare(Integer index_a, Integer index_b) {
        return 0;
    }
}
