import java.io.*;
import java.util.*;


/**
 * A class that will perform
 * Dijkstra's Algorithm on the file
 * Typing commandline
 * java FindShortestRoadPath {file}.gr sourceID targetID {outputfile}.txt
 *
 * to see the actual graph type
 * java FindShortestRoadPath {file}.gr sourceID targetID {file}.co savedCoord.txt
 * then open Geocoding Map.html
 */
public class FindShortestRoadPath {
    public static void main(String[] args) throws IOException {
        String inputFileName = args[0];
        String sourceID = args[1];
        String targetID = args[2];


        GraphBuilder graph = new GraphBuilder();
        String result = readFromFile(graph, inputFileName, sourceID, targetID);

        //param: java FindShortestRoadPath {file}.gr sourceID targetID {outputfile}.txt
        if(args.length ==4){                                //first part
            String outputFileName = args[3];
            writeToFile(result, outputFileName);            //write the data to a File
        }
        //param: java FindShortestRoadPath {file}.gr sourceID targetID {file}.co savedCoord.txt
         if (args.length ==5){                              //bonus part
             String coordFileName = args[3];
             String encodedFileName = args[4];
             //read gz file as an string array of 3
             String[] id_to_coord_list = readFromCoordinateFile(coordFileName);
             writeToCoordinateFile(result, id_to_coord_list,encodedFileName);
        }
    }

    //The method that read the {filename}.co
    private static String[] readFromCoordinateFile(String inputFileName){
        BufferedReader inputBuffer = null;

        String[] id_to_coord_list = null;
        int i = -1;
        try {
            inputBuffer = new BufferedReader(new FileReader(inputFileName)); //create buffered reader
            String myLine = inputBuffer.readLine();
            while (myLine != null) {
                myLine = inputBuffer.readLine();
                if (myLine == null || myLine.length() == 0) break; //read until encounter an empty line
                String[] arrElements = myLine.split(" ");//split the space;

                //if myLine starts with c, ignore it
                //if myLine starts with p
                //get the nodes n

                if (arrElements[0].equals("p")) {
                    int nodesNum = Integer.parseInt(arrElements[4]);
                    id_to_coord_list = new String[nodesNum];
                    i = 0;
                }
                if (arrElements[0].equals("v")) {
                    id_to_coord_list[i] = myLine;                   //saved the data to id_to_coord_list
                    i++;                                            //increment the index
                }

            }
        }
        catch (FileNotFoundException e){System.err.println("Error Reading File");}
        catch (IOException e){System.err.println("Error Reading File");}
        finally {
            try {
                inputBuffer.close();
            }catch (IOException e){System.err.println("Error Closing File");}     //close the bufferedreader
        }
        return id_to_coord_list;


    }

    //the method that write to savedCoord.txt file
    private static void writeToCoordinateFile(String idNames, String[] id_to_coord_list, String outputFileName){
        String[] stringToWrite = getStringBasedOnCoordinate( idNames, id_to_coord_list); //list of id we found, ignore the first 0th index
        writeToFile(stringToWrite, outputFileName);
    }

    //The method to get the string array of coordinate based on the coordinate list
    private static String[] getStringBasedOnCoordinate(String idNames, String[] id_to_coord_list){
        String[] subID = idNames.split("\\n");
        String[] string_of_id = new String[subID.length-1];
        for(int i=1; i< subID.length; i++){
            string_of_id[i-1] = id_to_coord_list[Integer.parseInt(subID[i])-1];
        }
        return string_of_id;
    }

