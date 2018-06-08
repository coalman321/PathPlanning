public class Node implements Comparable<Node>{

    enum TraversalState{
        IMPASSABLE(9),  //cannot pass through
        UNKNOWN(7),     //this node is unknown need more data
        UNCERTAIN(4),   //data inconclusive but suggests passable
        PASSABLE(1),    //passable node
        TRAVERSED(0);   //node that has already been traversed

        private int weight;

        TraversalState(int weight){
            this.weight = weight;
        }

        public int getWeight() {
            return weight;
        }
    }

    protected int pos_x, pos_y, cost;
    protected TraversalState traversalState;
    protected boolean hasSearched = false;
    protected Node cameFrom = null;

    /**
     * creates new instance of node from x,y position and a traversal state
     * @param pos_x x position in a map
     * @param pos_y y position in a map
     * @param traversalState traversal state of the node
     */
    public Node(int pos_x, int pos_y, TraversalState traversalState ){
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.traversalState = traversalState;
        cost = 0;
    }

    /**
     * default constructor that initializes to unknown traversal state
     * @param pos_x x position in a map
     * @param pos_y y position in a map
     */
    public Node(int pos_x, int pos_y){
        this(pos_x, pos_y, TraversalState.UNKNOWN);
    }

    public int getPos_x(){
        return pos_x;
    }

    public int getPos_y() {
        return pos_y;
    }

    public Node getCameFrom() {
        return cameFrom;
    }

    public void setCameFrom(Node cameFrom) {
        cost = cameFrom.cost + 1;
        this.cameFrom = cameFrom;
    }

    public int getCost(){
        return cost;
    }

    public boolean getSearched() {
        return hasSearched;
    }

    public void setSearched(boolean hasSearched) {
        this.hasSearched = hasSearched;
    }

    public boolean canTraverse(){
        return traversalState != TraversalState.IMPASSABLE;
    }

    public boolean hasTraversed(){
        return traversalState == TraversalState.TRAVERSED;
    }

    /**
     * updates the traversal state of a node
     * @param traversalState
     */
    public void setTraversalState(TraversalState traversalState) {
        this.traversalState = traversalState;
    }

    public void reset() {
        hasSearched = false;
        cameFrom = null;
    }

    @Override
    public int compareTo(Node o) {
        if()
    }
}