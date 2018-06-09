import aStar.Map;
import aStar.Node;

import java.io.File;

public class Main {

    public static void main(String[] args){
        Map map = new Map(10,10);
        //System.out.println(map.adjacentTo(map.getNode(9,9)));
        map.getNode(2,2).setTraversalState(Node.TraversalState.IMPASSABLE);
        map.getNode(1,2).setTraversalState(Node.TraversalState.IMPASSABLE);
        map.getNode(0,2).setTraversalState(Node.TraversalState.IMPASSABLE);
        //map.getNode(3,4).setTraversalState(aStar.Node.TraversalState.IMPASSABLE);
        //map.getNode(4,3).setTraversalState(aStar.Node.TraversalState.IMPASSABLE);
        //map.getNode(5,4).setTraversalState(aStar.Node.TraversalState.IMPASSABLE);
        //map.getNode(4,5).setTraversalState(aStar.Node.TraversalState.IMPASSABLE);
        System.out.println(map.getPathBetween(map.getNode(1,1), map.getNode(4, 4)));

        //map.writeToFile(new File("C:\\Users\\coalm\\Desktop\\file.tsv"));


        Map map2 = new Map(new File("C:\\Users\\coalm\\Desktop\\file.tsv"));
        System.out.println(map2.getPathBetween(map2.getNode(1,1), map2.getNode(4, 4)));

    }

}