    //The method that read {filename}.gr and call disjkstraAlgorithm  execute Dijkstra's Algorithm
    private static String readFromFile(GraphBuilder graph, String inputFileName, String sourceID, String targetID){
        BufferedReader inputBuffer = null;
        String result = "";
        try {
            inputBuffer = new BufferedReader(new FileReader(inputFileName)); //create buffered reader
            String myLine = inputBuffer.readLine();
            while (myLine != null) {
                myLine = inputBuffer.readLine();
                if (myLine == null || myLine.length() == 0) break; //read until encounter an empty line
                String[] arrElements = myLine.split(" ");//split the space;
                if (arrElements[0].equals("a")) {
                    long tail = Integer.parseInt(arrElements[1]); //  first node
                    long head = Integer.parseInt(arrElements[2]); //  sec node
                    long weight = Integer.parseInt(arrElements[3]);

                    graph.buildGraph(tail, head, weight);              //build the graph
                }

            }
            Node sourceNode = graph.nodeArr.get((long)Integer.parseInt(sourceID));      //get source Node
            Node targetNode = graph.nodeArr.get((long)Integer.parseInt(targetID));      //get source Node
//            System.out.println(sourceNode);
            sourceNode.distance = 0;                                                    //Initialize source distance to 0
            disjkstraAlgorithm(sourceNode, graph);
//            System.out.println(targetNode);
            LinkedList<Node> reversedPath = getRevesredPath(targetNode);
            result = "Total distance: "+targetNode.distance+"\n";
//            System.out.println(result);

            for(Iterator<Node> iterator = reversedPath.descendingIterator(); iterator.hasNext();  ){
                result += iterator.next().id+"\n";
//                System.out.println(iterator.next().id);
            }
            System.out.print(result);
//            System.out.println(graph.nodeArr.get((long)5)); //be careful with contains key
        }
        catch (FileNotFoundException e){System.err.println("Error Reading File");}
        catch (IOException e){System.err.println("Error Reading File");}
        finally {
            try {
                inputBuffer.close();
            }catch (IOException e){System.err.println("Error Closing File");}     //close the bufferedreader
        }
       return result;
    }

    //The method that write to a file by passing string array
    private static void writeToFile(String[] writeStringArr, String outputFileName){
        BufferedWriter outputBuffer = null;
        try{
            File outputFile = new File(outputFileName);
            if(!outputFile.exists()){
                outputFile.createNewFile();
            }

            outputBuffer = new BufferedWriter(new FileWriter(outputFile)); //create buffered reader
//          outputBuffer.write(writeString);

            for(int i=0; i< writeStringArr.length; i++){
                outputBuffer.write(writeStringArr[i]);
                outputBuffer.newLine();
            }
        } catch (IOException e){System.err.println("Error Writing File");}
        finally {
            try {
                outputBuffer.close();
            }catch (IOException e){System.err.println("Error Closing File");}
        }
    }

    //The method that write to a file by passing a string
    private static void writeToFile(String writeString, String outputFileName){
        String[] substring = writeString.split("\\n");
        writeToFile(substring, outputFileName);
    }

    //the method that find the shortest distance from a source to other nodes
    private static void disjkstraAlgorithm(Node sourceNode, GraphBuilder graph){
            PriorityQueue<Node> smallestDisQueue = new PriorityQueue<>(graph.nodeArr.size(), new Comparator<Node>() {
                @Override
                public int compare(Node first, Node sec) {
                if(first.distance == Integer.MAX_VALUE && sec.distance == Integer.MAX_VALUE){
                    return 0;
                }
                else if(first.distance == Integer.MAX_VALUE && sec.distance != Integer.MAX_VALUE){
                    return 1;
                } else if(first.distance != Integer.MAX_VALUE && sec.distance == Integer.MAX_VALUE){
                    return -1;
                }
                else
                    return (int) (first.distance - sec.distance);
            }
        });

        smallestDisQueue.add(sourceNode);           //add the node to the queue

        //        until all vertices are know get the vertex with smallest distance

        while(!smallestDisQueue.isEmpty()) {

            Node currNode = smallestDisQueue.poll();
//            System.out.println("Curr: ");
//            System.out.println(currNode);
             if(currNode.known)
                continue;               //do nothing if the currNode is known

            currNode.known = true;      // otherwise, set it to be known

            for(Edge connectedEdge : currNode.connectingEdges){
                Node nextNode = connectedEdge.head;
                if(!nextNode.known){    // Visit all neighbors that are unknown

                    long weight = connectedEdge.weight;
                    if(currNode.distance == Integer.MAX_VALUE){
                        continue;
                    }
                    else if(nextNode.distance == Integer.MAX_VALUE && currNode.distance == Integer.MAX_VALUE) {
                        continue;
                    }

                    else if(nextNode.distance> weight + currNode.distance){//Update their distance and path variable
                        smallestDisQueue.remove(nextNode);                  //remove it from the queue
                        nextNode.distance = weight + currNode.distance;

                        smallestDisQueue.add(nextNode);                     //add it again to the queue
                        nextNode.pathFromSouce = currNode;

//                        System.out.println("Next:  ");
//                        System.out.println(nextNode);
                    }
                }
            }
//            System.out.println("/////////////");
        }
    }

