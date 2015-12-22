package assign7;
import algs4.Stack;
import stdlib.Picture;

import java.awt.Color;


/**
 * Created by VGN on 12/18/15.
 */
public class SeamCarver {
    //static constants defining colorType
    private static final String RED = "red";
    private static final String GREEN = "green";
    private static final String BLUE = "blue";
    private Picture picture;
    private float[][] energy2D; //array to store energy of pixels from the picture
    private int[][] colors; //array to store the color of the picture


    /*
     create a seam carver object based on the given picture
    */
    public SeamCarver(Picture picture) {
        //deep copy of picture object
        this.picture = new Picture(picture);
        this.setColors();
        this.setEnergy2D();
    }

    /*
    current picture
    */
    public Picture picture() {
        return new Picture(picture);
    }

    /*
    width of current picture
    */
    public int width(){
        return this.picture.width();
    }

    /*
    height of current picture
    */
    public int height()  {
        return  this.picture.height();
    }

    /*
    energy of pixel at column x and row y
    */
    public  double energy(int x, int y)  {
        //validate pixel
        this.pixelValidation(x, y);
        //check border pixels
        if (this.isBorderPixel(x, y)){
            return 1000;
        }
        double deltaXSquare = this.computeDeltaSquare(true, x, y);
        double deltaYSquare = this.computeDeltaSquare(false, x, y);
        return Math.sqrt(deltaXSquare + deltaYSquare);
    }

    /*
       sequence of indices for horizontal seam
    */
    public int[] findHorizontalSeam(){

        return findSeam(true);
    }

    /*
     sequence of indices for vertical seam
    */
    public   int[] findVerticalSeam(){
        return findSeam(false);
    }

    /*
    find the sequence of indices if vertical/horizontal
     */
    private int[] findSeam(boolean horizontal){
        if (horizontal)
            transposeEnergy2D();
        Stack<Integer> stack  = computeShortestPath();
        int[] seam = seam(stack);
        if (horizontal)
            transposeEnergy2D();
        return seam;
    }

    /*
      remove vertical seam from current picture
     */
    public  void removeVerticalSeam(int[] seam){
        removeSeam(seam, false);

    }

    /*
      remove horizontal seam from current picture
    */
    public void removeHorizontalSeam(int[] seam) {
        removeSeam(seam, true);
    }


    /*
    Update picture from colors array
     */
    private void updatePicture(){
        int height = colors.length;
        int width = colors[0].length;
        picture = new Picture(width, height);
        for (int h =0; h < height; h++){
            for (int w=0; w< width; w++){
                int rgb = colors[h][w];
                picture.set(w, h, new Color(rgb));
            }
        }
    }

    /*
    update colors by removing the entries located in seam
     */
    private void updateColors(int[] seam){
        int height = this.colors.length;
        int width = this.colors[0].length;
        //width goes down by 1
        int[][] newColors = new int[height][width-1];
        for(int i=0; i< height; i++){
            int counter = 0;
            for (int j=0; j < width; j++){
                if (j == seam[i]){
                    continue;
                }
                int color = colors[i][j];
                newColors[i][counter++] = color;

            }
        }
        this.colors = newColors;
    }

