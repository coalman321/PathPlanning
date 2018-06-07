import java.util.LinkedList;
import java.util.List;

public class Path {

    public static Node[] getPathBetween(Node start, Node end, Map map){

        //prep variables for manipulated mapping
        List<Node> closed = new LinkedList<Node>();
        Node current = start;

        //commence manipulated mapping
        while(!current.equals(end)){
            Node[] adj = map.adjacentTo(current);

        }

        //TODO once assignment is complete search end adjacents for least cost and work backwards to start

        return null;
    }
}
