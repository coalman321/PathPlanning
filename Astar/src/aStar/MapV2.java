package aStar;

import java.io.*;
import java.nio.file.Files;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class MapV2 {

    private int size_x, size_y;
    private Node[][] loadedRegion;
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
        try {
            //System.out.println("serializing map");
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            for(int r = 0; r < size_y; r++){
                String toWrite = "";
                for(int c = 0; c < size_x; c++){
                    toWrite += Node.TraversalState.DEFAULT + "\t"; //TSV
                }
                writer.println(toWrite);
                writer.flush();
            }
            loadRegion(region);

        } catch (IOException e) {
            System.out.println("failed to serialize map to file");
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
        writeRegion(region);
        //System.out.println("deserializing map");
        try {
            Scanner scanner = new Scanner(map);
            loadedRegion = new Node[region.y_size][region.x_size];
            for(int r = 0; r < region.y_off + region.y_size; r++){
                if(r <  region.y_off){
                    scanner.nextLine();
                    continue;
                }
                for(int c = 0; c < region.x_off + region.x_size; c++){
                    if(r < region.x_off){
                        scanner.nextInt();
                        continue;
                    }
                    loadedRegion[r][c] = new Node(r,c,Node.getStateFromInt(scanner.nextInt()));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("failed to de-serialize region");
        }

    }

    private void writeRegion(MapRegion region){
        try {
            List<String> lines = Files.readAllLines(map.toPath());
            for(int r = 0; r < region.y_off + region.y_size; r++){
                if(r <  region.y_off){
                    continue;
                }
                for(int c = 0; c < region.x_off + region.x_size; c++){
                    if(r < region.x_off){
                        scanner.nextInt();
                        continue;
                    }
                    //exec
                }
            }
        } catch (IOException e) {
            System.out.println("failed to serialize region");
        }
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
            return loadedRegion[y][x];
        }
        catch (ArrayIndexOutOfBoundsException e ){
            return null;
        }
    }

    public void close(){
        loadedRegion = null;
        System.gc();
    }

    public LinkedList<Node> adjacentTo(Node node){

        LinkedList<Node> toReturn = new LinkedList<>();

        //left
        if(node.getPos_x() - 1 > -1 && loadedRegion[node.getPos_y()][node.getPos_x() - 1].canTraverse() && !loadedRegion[node.getPos_y()][node.getPos_x() - 1].getSearched()){
            loadedRegion[node.getPos_y()][node.getPos_x() - 1].setCameFrom(node);
            toReturn.add(loadedRegion[node.getPos_y()][node.getPos_x() - 1]);
        }

        //right
        if(node.getPos_x() + 1 < loadedRegion[0].length && loadedRegion[node.getPos_y()][node.getPos_x() + 1].canTraverse() && !loadedRegion[node.getPos_y()][node.getPos_x() + 1].getSearched()) {
            loadedRegion[node.getPos_y()][node.getPos_x() + 1].setCameFrom(node);
            toReturn.add(loadedRegion[node.getPos_y()][node.getPos_x() + 1]);
        }

        //above
        if(node.getPos_y() - 1 > -1 && loadedRegion[node.getPos_y() - 1][node.getPos_x()].canTraverse() && !loadedRegion[node.getPos_y() - 1][node.getPos_x()].getSearched()) {
            loadedRegion[node.getPos_y() - 1][node.getPos_x()].setCameFrom(node);
            toReturn.add(loadedRegion[node.getPos_y() - 1][node.getPos_x()]);
        }
        //below
        if(node.getPos_y() + 1 < loadedRegion.length && loadedRegion[node.getPos_y() + 1][node.getPos_x()].canTraverse() && !loadedRegion[node.getPos_y() + 1][node.getPos_x()].getSearched()) {
            loadedRegion[node.getPos_y() + 1][node.getPos_x()].setCameFrom(node);
            toReturn.add(loadedRegion[node.getPos_y() + 1][node.getPos_x()]);
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
        for(Node[] nodes : loadedRegion){
            for(Node node : nodes){
                node.reset();
            }
        }
    }

    private void setGoal(Node goal){
        //System.out.println("setting goal");
        for(Node[] nodes : loadedRegion){
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
