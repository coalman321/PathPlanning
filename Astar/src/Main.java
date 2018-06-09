import aStar.Map;
import aStar.Node;

import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        System.out.println("creating map");
        Map map = new Map(5000,5000);
        System.out.println("map created");
        map.getNode(2,2).setTraversalState(Node.TraversalState.IMPASSABLE);
        map.getNode(1,2).setTraversalState(Node.TraversalState.IMPASSABLE);
        map.getNode(0,2).setTraversalState(Node.TraversalState.IMPASSABLE);
        //map.getNode(3,4).setTraversalState(aStar.Node.TraversalState.IMPASSABLE);
        //map.getNode(4,3).setTraversalState(aStar.Node.TraversalState.IMPASSABLE);
        //map.getNode(5,4).setTraversalState(aStar.Node.TraversalState.IMPASSABLE);
        //map.getNode(4,5).setTraversalState(aStar.Node.TraversalState.IMPASSABLE);
        System.out.println("finding map");
        System.out.println(map.getPathBetween(map.getNode(1,1), map.getNode(4, 4)));

        Scanner s = new Scanner(System.in);
        while (true){
            if(s.next().contains("continue")) break;
        }

        System.out.println("serializing map");
        map.writeToFile(new File("C:\\Users\\coalm\\Desktop\\file.tsv"));
        map.close();

        System.out.println("deserializing map");
        Map map2 = new Map(new File("C:\\Users\\coalm\\Desktop\\file.tsv"), 5000, 5000);
        System.out.println(map2.getPathBetween(map2.getNode(1,1), map2.getNode(4, 4)));

        while (true){
            if(s.next().contains("continue")) break;
        }

    }

}
