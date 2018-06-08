

public class Main {

    public static void main(String[] args){
        Map map = new Map(10,10);
        //System.out.println(map.adjacentTo(map.getNode(9,9)));
        System.out.println(map.getPathBetween(map.getNode(1,1), map.getNode(4, 4)));


    }

}
