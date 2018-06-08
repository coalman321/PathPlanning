import java.io.File;
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

    /**
     * Resets the mapping state of nodes for re-use
     * <p><b>Only resets path specific flags<b/>
     */
    public void reset(){
        for(Node[] nodes : nodeMap){
            for(Node node : nodes){
                node.reset();
            }
        }
    }

    public Node getNode(int x, int y){
        return nodeMap[x][y];
    }

    public LinkedList<Node> getPathBetween(Node start, Node end){

        //prep variables for manipulated mapping
        LinkedList<Node> toVisit = new LinkedList<>();
        Node current = start;

        //commence manipulated mapping
        while(!current.equals(end)){
            toVisit.addAll(adjacentTo(current));
            //TODO sort by getF
            current = toVisit.poll();
        }

        LinkedList<Node> path = new LinkedList<>();
        Node prev = end.getCameFrom();
        while(!prev.equals(start)){
            path.addFirst(prev);
            prev = prev.getCameFrom();
        }
        path.addFirst(start);
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