    /*
        Set the 2dEnergy array from Picture
    */
    private void setEnergy2D(){
        int height = colors.length;
        int width = colors[0].length;
        this.energy2D = new float[height][width];
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                energy2D[h][w] = (float)this.energy(w, h);
            }
        }
    }

    /*
    Set the Colors array from Picture
    */
    private void setColors(){
        this.colors = new int[picture.height()][picture.width()];
        for (int height = 0; height < picture.height(); height++) {
            for (int width = 0; width < picture.width(); width++) {
                colors[height][width] = this.picture.get(width, height).getRGB();
            }
        }
    }


    /*
   Remove seam from the picture
   */
    private void removeSeam(int[] seam, boolean horizontal){
        if (seam == null){
            throw new java.lang.NullPointerException("Cannot have null seam");
        }
        if (horizontal)
            transposeColors();
        validateSeam(seam);
        updateColors(seam);
        if (horizontal)
            transposeColors();
        setEnergy2D();
        updatePicture();
    }

    private  void transposeEnergy2D(){
        float[][] temp = new float[energy2D[0].length][energy2D.length];
        for (int r = 0; r < energy2D.length; r++) {
            for (int c = 0; c < energy2D[0].length; c++) {
                temp[c][r] = energy2D[r][c];
            }
        }
        this.energy2D = temp;
    }

    private  void transposeColors(){
        int[][] temp = new int[colors[0].length][colors.length];
        for (int r = 0; r < colors.length; r++) {
            for (int c = 0; c < colors[0].length; c++) {
                temp[c][r] = colors[r][c];
            }
        }
        this.colors = temp;
    }

    /*
      Return the row no of the pixel based on the position in internal energy array
     */
    private int getCol(int pos){
        return pos % energy2D[0].length;
    }

    /*
      Return the neighbors for a given pixel , based on the position in internal energy array
     */
    private int[] computeNeighborsIndex(int pos){
        int width = this.energy2D[0].length;
        int height = this.energy2D.length;
        int row = this.rowCol(pos)[0];
        int col = this.rowCol(pos)[1];
        int down = pos + width; //down position
        int downLeft = down -1; //down left position
        int downRight = down + 1; //down right position
        //last row
        if (row == height -1){
            return new int[]{};
        }
        //first column has no downLeft, only 2 neighbors
        if (col == 0){
            if (rowCol(down)[0] == rowCol(downRight)[0])
                return new int[]{down, downRight};
            if (rowCol(down)[0] == row +1)
                return new int[]{down};
            return new int[]{};
        }
        //last column has no downRight , only 2 neighbors
        if (col == width -1){
            if (rowCol(down)[0] == rowCol(downLeft)[0])
                return new int[]{down, downLeft};
            if (rowCol(down)[0] == row +1)
                return new int[]{down};
            return new int[]{};
        }
        //remaining
        if (rowCol(down)[0] == rowCol(downLeft)[0] && (rowCol(down)[0] == rowCol(downRight)[0]))
            return new int[]{down, downLeft, downRight};
        if (rowCol(down)[0] == row +1)
            return new int[]{down};
        return new int[]{};
    }

    /*
       Validate the pixel position(x, y)
    */
    private void pixelValidation(int x, int y){
        int width = colors[0].length;
        int height = colors.length;
        if (x < 0 || x >= width){
            throw new java.lang.IndexOutOfBoundsException("Width of picture out of range");
        }

        if (y < 0 || y >= height){
            throw new java.lang.IndexOutOfBoundsException("Height of picture out of range");
        }
    }

    /*
        Compute the square delta for the given position(x,y) based on horizontal/vertical seam
    */
    private double computeDeltaSquare(boolean isHorizontal, int x, int y){
        double redDeltaSquare = this.computeColorDeltaSquare(isHorizontal, x, y, RED);
        double greenDeltaSquare = this.computeColorDeltaSquare(isHorizontal, x, y, GREEN);
        double blueDeltaSquare = this.computeColorDeltaSquare(isHorizontal, x, y, BLUE);
        return redDeltaSquare + greenDeltaSquare + blueDeltaSquare;
    }

    /*
        Compute the square delta for the color on the given position(x,y) based on horizontal/vertical seam
    */
    private double computeColorDeltaSquare(boolean isHorizontal, int x, int y , String colorType){
        double successor = this.computeSuccessor(isHorizontal, x, y, colorType);
        double predecessor = this.computePredecessor(isHorizontal, x, y, colorType);
        double delta = successor - predecessor;
        return Math.pow(delta, 2.0);
    }

    /*
        Compute the color value for successor row/col
     */
    private double computeSuccessor(boolean isHorizontal, int x, int y, String colorType){
        if (isHorizontal){
            x = x +1;
        }
        else{
            y = y + 1;
        }
        return this.computeColorValue(x, y, colorType);
    }

    /*
        Compute the color value for predecessor row/col
    */
    private double computePredecessor(boolean isHorizontal, int x, int y, String colorType){
        if (isHorizontal){
            x = x -1;
        }
        else{
            y = y -1;
        }
        return this.computeColorValue(x, y, colorType);
    }

    /*
      Compute the color value at the given position(x,y)
     */
    private double computeColorValue(int x, int y, String colorType){
        int color = this.colors[y] [x];
        if (colorType.equals("red"))
            return (color >> 16) & 0x000000FF;
        if (colorType.equals("green"))
            return (color >>8 ) & 0x000000FF;
        return (color) & 0x000000FF;
    }

    /*
      Check if the given position is a border pixel
     */
    private boolean isBorderPixel(int x, int y){
        int width = colors[0].length;
        int height = colors.length;
        if (x+1 >= width){
            return true;
        }
        if (x -1 < 0){
            return true;
        }
        if (y+1 >= height){
            return true;
        }
        if (y-1 < 0){
            return true;
        }
        return false;
    }

    private int pos(int row, int col){
        return (row * this.energy2D[0].length + col);
    }

    private int[] rowCol(int pos){
        int row = pos/this.energy2D[0].length;
        int col = pos%this.energy2D[0].length;
        return new int[]{row, col};
    }

    /*
      Compute shortest path from pos 0 in internal energy array using variant of top ordering in DAG
     */
    private Stack<Integer> computeShortestPath(){
        //init data structures
        int counter = 0;
        double [] distTo = new double[energy2D.length*energy2D[0].length];
        int[] pathTo = new int[energy2D.length*energy2D[0].length];
        for (int height = 0; height < energy2D.length; height++) {
            for (int width = 0; width < energy2D[0].length; width++) {
                distTo[counter] = Double.POSITIVE_INFINITY;
                pathTo[counter++] = -1;

            }
        }
        int sourcePathTo = -1;// track of source variable
        int pathToSink = -1; // track of sink variable
        double distSink = Double.POSITIVE_INFINITY;
        //relax all the vertices in first row
        for (int j =0; j < energy2D[0].length; j++){
            double val = energy2D[0][j];
            int pos = this.pos(0, j);
            distTo[pos]= val;
        }
        for (int i=0; i < energy2D.length; i++){
            for (int j=0; j < energy2D[0].length; j++){
                int pos = this.pos(i, j);
                int[] neighbors = this.computeNeighborsIndex(pos);
                if (neighbors.length == 0){
                    //last row, connected to special sink
                    if (distSink > distTo[pos] + energy2D[i][j]){
                        distSink = distTo[pos] + energy2D[i][j];
                        pathToSink = pos;
                    }
                    continue;
                }
                for (int neighbor: neighbors){
                    //relaxation
                    if (distTo[neighbor] > distTo[pos] + this.energy2D[i][j]){
                        distTo[neighbor] = distTo[pos] + this.energy2D[i][j];
                        pathTo[neighbor] = pos;
                    }

                }
            }
        }
        //stack of positions in shortest path(pos0 - pos(energyLength-1) from the pathTo array
        Stack<Integer> stack = new Stack<Integer>();
        int index = pathToSink;
        while (index != sourcePathTo){
            stack.push(index);
            index = pathTo[index];
        }
        return stack;
    }

    /*
    Compute the vertical seam from the stack of positions in internal energy array
     */
    private int[] seam(Stack<Integer> stack){
        int [] seam = new int[stack.size()];
        int counter = 0;
        for (Integer pos: stack){
            int col = this.getCol(pos);
            seam[counter++] = col;
        }
        return seam;
    }

    private void printArray(double[][] energy){
        // Print original array.
        System.out.println ("Original array:");
        for (int r = 0; r < energy.length; r++) {
            for (int c = 0; c < energy[0].length; c++)
                System.out.print (energy[r][c] + " ");
            System.out.println();
        }
    }

    /*
   validate seam
    */
    private void validateSeam(int[] seam){

        int dimension = this.colors.length;
        int adjDim = this.colors[0].length;
        if (seam.length != dimension){
            throw new IllegalArgumentException("Seam length does not match picture dimension");
        }
        if (dimension < 1 ){
            throw new IllegalArgumentException("Cannot remove seam when picture dimension is less than 1");
        }
        int prevEntry = seam[0];
        for (int i=0; i< seam.length;i++){
            if (seam[i] <0 || seam[i] >= adjDim){
                throw new IllegalArgumentException("Seam entry out of bounds" + i);
            }
            if (i != 0){
                if (Math.abs(seam[i] - prevEntry) > 1 ){
                    throw new IllegalArgumentException("Seam entry at " + i + "exceeds prevEntry by more than 1");
                }
                prevEntry = seam[i];
            }
        }
    }


    public static void main(String[] args){
        Picture picture = new Picture(args[0]);
        SeamCarver sc = new SeamCarver(picture);
        //sc.printArray(sc.energy2D);
        int[] seam = sc.findVerticalSeam();
        sc.removeVerticalSeam(seam);
        System.out.println("New matrix");
        //sc.printArray(sc.energy2D);

        //sc.transposeEnergy2D();
        //sc.printArray(sc.energy2D);
    }


}
