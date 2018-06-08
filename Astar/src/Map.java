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

    public LinkedList<Node> adjacentTo(int x, int y){

        LinkedList<Node> toReturn = new LinkedList<Node>();

        //above
        if(y - 1 > -1 && nodeMap[x][y - 1].canTraverse() && !nodeMap[x][y - 1].hasSearched)
            toReturn.add(nodeMap[x][y - 1]);
        //below
        if(y + 1 < nodeMap[0].length && nodeMap[x][y + 1].canTraverse() && !nodeMap[x][y + 1].hasSearched)
            toReturn.add(nodeMap[x][y + 1]);
        //left
        if(x - 1 > -1 && nodeMap[x - 1][y].canTraverse() && !nodeMap[x - 1][y].hasSearched)
            toReturn.add(nodeMap[x - 1][y]);
        //right
        if(x + 1 < nodeMap.length && nodeMap[x + 1][y].canTraverse() && !nodeMap[x + 1][y].hasSearched)
            toReturn.add(nodeMap[x + 1][y]);
        return toReturn;
    }

    public LinkedList<Node> adjacentTo(Node node){
        return adjacentTo(node.getPos_x(), node.getPos_y());
    }

}
