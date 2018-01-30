import java.io.*;
import java.util.*;


public class FindShortestRoadPathNew {

    private static Map<String, Integer> frequencyMap = new HashMap<String, Integer>();

    public static void main(String[] args) throws IOException {
        String localFile = args[0];
        String sourceID = args[1];
        String targetID = args[2];

        BufferedReader mybuff = new BufferedReader(new FileReader(localFile)); //create buffered reader
        try {
            String myLine = mybuff.readLine();
            GraphBuilder graph = new GraphBuilder();

            int nodesNum = 0;
            int edgesNum = 0;
            //Using hash map
//            ArrayList<MyNode> alreadyAdded = new ArrayList<>();
//            ArrayList<String> alreadyAddedID = new ArrayList<>();
//            TreeMap<Integer, ArrayList<HashMap<MyNode, Integer>>> mygraph = null;
//
//            MyNode n = null;
//            MyNode sourceNode= null;
            while (myLine != null) {
                myLine = mybuff.readLine();
                if (myLine == null || myLine.length() == 0) break; //read until encounter an empty line
                String[] arrElements = myLine.split(" ");//split the space;

                //if myLine starts with c, ignore it
                //if myLine starts with p
                //get the nodes n
                //get the edges e
                if (arrElements[0].equals("p")) {
                    nodesNum = Integer.parseInt(arrElements[2]);
                    edgesNum = Integer.parseInt(arrElements[3]);
//                    mygraph = new TreeMap<>();
                }
                if (arrElements[0].equals("a")) {
                    int tail = Integer.parseInt(arrElements[1]); //  first node
                    int head = Integer.parseInt(arrElements[2]); //  sec node
                    int weight = Integer.parseInt(arrElements[3]);

                    graph.buildGraph(tail, head, weight);              //build the graph
                }






                //get the first line element


            }
//            System.out.println(sourceID);

//            graph.nodeArr.forEach((val, node)->(val==5) System.out.println(val)));
            for(Long s : graph.nodeArr.keySet()){
                if(s==5)
                    System.out.println(s);
            }
            System.out.println(graph.nodeArr.get((long)5)); //becare full with contains key

        } finally {
            mybuff.close();     //close the bufferedreader
        }

    }
//    void disjkstraAlgorithm(  source){
//
//    }
}

/**
 * Class contains the info of its edges
 */
/*
class MNode implements Comparable<MyNode>{
    int id;         //id
    ArrayList<MyNode> nextNodeArr;  //nextNode
    boolean known;
    int disFromVer; //distance form the source vertex
    MyNode preNode;   //Previous vertex
    ArrayList<Integer> weightArr;
    public MyNode(int id, MyNode next, int weight){
        this.id = id;

        this.known = false;
        this.disFromVer = Integer.MAX_VALUE;    //get the max val
        this.preNode = null;

        if(this.weightArr==null){
            this.weightArr = new ArrayList<>();
        }
        this.weightArr.add(weight);

        if(this.nextNodeArr==null){
            this.nextNodeArr = new ArrayList<>();
        }
        this.nextNodeArr.add(next);
    }
    public MyNode(int id){
        this(id, null, 0);
    }

    @Override
    public int compareTo(MyNode other){
        if(this.disFromVer == Integer.MAX_VALUE)
            return 1;   //if dis is infinity it is greater
        else if (this.disFromVer == Integer.MAX_VALUE)
            return disFromVer - other.disFromVer;
        else return -1;
    }
    public String toString(){
        String arrayNext = "[" ;
        for(MyNode elements: nextNodeArr){
            arrayNext+=elements.id+" ";
        }
        arrayNext += "]";

        String arrayweight = "[" ;
        for(Integer elements: weightArr){
            arrayweight+=elements.toString()+" ";
        }
        arrayweight += "]";
        return String.format("id: %s, next: %s, weight: %s", id, arrayNext, arrayweight);
    }
}
*/

