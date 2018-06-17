import aStar.MapRegion;
import aStar.MapV2;

import java.io.File;

public class MainV2 {

    public static void main(String[] args){
        System.out.println("creating map");
        MapV2 inst = new MapV2(new File("C:\\Users\\Lynn's\\Desktop\\test.txt"), 1000,1000, new MapRegion(0,0,10,10));
        System.out.println("map created");
        System.out.println("adjacent to " + inst.getNode(2,2) + " " + inst.adjacentTo(inst.getNode(2,2)));
        //inst.loadRegion(new MapRegion(20,20,20,20));
        //inst.loadRegion(new MapRegion(10,10,1500,20));
        //inst.loadRegion(new MapRegion(20,20,20,20));
        //System.out.println(inst.getNode(30,30));
        //inst.loadRegion(new MapRegion(0,0,20,10));
        //System.out.println(inst.getPathBetween(inst.getNode(1,1), inst.getNode(9,9)));

    }

}
