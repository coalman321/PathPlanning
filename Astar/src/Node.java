public class Node {

    protected int pos_x, pos_y, cost;

    public Node(int pos_x, int pos_y, int cost){
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.cost = cost;
        //this.map TODO find new way to get adjacent nodes
    }

    public Node(int pos_x, int pos_y){
        this(pos_x, pos_y, 1);
    }

    public int distanceTo(Node node){
        if(node != null) return (int) Math.sqrt(Math.pow(pos_x - node.pos_x, 2) + Math.pow(pos_y - node.pos_y, 2));
        return 0;
    }

    public int getCost(){
        return cost;
    }

    public int getPos_x(){
        return pos_x;
    }

    public int getPos_y() {
        return pos_y;
    }

    public class ManipNode extends Node {

        protected Node goal;

        public ManipNode(Node node, Node goal){
            super(node.pos_x, node.pos_y, Integer.MAX_VALUE);
            this.goal = goal;
        }

        public void setCost(int newCost){
            cost = newCost;
        }

        public void setGoal(Node goal) {
            this.goal = goal;
        }

        public int getF(){
            return cost + distanceTo(goal);
        }



    }

}