    //reverse the path from the source id and store in a linkedlist array
    private static LinkedList<Node> getRevesredPath(Node targetNode){
        LinkedList<Node> arr = new LinkedList<>();
        arr.add(targetNode);
        while(targetNode.pathFromSouce !=null){
//            System.out.println(targetNode);
//            System.out.println(targetNode.pathFromSouce);
            arr.add(targetNode.pathFromSouce);
            targetNode = targetNode.pathFromSouce;
        }
        return arr;
    }
}

/**
 * A class that build the
 * graph
 */
class GraphBuilder{

        LinkedList<Edge> edgeArr;
        TreeMap<Long,Node>nodeArr;

    /**
     * Constructor
     */
    public GraphBuilder(){
            this.edgeArr = new LinkedList<>();
            this.nodeArr = new TreeMap<>();
    }

    /**
     * The method that build the graph
     * @param tailID    the id of the tail of the edge
     * @param headID    the id of the head of the edga
     * @param weight    the weight of the edge
     */
    public void buildGraph(long tailID, long headID, long weight){
        Node tailNode;
        Node headNode;

        if(!nodeArr.containsKey(tailID)){               //if the tail is not already in the node arr
            tailNode = new Node(tailID);              //create a new node for tail
            nodeArr.put(tailID,tailNode);               //add it to the map
        } else{tailNode = nodeArr.get(tailID);}         //else get the tail node using tailID

        if(!nodeArr.containsKey(headID)){               //if the head is not already in the node arr
            headNode = new Node(headID);              //create new node for head
            nodeArr.put(headID,headNode);
        } else{headNode = nodeArr.get(headID);}         //else get the head node using headID

        //create edge
        Edge currEdge = new Edge(tailNode, headNode, weight);
        edgeArr.add(currEdge);                          //add that edge to current Array
        tailNode.connectingEdges.add(currEdge);         //connect tail node to curr edge
        tailNode.nextNodeArr.add(headNode);             //put it in the list
//        System.out.println(currEdge);
    }

}

/**
 * The class that model a
 * node in a graph
 */
class Node {
    long id;
    long distance;
    boolean known;
    LinkedList<Edge> connectingEdges;       //the edge that it connects to
    LinkedList<Node> nextNodeArr;           //the nodes that it connects to
    Node pathFromSouce;

    /**
     * Constructor
     * @param id    the id of the node
     */
    Node(long id){
        this.id = id;
        this.connectingEdges = new LinkedList<>();
        this.nextNodeArr = new LinkedList<>();
        this.distance = Integer.MAX_VALUE;
        this.known = false;
        this.pathFromSouce = null;
    }

    /**
     * print the node
     * @return  string representation of the node
     */
    public String toString(){
        String cestring = connectingEdges.toString();
        String idString = ""+ this.id;
        String distancestring = ""+this.distance;
        return String.format("id: %s, ,distance: %s\nConnecting Edges: %s\n", idString, distancestring, cestring);
    }

}

/**
 * The class that model
 * the edge in a graph
 */
class Edge{ //edge goes from tail to head
    Node tail;
    Node head;
    long weight;

    /**
     *
     * @param tail  the starting node
     * @param head  the ending node
     * @param weight    the weight of the edge
     */
    Edge(Node tail, Node head, long weight){
        this.head = head;
        this.tail = tail;
        this.weight = weight;
    }

    /**
     * Print the edge
     * @return  the string representation of the edge
     */
    public String toString(){
        return String.format("\ntail: %s, head: %s, weight: %s", tail.id, head.id, weight);
    }

}
