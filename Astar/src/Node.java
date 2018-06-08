public class Node implements Comparable<Node>{

    enum TraversalState{
        IMPASSABLE(9),  //cannot pass through
        UNKNOWN(2),     //this node is unknown need more data
        UNCERTAIN(4),   //data inconclusive but suggests passable
        PASSABLE(2),    //passable node
        TRAVERSED(1);   //node that has already been traversed

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
    protected Node cameFrom = null, goal;

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
        hasSearched = true;
        cost = cameFrom.cost + 1;
        this.cameFrom = cameFrom;
    }

    public int getCost(){
        return cost;
    }

    public int distanceTo(Node node1, Node node2){
        return (int) Math.sqrt(Math.pow(node1.getPos_x() - node2.getPos_x(), 2) + Math.pow(node1.getPos_y() - node2.getPos_y(), 2));
    }

    public int getF(Node goal) {
        return traversalState.getWeight() * cost + distanceTo(this, goal);
    }

    public boolean getSearched() {
        return hasSearched;
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
        goal = null;
    }

    public void setGoal(Node goal) {
        this.goal = goal;
    }

    @Override
    public int compareTo(Node o) {
        if(getF(goal) < o.getF(goal)) return -1;
        if(getF(goal) > o.getF(goal)) return 1;
        return 0;
    }

    @Override
    public String toString() {
        return "node X: " + pos_x + " Y: " + pos_y + (canTraverse()? " traversable": " impassable");
    }
}