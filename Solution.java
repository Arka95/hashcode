import java.util.*;
import java.io.*;

public class Solution{

    public static String do_something(int []arr){

      return "";
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
      String filename = "a_example.txt";
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
        int n= sc.nextInt();
        int arr[]= new int[n];
        int k= sc.nextInt();
        for(int j=0;j<n;j++)
          arr[j]=sc.nextInt();
        output="Case #"+(i+1)+": "+do_something(arr);
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