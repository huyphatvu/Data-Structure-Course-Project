 
import java.util.*;

public class MyGraph{

    LinkedList<MyEdge> edgeArr;
    TreeMap<Long,MyNode>nodeArr;

    public MyGraph(){
        this.edgeArr = new LinkedList<>();
        this.nodeArr = new TreeMap<>();
    }
    public void buildGraph(long tailID, long headID, long weight){
        MyNode tailNode;
        MyNode headNode;

        if(!nodeArr.containsKey(tailID)){               //if the tail is not already in the node arr
            tailNode = new MyNode(tailID);              //create a new node for tail
            nodeArr.put(tailID,tailNode);               //add it to the map
        } else{tailNode = nodeArr.get(tailID);}         //else get the tail node using tailID

        if(!nodeArr.containsKey(headID)){               //if the head is not already in the node arr
            headNode = new MyNode(headID);              //create new node for head
            nodeArr.put(headID,headNode);
        } else{headNode = nodeArr.get(headID);}         //else get the head node using headID

        //create edge
        MyEdge currEdge = new MyEdge(tailNode, headNode, weight);
        edgeArr.add(currEdge);                          //add that edge to current Array
        tailNode.connectingEdges.add(currEdge);         //connect tail node to curr edge
        tailNode.nextNodeArr.add(headNode);             //put it in the list
//        System.out.println(currEdge);
    }

}
class MyNode{
    long id;
    long longtitude;
    long latitude;
    LinkedList<MyEdge> connectingEdges;
    LinkedList<MyNode> nextNodeArr;

    MyNode(long id){
        this.id = id;
        this.connectingEdges = new LinkedList<>();
        this.nextNodeArr = new LinkedList<>();
    }
    @Override
    public boolean equals(Object other) {
        MyNode oth = (MyNode) other;
        if(this.id == oth.id)
            return true;
        return false;
    }
    public String toString(){

        String cestring = connectingEdges.toString();
//        String nnastring = nextNodeArr.toString();            caused overflowed
        String idString = ""+ this.id;
//        String datas = "" +this.data;
        return String.format("id: %s\nConnecting Edges: %s\n",idString, cestring);
    }
}

class MyEdge{
    MyNode head;
    MyNode tail;
    long weight;
    MyEdge(MyNode head, MyNode tail, long weight){
        this.head = head;
        this.tail = tail;
        this.weight = weight;
    }

    @Override
    public boolean equals(Object other){
        MyEdge oth = (MyEdge) other;
        if(!head.equals(oth.head))
            return false;
        if(!tail.equals(oth.tail))
            return false;
        if(weight != oth.weight)
            return false;
        return true;
    }
    public String toString(){
        return String.format("\nhead: %s, tail: %s, weight: %s", head.id, tail.id, weight);
    }

}
