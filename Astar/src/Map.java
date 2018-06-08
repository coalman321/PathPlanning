import java.io.File;
import java.util.Collections;
import java.util.LinkedList;


public class Map {

    Node[][] nodeMap;


    /**
     * create a new blank map of size x,y
     * @param size_x
     * @param size_y
     */
    public Map(int size_x, int size_y){
        nodeMap = new Node[size_x][size_y];
        for(int r = 0; r < size_x; r++){
            for(int c = 0; c < size_y; c++){
                nodeMap[r][c] = new Node(r, c);
            }
        }
    }

    public Map (File file){
        //import cost map from file
    }

    public Node getNode(int x, int y){
        return nodeMap[x][y];
    }

    /**
     * Resets the mapping state of nodes for re-use
     * <p><b>Only resets path specific flags<b/>
     */


    public LinkedList<Node> getPathBetween(Node start, Node end){
        reset();
        setGoal(end);


        //prep variables for manipulated mapping
        LinkedList<Node> toVisit = new LinkedList<>();
        toVisit.add(start);
        Node current;

        System.out.println("mapping started");
        while(!toVisit.isEmpty()){
            current = toVisit.poll(); //pull the lowest f off the stack
            System.out.println("searching " + current);
            if(current.equals(end)) break;
            toVisit.addAll(adjacentTo(current));
            Collections.sort(toVisit);// sort by lowest F value
            System.out.println("sorted list to visit " + toVisit);
        }
        System.out.println("iteration complete");

        return reconsruct(end, start);

    }

    private void reset(){
        for(Node[] nodes : nodeMap){
            for(Node node : nodes){
                node.reset();
            }
        }
    }

    private void setGoal(Node goal){
        System.out.println("setting goal");
        for(Node[] nodes : nodeMap){
            for(Node node : nodes){
                node.setGoal(goal);
            }
        }
    }

    private LinkedList<Node> reconsruct(Node end, Node start){
        LinkedList<Node> path = new LinkedList<>();
        Node prev = end.getCameFrom();
        System.out.println("reconstructing map");
        while(!prev.equals(start)){

            path.addFirst(prev);
            prev = prev.getCameFrom();
        }
        path.addFirst(start);
        System.out.println("map reconstructed");
        return path;
    }

    public LinkedList<Node> adjacentTo(Node node){

        LinkedList<Node> toReturn = new LinkedList<>();

        //above
        if(node.getPos_y() - 1 > -1 && nodeMap[node.getPos_x()][node.getPos_y() - 1].canTraverse() && !nodeMap[node.getPos_x()][node.getPos_y() - 1].getSearched()){
            nodeMap[node.getPos_x()][node.getPos_y() - 1].setCameFrom(node);
            toReturn.add(nodeMap[node.getPos_x()][node.getPos_y() - 1]);
        }

        //below
        if(node.getPos_y() + 1 < nodeMap[0].length && nodeMap[node.getPos_x()][node.getPos_y() + 1].canTraverse() && !nodeMap[node.getPos_x()][node.getPos_y() + 1].getSearched()) {
            nodeMap[node.getPos_x()][node.getPos_y() + 1].setCameFrom(node);
            toReturn.add(nodeMap[node.getPos_x()][node.getPos_y() + 1]);
        }

        //left
        if(node.getPos_x() - 1 > -1 && nodeMap[node.getPos_x() - 1][node.getPos_y()].canTraverse() && !nodeMap[node.getPos_x() - 1][node.getPos_y()].getSearched()) {
            nodeMap[node.getPos_x() - 1][node.getPos_y()].setCameFrom(node);
            toReturn.add(nodeMap[node.getPos_x() - 1][node.getPos_y()]);
        }
        //right
        if(node.getPos_x() + 1 < nodeMap.length && nodeMap[node.getPos_x() + 1][node.getPos_y()].canTraverse() && !nodeMap[node.getPos_x() + 1][node.getPos_y()].getSearched()) {
            nodeMap[node.getPos_x() + 1][node.getPos_y()].setCameFrom(node);
            toReturn.add(nodeMap[node.getPos_x() + 1][node.getPos_y()]);
        }
        return toReturn;
    }


}
