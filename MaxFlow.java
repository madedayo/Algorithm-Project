import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.List;
import java.io.File; 
import java.util.Scanner; 
import java.util.Random;


public class MaxFlow{
    
    public static class Edge{
        
        public int start;
        public int end;
        public int capacity;
        public int flow;
        public Edge reverse;


        public Edge(int start, int end, int capacity){
            this.start = start;
            this.end = end;
            this.capacity = capacity;
        }
    
        public void augment(int value){
            flow = flow + value;
            reverse.flow = reverse.flow - value ;
        }

        public int leftCapacity(){
            return capacity -  flow;
        }
        
    }

    public static class FLowNetwork{

        public int n;
        public int sink; 
        public int source;
        public int maxFlow;
        List <Edge> [] adjList;
        public int visitIndicator = 1;
        public int[] visitStatus;

        public FLowNetwork(int n, int s, int t){
            
            this.n = n;
            this.source = s;
            this.sink = t;

            initializeAdjList();
            visitStatus = new int[n];

        }

        public void initializeAdjList(){
            
            adjList = new List[n];
            for(int i = 0; i < n; i++){
                adjList[i] = new ArrayList<Edge>();
            }

        }

        public void addEdge(int start, int end, int capacity){

            Edge edge1 = new Edge(start, end, capacity);
            Edge edge2 = new Edge(end, start, 0);

            edge1.reverse = edge2;
            edge2.reverse = edge1;

            adjList[start].add(edge1);
            adjList[end].add(edge2);

        }
       
        public int  DepthFirst(int vertex, int flow){

            if(vertex == sink) return flow;

            visitStatus[vertex] = visitIndicator;

            List <Edge> edges = adjList[vertex];

            for(Edge edge : edges){

                if(edge.leftCapacity() > 0 && visitStatus[edge.end] != visitIndicator){
                    
                    int value = DepthFirst(edge.end, min(flow, edge.leftCapacity()));

                

                    if(value > 0){
                        edge.augment(value);
                        return value;
                    }
            }

            }

            return 0;

        }

        public void FordFulkerson(){

            int i = DepthFirst(source, Integer.MAX_VALUE/4);
            while(i!= 0){

                visitIndicator ++;
                maxFlow += i;
                i = DepthFirst(source, Integer.MAX_VALUE/4);

            }

        }


    }

    
     public static void main(String[] args) throws Exception {

        //*** This part is used to generate random flow networks */
        //generating a random n nodes graph
        //  int n = 10000;
        //  int v = 100;
        //  int flowMax = 10;
        //  int [] start = new int [n];
        //  int [] end = new int [n];
        //  int [] flows = new int[n];

        //  //populating start and end

        //  Random rand = new Random();

        //  for(int i = 0; i < n; i++){
             
        //     start[i] = rand.nextInt(v);
        //     end[i] = rand.nextInt(v);
        //     flows[i] = rand.nextInt(flowMax);

        //  }

        // int sourceIndex = rand.nextInt(n);
        // int sinkIndex = rand.nextInt(n);

        // for(int i = 0; i < n; i++){
        //     for(int j = 0; j < n; j++){
        //         if(i != j){
        //             if(start[i] == start[j] && end[i]==end[j]){
        //                 end[j] = rand.nextInt(v);
        //             }
        //         }
        //     }
        // }


        // FLowNetwork flowGraph = new FLowNetwork(n, start[sourceIndex], end[sinkIndex]);

        // for(int i = 0; i < n; i++){
        //     flowGraph.addEdge(start[i], end[i], flows[i]);
        // }

        //******************//


        // **** uncomment the following part to run it on the real data
        // File file = new File("/Users/parsa/Downloads/BVZ-tsukuba2.txt"); 
        // Scanner sc = new Scanner(file);
        // int [] parameters = new int [3];
        // int [] numbers = new int [3]; 
    
        // for(int i = 0; i < 3; i++){
        //     parameters[i] = sc.nextInt();
        // }
    
        // FLowNetwork flowGraph = new FLowNetwork(parameters[0], parameters[1] -1 , parameters[2] -1);    

        // while (sc.hasNextLine()) {
      
        //     for(int i = 0; i < 3; i++){
        //         numbers[i] = sc.nextInt();
        //     }
        
        //     flowGraph.addEdge(numbers[0] -1, numbers[1] -1, numbers[2]);
         
        // }  

        // sc.close();


        //uncomment this part to run the test file

        // **** this is the test file
        File file = new File("/Users/parsa/Downloads/Lab07/test.txt"); 
        Scanner sc = new Scanner(file);
        int [] parameters = new int [3];
        int [] numbers = new int [3]; 
    
        for(int i = 0; i < 3; i++){
            parameters[i] = sc.nextInt();
        }
    
        FLowNetwork flowGraph = new FLowNetwork(parameters[0], parameters[1] , parameters[2]);    

        while (sc.hasNextLine()) {
      
            for(int i = 0; i < 3; i++){
                numbers[i] = sc.nextInt();
            }
        
            flowGraph.addEdge(numbers[0], numbers[1], numbers[2]);
         
        }  

        sc.close();

        
        long startTime = System.nanoTime();
        flowGraph.FordFulkerson();
        long endTime = System.nanoTime();

        long duration = (endTime - startTime)/1000000 ;
        
        System.out.printf("Maximum Flow is: %d\n", flowGraph.maxFlow);
        System.out.printf("\nThe running time is: %d\n", duration);

     }
    


}