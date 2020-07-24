import java.util.Scanner;
import java.io.IOException;
import java.io.File;
import java.util.*;

public class Graphs {

    static LinkedList[] createGraph(String filename) throws IOException{
        File file= new File(filename);
        Scanner sc = new Scanner(file);
        int no_verts = sc.nextInt();
        int no_edges = sc.nextInt();
        LinkedList[] graph = new LinkedList[no_verts];
        for (int i=0;i<no_edges;i++){
            int src = sc.nextInt();
            if (graph[src]==null)
                graph[src] = new LinkedList<Integer>();
            graph[src].add(sc.nextInt());
        }
        sc.close();
        return graph;
    }

    static void printGraph(LinkedList[] graph){
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
        result[counter--]=i;
    }

    public static void main(String args[]) throws Exception
    {
        LinkedList[] graph = createGraph("graph.txt");
        printGraph(graph);
        Integer[] sorted = topSort(graph);
        System.out.println(Arrays.toString(sorted));
    }

}