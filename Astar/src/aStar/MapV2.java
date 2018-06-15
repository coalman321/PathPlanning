package aStar;

import java.io.*;
import java.nio.file.Files;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class MapV2 {

    private int size_x, size_y;
    private Node[][] loadedNodes;
    private MapRegion loadedRegion = null;
    private File map;

    /**
     * creates a map of size_x by size_y and writes it to file. a sub-region is then loaded into memory designated by region.
     * @param file file reference to write to
     * @param size_x size of map to create
     * @param size_y
     * @param region map region to load into memory
     */
    public MapV2(File file, int size_x, int size_y, MapRegion region){
        map = file; this.size_x = size_x; this.size_y = size_y;
        //System.out.println("set class vars");
        //System.out.println(map.isFile() + "");
        if(map.isFile()) {
            try {
                System.out.println("writing blank map");
                PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
                for (int r = 0; r < size_y; r++) {
                    String toWrite = "";
                    for (int c = 0; c < size_x; c++) {
                        toWrite += Node.TraversalState.DEFAULT + "\t"; //TSV
                    }
                    writer.println(toWrite);
                    writer.flush();
                }


            } catch (IOException e) {
                System.out.println("failed to serialize map to file");
            }
            //System.out.println("attempting to load " + region);
            loadRegion(region);
        }
    }

    /**
     * loads a 10 by 10 array starting at 0,0
     * @param file mapping file
     * @param size_x
     * @param size_y
     */
    public MapV2(File file, int size_x, int size_y){
        this(file, size_x, size_y, new MapRegion(0,0,10,10));
    }

    public void loadRegion (MapRegion region){
        writeRegion(loadedRegion);
        //System.out.println("called to load " + region);
        try {
            Scanner scanner = new Scanner(map);
            loadedNodes = new Node[region.y_size][region.x_size];
            //System.out.println("created array");
            for(int r = 0; r < region.y_off + region.y_size; r++){
                //System.out.println("row " + r);
                if(r <  region.y_off){
                    scanner.nextLine();
                    continue;
                }
                for(int c = 0; c < region.x_off + region.x_size; c++){
                    if(c < region.x_off){
                        scanner.nextInt();
                        continue;
                    }
                    loadedNodes[r - region.y_off][c - region.x_off] = new Node(r,c,Node.getStateFromInt(scanner.nextInt()));
                }
            }
            loadedRegion = region;
            System.out.println("loaded " + region);
        } catch (FileNotFoundException e) {
            System.out.println("failed to de-serialize region");
        }

    }

    private void writeRegion(MapRegion region){
        //System.out.println("writing " + loadedRegion);
        if(region == null) return;
        try {
            List<String> lines = Files.readAllLines(map.toPath());
            //System.out.println("Read file contents");
            for(int r = region.y_off; r < region.y_off + region.y_size; r++){
                String out = "";
                for(int c = region.x_off; c < region.x_off + region.x_size; c++){
                    out += loadedNodes[r - region.y_off][c - region.x_off].serialize() + "\t";
                }
                //System.out.println("re-writing line " + r);
                out = ((region.x_off > 0) ? lines.get(r).substring(0, region.x_off * 2) : "") + out +
                        ((lines.get(r).length() > (region.x_off + region.x_size) * 2)? lines.get(r).substring((region.x_off + region.x_size + 1) * 2) : "");
                lines.set(r, out);
            }
        } catch (IOException e) {
            System.out.println("failed to serialize region");
        }
        System.out.println(region + " written");
    }

    /**
     * method used to retrieve a specific node on a given map
     * <p>can be used to set node properties
     * @param y y coordinate
     * @param x x coordinate
     * @return Node object at the coordinate pair specified, null of OOB
     */
    public Node getNode(int x, int y){
        try {
            return loadedNodes[y][x];
        }
        catch (ArrayIndexOutOfBoundsException e ){
            return null;
        }
    }

    public void close(){
        loadedNodes = null;
        System.gc();
    }

    public LinkedList<Node> adjacentTo(Node node){

        LinkedList<Node> toReturn = new LinkedList<>();

        //left
        if(node.getPos_x() - 1 > -1 && loadedNodes[node.getPos_y()][node.getPos_x() - 1].canTraverse() && !loadedNodes[node.getPos_y()][node.getPos_x() - 1].getSearched()){
            loadedNodes[node.getPos_y()][node.getPos_x() - 1].setCameFrom(node);
            toReturn.add(loadedNodes[node.getPos_y()][node.getPos_x() - 1]);
        }

        //right
        if(node.getPos_x() + 1 < loadedNodes[0].length && loadedNodes[node.getPos_y()][node.getPos_x() + 1].canTraverse() && !loadedNodes[node.getPos_y()][node.getPos_x() + 1].getSearched()) {
            loadedNodes[node.getPos_y()][node.getPos_x() + 1].setCameFrom(node);
            toReturn.add(loadedNodes[node.getPos_y()][node.getPos_x() + 1]);
        }

        //above
        if(node.getPos_y() - 1 > -1 && loadedNodes[node.getPos_y() - 1][node.getPos_x()].canTraverse() && !loadedNodes[node.getPos_y() - 1][node.getPos_x()].getSearched()) {
            loadedNodes[node.getPos_y() - 1][node.getPos_x()].setCameFrom(node);
            toReturn.add(loadedNodes[node.getPos_y() - 1][node.getPos_x()]);
        }
        //below
        if(node.getPos_y() + 1 < loadedNodes.length && loadedNodes[node.getPos_y() + 1][node.getPos_x()].canTraverse() && !loadedNodes[node.getPos_y() + 1][node.getPos_x()].getSearched()) {
            loadedNodes[node.getPos_y() + 1][node.getPos_x()].setCameFrom(node);
            toReturn.add(loadedNodes[node.getPos_y() + 1][node.getPos_x()]);
        }
        return toReturn;
    }

    /**
     * Resets the mapping state of nodes for re-use
     * <p><b>Only resets path specific flags<b/>
     */
    public LinkedList<Node> getPathBetween(Node start, Node end){
        //prep for mapping process
        reset(); //reset mapping flags
        setGoal(end); //set new goal node
        LinkedList<Node> toVisit = new LinkedList<>();
        toVisit.add(start); //create new visitation list and add start node
        Node current; //create node for manipulation

        //System.out.println("mapping started");
        while(!toVisit.isEmpty()){
            current = toVisit.poll(); //pull the lowest f off the stack
            //System.out.println("searching " + current);
            if(current.equals(end)) break;
            toVisit.addAll(adjacentTo(current));
            Collections.sort(toVisit);// sort by lowest F value
            //System.out.println("sorted list to visit " + toVisit);
        }
        //System.out.println("iteration complete");

        return reconsruct(end, start);

    }

    private void reset(){
        for(Node[] nodes : loadedNodes){
            for(Node node : nodes){
                node.reset();
            }
        }
    }

    private void setGoal(Node goal){
        //System.out.println("setting goal");
        for(Node[] nodes : loadedNodes){
            for(Node node : nodes){
                node.setGoal(goal);
            }
        }
    }

    private LinkedList<Node> reconsruct(Node end, Node start){
        LinkedList<Node> path = new LinkedList<>();
        path.addFirst(end);
        Node prev = end.getCameFrom();
        //System.out.println("reconstructing map");
        while(prev != null && prev != start){
            path.addFirst(prev);
            prev = prev.getCameFrom();
            if(prev == null){
                return new LinkedList<>();  //short circuit to return empty list
            }
        }
        path.addFirst(start);
        //System.out.println("map reconstructed");
        return path;
    }
}
