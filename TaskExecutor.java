import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
// parallelize a set of tasks which have chain depdendencies on eachother. a classic case will be this:
/* THE BEST CORNER CASE, ( lets assume all the nodes belong to one component)
A graph that forms an X sign with left diagonal having (m+n) (\) and right diagonal having (i+j) (/) nodes
The left diagonal has m elements before intersecting with right diagonal
The right diagonal has i elements before intersecting left diagonal
collectively (m+n)>(i+j) thus making left diagonal (\) the longest depth in the graph

We want to base our discovery times based on the longest depth left diagonal BUT this is an isse
consider this case :(m+n)>(i+j) but then may be i>m . Assuming both lines meet at pt C
THE discovery time for C as calcuated by us will be m. But in reality it is i

OH OH OH , lets not forget about the diamond problem <3 ^_^ LIFE IS HARD BRO
*/

public class TaskExecutor {

    static int time=0;
    static void organizeTasks(String dependencyFileName) {
        // load the dependency file ( inverted graph )
        // the file should be like this: destination depends on source (inverted)
        // hence in adjacency list representation would be like this source->[destination(s)]
        LinkedList[] predecessors, successors;
        try {
            successors = Graphs.createGraph(dependencyFileName, true); //source->[destination(s)]
            predecessors = Graphs.createGraph(dependencyFileName, false);
            // find components treating graphs as bi-directional an using dfs
            // this way we can be sure that in the next loop of dfs, we will only get a new graph not in current visited set
            Graphs.printGraph(predecessors);
            Graphs.printGraph(successors);
        }catch (IOException e){
            System.out.println("Couldn't cerate dependency graph due to"+ e.getMessage());
        }
    }


    void graphStats(LinkedList[] uniDirectedGraph){
        // utilizes a graph that is bidireted based on this graph
        // returns the longest chain of the graph along with nodes in list
    }

    static HashMap<Integer, LinkedList<Integer>> parallelizableTopsort(LinkedList[] graph){
        HashMap<Integer, LinkedList<Integer>> completed = new HashMap<>();
        boolean[] visited = new boolean[graph.length];
        // find all spearate components treating graphs as undirected
        // find longest chain of items for every graph that belongs to same component
        // based on longest chain, order the children and parent of other items
        // now to deal with whatever remains
        /*--------------------------------------------COMPLETE THIS
        for(int i =0;i<graph.length;i++){
            if(!visited[i]){
                time = graph.length;
                dfs(i, graph, visited, completed);
            }
        }
        ------------------------------------------------------------*/
        return completed;
    }

    static void dfs(int i,LinkedList[] graph, boolean[] visited,  HashMap<Integer, LinkedList<Integer>> completed){
        visited[i]= true;
        LinkedList<Integer> neighbors = graph[i];
        if(neighbors!=null) {
            for (Integer neighbor : neighbors) {
                if (!visited[neighbor]) {
                    dfs(neighbor, graph, visited, completed);
                }
            }
        }
        // when the node is completely visited, we can add it to completed
        time--;
        LinkedList<Integer> parallelItems = completed.getOrDefault(time, new LinkedList<Integer>());
        parallelItems.add(i);
        completed.put(time,parallelItems);
    }

    public static void main(String[] args) {
        organizeTasks("dependencyGraph.txt");
    }

}
