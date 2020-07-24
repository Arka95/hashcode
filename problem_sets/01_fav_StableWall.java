import java.util.*;
import java.io.*;

/*
Problem
Apollo is playing a game involving polyominos. A polyomino is a shape made by joining together one or more squares edge to edge to form a single connected shape. The game involves combining N polyominos into a single rectangular shape without any holes. Each polyomino is labeled with a unique character from A to Z.
Apollo has finished the game and created a rectangular wall containing R rows and C columns. He took a picture and sent it to his friend Selene. Selene likes pictures of walls, but she likes them even more if they are stable walls. A wall is stable if it can be created by adding polyominos one at a time to the wall so that each polyomino is always supported. A polyomino is supported if each of its squares is either on the ground, or has another square below it.
Apollo would like to check if his wall is stable and if it is, prove that fact to Selene by telling her the order in which he added the polyominos.
Input
The first line of the input gives the number of test cases, T. T test cases follow. Each test case begins with a line containing the two integers R and C. Then, R lines follow, describing the wall from top to bottom. Each line contains a string of C uppercase characters from A to Z, describing that row of the wall.
Output
For each test case, output one line containing Case #x: y, where x is the test case number (starting from 1) and y is a string of N uppercase characters, describing the order in which he built them. If there is more than one such order, output any of them. If the wall is not stable, output -1 instead.
Limits
Time limit: 20 seconds per test set.
Memory limit: 1GB.
1 ≤ T ≤ 100.
1 ≤ R ≤ 30.
1 ≤ C ≤ 30.
No two polyominos will be labeled with the same letter.
The input is guaranteed to be valid according to the rules described in the statement.
Test set 1
1 ≤ N ≤ 5.
Test set 2
1 ≤ N ≤ 26.
Sample
Input
 	
Output
 
4
4 6
ZOAAMM
ZOAOMM
ZOOOOM
ZZZZOM
4 4
XXOO
XFFO
XFXO
XXXO
5 3
XXX
XPX
XXX
XJX
XXX
3 10
AAABBCCDDE
AABBCCDDEE
AABBCCDDEE
  
Case #1: ZOAM
Case #2: -1
Case #3: -1
Case #4: EDCBA
  
In sample case #1, note that ZOMA is another possible answer.
In sample case #2 and sample case #3, the wall is not stable, so the answer is -1.
In sample case #4, the only possible answer is EDCBA.
 */

public class Solution{

    public static String do_something(char [][]mat, int r, int c){

      HashMap<Character, Node> graph = create_graph(mat, r ,c);
      return topsort(graph);
    }

    public static String topsort(HashMap<Character, Node> graph)
    {
      //L <- Empty list that will contain the sorted elements
      ArrayList<Node> L = new ArrayList<Node>();

      //S <- Set of all nodes with no incoming edges
      HashSet<Node> S = new HashSet<Node>(); 
      for(Node n : graph.values()){
        if(n.inEdges.size() == 0){
          S.add(n);
        }
      }

      //while S is non-empty do
      while(!S.isEmpty()){
        //remove a node n from S
        Node n = S.iterator().next();
        S.remove(n);

        //insert n into L
        L.add(n);

        //for each node m with an edge e from n to m do
        for(Iterator<Edge> it = n.outEdges.iterator();it.hasNext();){
          //remove edge e from the graph
          Edge e = it.next();
          Node m = e.to;
          it.remove();//Remove edge from n
          m.inEdges.remove(e);//Remove edge from m

          //if m has no other incoming edges then insert m into S
          if(m.inEdges.isEmpty()){
            S.add(m);
          }
        }
      }
      //Check to see if all edges are removed
      boolean cycle = false;
      for(Node n : graph.values()){
        if(!n.inEdges.isEmpty()){
          cycle = true;
          break;
        }
      }
      if(cycle)
        return "-1";
      return Arrays.toString(L.toArray());
    }

    public static HashMap<Character, Node> create_graph(char[][]mat,int r,int c){
      HashMap<Character, Node> graph=new HashMap<Character, Node>();
      for(int j=0;j<c;j++)// current column
      {
        for(int i=0;i<r;i++)// current row
        {
          Character alph=mat[i][j];
          Character previous_alph= mat[i-1][j];
          Node current_node = graph.get(alph);
          if (current_node==null){
            current_node = new Node(alph);
              graph.put(alph, current_node);
          }
          if(i>0 && previous_alph!=alph)
            current_node.addEdge(graph.get(previous_alph));
        }
      }
      return graph;
    }

    static class Node{
      public final Character name;
      public final HashSet<Edge> inEdges;
      public final HashSet<Edge> outEdges;
      public Node(Character name) {
        this.name = name;
        inEdges = new HashSet<Edge>();
        outEdges = new HashSet<Edge>();
      }
      public Node addEdge(Node node){
        Edge e = new Edge(this, node);
        outEdges.add(e);
        node.inEdges.add(e);
        return this;
      }
    }

    static class Edge{
      public final Node from;
      public final Node to;
      public Edge(Node from, Node to) {
        this.from = from;
        this.to = to;
      }
      @Override
      public boolean equals(Object obj) {
        Edge e = (Edge)obj;
        return e.from == from && e.to == to;
      }
    }

    public static void main(String args[]) throws Exception
    {
      //int []arr = {100,7, 6, 5, 4, 3,2, 1, 100};
      //int []arr = {101,100,99,98};
      //System.out.println(do_something(arr,6));
      kickstart_io();
    }


    public static void code_jam_io()
    {
      String filename = "test.txt";
      String output="";
      Scanner sc = new Scanner(filename); // or System.in
      int no_tests = sc.nextInt();
      for (int i=0; i<no_tests; i++)
      {
        int n= sc.nextInt();
        int arr[]= new int[n];
        int k= sc.nextInt();
        for(int j=0;j<n;j++)
          arr[j]=sc.nextInt();
        //output+="Case #"+(i+1)+": "+do_something(arr, k)+"\n";
      }
      sc.close();
      write_to_file(output, filename);
    }

    public static void kickstart_io()
    {
      //String filename = "test.txt";
      String output="";
      Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in))); // or System.in
      int no_tests = sc.nextInt();
      for (int i=0; i<no_tests; i++)
      {
        int no_chars= sc.nextInt();
        int dim = sc.nextInt();
        char [][]mat= new char[dim][dim];
        for(int j=0;j<dim;j++)
            mat[j]=sc.next().toCharArray();
        output="Case #"+(i+1)+": "+do_something(mat, dim, dim);
        System.out.println(output);
      }
      sc.close();
    }

    public static void stream(String filename) throws IOException {
      /*Read a file bye per byte*/
      FileInputStream d = new FileInputStream(filename);
      int b = -1;
      while((b = d.read()) != -1) {
        System.out.print((byte)b+" ");
      }
      d.close();
    }

    public static void write_to_file(String output, String filename){    
      try{    
        FileWriter fw=new FileWriter(filename);    
        fw.write(output);    
        fw.close();    
       }catch(Exception e){System.out.println(e);}      
  }    
} 
 
