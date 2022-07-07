import java.util.*;

class CountMinSketch {

    /*CountMinSketch(CMS) from https://www.youtube.com/watch?v=kx-XDoPjoHw . This is a probabilistic data-structure
    that is a matrix of #hashesFuntion X width (maxModulo is the width) . this can store the approximate frequency
    of a string in limited memory.

    So when do we use it? usually you would store string frequency mapping in a map where every stirng will be mapped
     to its frequency. But Hashmaps will grow bigger and bigger with a larger number of keys , which in real world
     can be infinite. But this CMS can keep that memory constant to |H|x|W|, independent of number of strings. check the
     above link for more info
     */

    // width(W) of the table , usually in 1000s
    int width;
    // a list of number to modulo with. note that the hashValue cannot surpass the width (H)
    int[] hashFunctions;
    // this is the fixed size matrix where we store our values
    int[][] cms;

    CountMinSketch(int numOfHashes, int w){
        width=w;
        hashFunctions = new int[numOfHashes];
        cms = new int[numOfHashes][width];
    }

    void generateHashFunctions(){
        Random rand = new Random();
        int i=0;
        while( i<hashFunctions.length) {
            hashFunctions[i++] = Math.abs(rand.nextInt()) % width;
        }
        System.out.println("Generated hashes :"+ Arrays.toString(hashFunctions));
    }


    /* inserts a word into the cms and returns its approximate count for that word*/
     int insert(String s){
        int i=0, minimumOfAllHashes= Integer.MAX_VALUE;
        for(int hash: hashFunctions) {
            int columnForHash = Math.abs(s.hashCode()) % hash;
            cms[i][columnForHash]++;
            minimumOfAllHashes = Math.min(minimumOfAllHashes, cms[i][columnForHash]);
        }
        return minimumOfAllHashes;
     }


    /* return count of a word based on cms*/
     int getCount(String s){
         int i=0, minimumOfAllHashes= Integer.MAX_VALUE;
         for(int hash: hashFunctions) {
             int columnForHash = Math.abs(s.hashCode()) % hash;
             minimumOfAllHashes = Math.min(minimumOfAllHashes, cms[i][columnForHash]);
         }
         return minimumOfAllHashes;
     }


    public static void main(String[] args) {
        String[] dataset= {"arka","arka","arka","arka","algorithm","instagram","faang","gta", "gta","what","travel","travel"};
        CountMinSketch cms =new CountMinSketch(3,50);
        cms.generateHashFunctions();
        for(String word: dataset){
            System.out.println("current frequency of "+word+" is "+cms.insert(word));
        }

    }

}
