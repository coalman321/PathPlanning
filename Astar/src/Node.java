public class Node {

    protected int pos_x, pos_y;
    protected TraversalState traversalState;

    enum TraversalState{
        IMPASSABLE(9),  //cannot pass through
        UNKNOWN(7),     //this node is unknown need more data
        UNCERTAIN(5),   //data inconclusive but suggests passable
        PASSABLE(2),    //passable node
        TRAVERSED(0);   //node that has already been traversed

        private int weight;

        TraversalState(int weight){
            this.weight = weight;
        }

        public int getWeight() {
            return weight;
        }
    }


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

    public int distanceTo(Node node){
        if(node != null) return (int) Math.sqrt(Math.pow(pos_x - node.pos_x, 2) + Math.pow(pos_y - node.pos_y, 2));
        return 0;
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

    public class ManipNode extends Node {

        protected Node goal;
        protected int cost;
        protected boolean hasSearched = false;

        public ManipNode(Node node, Node goal){
            super(node.pos_x, node.pos_y, TraversalState.UNKNOWN);
            this.goal = goal;
            cost = 1;
        }

        public void setHasSearched(boolean hasSearched) {
            this.hasSearched = hasSearched;
        }

        public int getF(){
            return traversalState.getWeight() + distanceTo(goal);
        }

    }

}