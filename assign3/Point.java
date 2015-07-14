package assign3; /*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;
import stdlib.StdDraw;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER =  new Comparator<Point>() {
        public int compare(Point q, Point r) {
        /* YOUR CODE HERE */
            if (Point.this.slopeTo(q) < Point.this.slopeTo(r)){
                return -1;
            }
            else if (Point.this.slopeTo(q) > Point.this.slopeTo(r)){
                return 1;
            }
            return 0;
        }
    };

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        int deltaX = that.x - this.x;
        int deltaY = that.y - this.y;
        if (deltaX == 0 && deltaY != 0){
            //vertical line segment
            return Double.POSITIVE_INFINITY;
        }
        if (deltaX == 0 && deltaY == 0){
            //degenerate case
            return Double.NEGATIVE_INFINITY;
        }
        // horizontal line segment
        double slope =  (double)deltaY/deltaX;
        if (slope == -0.0){
            return 0.0;
        }
        return slope;
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (this.y < that.y ){
            return -1;
        }
        if (this.y == that.y && this.x < that.x){
            //breaking ties
            return -1;
        }
        if (this.y == that.y && this.x == that.x){
            return 0;
        }
        return 1;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }



    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        Point p = new Point(143,301);
        Point q = new Point(125,301);
        System.out.println(p.slopeTo(q));
    }
}