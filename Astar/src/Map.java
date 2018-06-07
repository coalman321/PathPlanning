import java.io.File;



public class Map {


    //9 in cost is a solid object
    //7 is uncertain
    //1 is nominal

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

    public Node[] adjacentTo(int x, int y){
        //TODO return safely and ignor non-passable nodes
        return new Node[] {nodeMap[x - 1][y], nodeMap[x + 1][y], nodeMap[x][y - 1], nodeMap[x][y + 1]};
    }

    public Node[] adjacentTo(Node node){
        return adjacentTo(node.pos_x, node.pos_y);
    }

}
