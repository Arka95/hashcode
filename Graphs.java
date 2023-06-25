import java.util.Scanner;
import java.io.IOException;
import java.io.File;
import java.util.*;

public class Graphs {

    static void addToAdjacenctList(int src,int dest, LinkedList[] graph){
        if (graph[src]==null)
            graph[src] = new LinkedList<Integer>();
        graph[src].add(dest);
    }

    static LinkedList[] createGraph(String filename, boolean inverted) throws IOException{
        /* Input: a file with #nodes and #edges followed by an Edgelist
           Creates a Graph from a Edgelist(in file) in the most optimal way possible
        *  returns: Array of linkedLists indicating an Adjacency List */
        File file= new File(filename);
        Scanner sc = new Scanner(file);
        int no_verts = sc.nextInt();
        int no_edges = sc.nextInt();
        LinkedList[] graph = new LinkedList[no_verts];
        for (int i=0;i<no_edges;i++){
            int src = sc.nextInt();
            int dest = sc.nextInt();
            if (!inverted)
                addToAdjacenctList(src,dest, graph);
            else
                addToAdjacenctList(dest,src, graph);
        }
        sc.close();
        return graph;
    }

    static void printGraph(LinkedList[] graph){
        /* just a  utility function */
        System.out.println("\n-----------------");
        int n = graph.length;
        for (int i=0;i<n ;i++){
            System.out.print(i+" : ");
            if (graph[i]!=null){
                ListIterator list_Iter = graph[i].listIterator();
                while(list_Iter.hasNext())
                    System.out.print(list_Iter.next()+" ");
                System.out.println();
            }
            else
                System.out.print("no outgoing edges\n");   
        }
        System.out.println("-----------------\n");
    }

    static int counter = 0; 
    static Integer[] topSort(LinkedList[] graph){
        // this topsort will give you a schedule for visitinng nodes in ascending order of time
        // note that this is a serial scdhedule and not parallel
        // we'd need more that topsort to identify parallelizable tasks
        int n = graph.length;
        boolean[] visited = new boolean[n];
        Integer[] result = new Integer[n];
        counter = n-1;
        for(int i=0;i<n;i++)
        {
            if(visited[i]==false)
                dfs(graph, i, visited, result);
        }
        return result;
    }
    static LinkedList[] reorder_graph(int new_indexes[], LinkedList[] graph)
    {
        // reorders the graph based on topsort. This is a basic traversal based topsort.

        int n = graph.length;
        LinkedList[] sortedGraph = new LinkedList[n];
        HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
        for(int i=0;i<n;i++)
            hm.put(new_indexes[i], i);
        for (Map.Entry<Integer, Integer> set : hm.entrySet()) 
        {
            //do something
        }
        return sortedGraph;
    }

    static void dfs(LinkedList[] graph, Integer i, boolean[] visited, Integer result[])
    {
        visited[i] = true;
        if(graph[i]==null){
            result[counter--]=i;
            return;
        }
        ListIterator<Integer> list_Iter = graph[i].listIterator();
        while(list_Iter.hasNext())
        {
            Integer current = list_Iter.next();
            if(visited[current]==false)
                dfs(graph, current, visited, result);
        }
        // add to list when completely visited only
        result[counter--]=i;
    }

    public static void main(String args[]) throws Exception
    {
        LinkedList[] graph = createGraph("graph.txt",false);
        printGraph(graph);
        Integer[] sorted = topSort(graph);
        System.out.println(Arrays.toString(sorted));
    }

}