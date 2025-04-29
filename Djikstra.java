import java.util.*;
import java.io.*;

//Priority queue is a heap
//Input is textual representation of weighted direct graph
//Output is list of shortest path lengths (one per vertex)
//Let input file be input.txt and output file be output.txt
public class Djikstra {
    private static PriorityQueue<Node> heap = new PriorityQueue<Node>();
    private static Scanner scan;
    private static FileWriter writer;
    private static List<int[]>[] graph;
    private static int[] distances;
    private static int numOfNodes = 0;
    private static int numOfEdges = 0;

    public static class Node implements Comparable<Node>{
        private int vertex;
        private int distance;

        public Node(int vertex, int distance){
            this.vertex = vertex;
            this.distance = distance;
        }

        public void setDistance (int distance){
            this.distance = distance;
        }

        public int getDistance(){
            return this.distance;
        }

        public int getVertex(){
            return this.vertex;
        }

        public int compareTo(Node node){
            return Integer.compare(this.distance, node.distance);
        }
    }

    //Directed, not undirected
    public static void addEdge(int source, int dest, int weight){
        graph[source].add(new int[] {dest, weight});
    }

    public static void main(String[] args){
        try {
            scan = new Scanner(new File("input.txt"));

            numOfNodes = scan.nextInt();
            numOfEdges = scan.nextInt();

            int source, dest, weight;

            distances = new int[numOfNodes];
            Arrays.fill(distances, Integer.MAX_VALUE); //Default all to infinity

            //Setup the graph
            graph = new ArrayList[numOfNodes];

            for (int i = 0; i < numOfNodes; i++){
                graph[i] = new ArrayList<>();
            }

            while (scan.hasNextInt()){
                addEdge(scan.nextInt(), scan.nextInt(), scan.nextInt());
            }

            heap.add(new Node(0, 0)); //Start from vertex 0 as source, with distance of 0
            distances[0] = 0;

            //Djikstra's algorithm
            while (!heap.isEmpty()){
                dest = heap.poll().getVertex();
                //p("Testing vertex " + dest);

                for (int[] neighbor : graph[dest]){
                    source = neighbor[0];
                    weight = neighbor[1];

                    if (distances[source] > distances[dest] + weight){
                        distances[source] = distances[dest] + weight;
                        heap.add(new Node(source, distances[source]));
                    }
                }
            }

            //Export to .TXT file
            File output = new File("output.txt");
            output.createNewFile();

            writer = new FileWriter("output.txt");

            //Vertex numbers
            writer.write("Vertex");
            for (int i = 0; i < numOfNodes; i++){
                if (i == 0)
                    writer.write("  " + i);
                else
                    writer.write("   " + i);
                //p("Writing " + "\t" + i);
            }

            writer.write("\n");
            
            //Distances
            writer.write("Distance");
            for (int i = 0; i < numOfNodes; i++){
                if (distances[i] == Integer.MAX_VALUE)
                    writer.write("   infinity");
                else if (i == 0)
                    writer.write("  " + distances[i]);
                else
                    writer.write("   " + distances[i]);
                //p("Writing " + "\t" + distances[i]);
            }

            writer.close();
        }
        catch (Exception e){
            p(e);
        }

    }

    //Helper method to make printing easier
    public static void p(Object p){
        System.out.println("" + p);
    }
}