import java.util.*;
import static java.lang.Thread.sleep;

/*
FAANG: HARD interview question (45min) for SDE 2/3:

Design a website url hit counter. Website has multiple urls, return a function getHitCountWithinLastXMinutes(url, x). 
Simple soln: HashMap<String, LinkedList<Long>> with linked list (O(1) insertion and O(n) retrieval) with assumption that acceses are increasing order of timestamp
What if it is unsorted ? Think of better ways 
Best Soln: ( Hashmap with TreeMap<Long (timestamp), Integer(frequency)> tree O(logN) insertion and O(LogN) retrieval)
(TreeMap uses self balancing RB trees. Whenever there are duplicate timestamp recs, increase Frequency of that)


Follow up questions: This is obviously not threadsafe. How can you make it threadsafe while writes ? (synchronize during Treeset add )
*/



class HitCounter {

    // a datastucture that holds #access of a url at that timestamp
    TreeMap<Long, Integer> ts;

    HitCounter() {
        ts = new TreeMap<>();
    }

    int add(Long t) {
        // increase count at that timestamp for concurrent accesses
        Integer c = this.ts.get(t);
        if (c == null) c = 0;
        this.ts.put(t, c + 1);
        return c + 1;
    }

    static int getTotalHits(SortedMap<Long, Integer> subMap){
        int s = 0;
        for(int i :subMap.values())
            s+=i;
        return s;
    }
}

class WebServerSingleton{
    // Good Case (access | hitcount): Olog1 N
    // Best case (access | hitcount): OlogN OlogN

    HashMap<String, HitCounter> hitsForUrl;
    static WebServerSingleton instance;

    // BONUS Points if he interviewee uses singleton
    private WebServerSingleton(){
        hitsForUrl= new HashMap<String, HitCounter>();
    }

    public static WebServerSingleton getInstance()
    {
        if (instance==null)
            instance = new WebServerSingleton();
        return instance;
    }

    void access(String url){
        // O Log(N) as TreeMap uses RB trees for insertion
        // Question: if access >> getHitsInLastXMinutes , should we make access O(1) and getHitsInLastXMinutes O(N) ?
        HitCounter current = hitsForUrl.get(url);
        if(current==null)
            current = new HitCounter();
        Long currentTimestamp = new Date().getTime();
        current.add(currentTimestamp);
        hitsForUrl.put(url, current);
    }

    static Long toMillis(int minutes){
        return (long)minutes*60*1000;
    }

    int getHitsInLastXMinutes(String url , int x){
        // O Log(N) as we use TreeMap for HitCounter
        HitCounter current = hitsForUrl.get(url);

        if(current==null)return 0; // url doesnt exist

        Long targetTime = new Date().getTime() - toMillis(x);
        // if targetTime not in treeset, find the value just greater than targetTime
        targetTime = current.ts.ceilingKey(targetTime);

        // return number of nodes in subtree starting from that treeset node
        SortedMap<Long, Integer> allRecsGreaterThanEqualToTarget = current.ts.tailMap(targetTime);
     return HitCounter.getTotalHits(allRecsGreaterThanEqualToTarget);
    }

}

class Main {

    public static void main(String[] args) throws Exception{
        WebServerSingleton ws = WebServerSingleton.getInstance();
        ws.access("abcd.com");
        ws.access("abcd.com");
        ws.access("xyz.com");
        ws.access("fhk.com");
        ws.access("lll.com");
        Thread.sleep(1000);
        System.out.println(ws.getHitsInLastXMinutes("abcd.com",1)); //expected 2

        ws.access("xyz.com");
        ws.access("fhk.com");
        ws.access("lll.com");
        System.out.println(ws.getHitsInLastXMinutes("abcd.com",1)); //expected 2
    }
}
