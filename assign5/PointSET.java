package assign5;

import algs4.Point2D;
import algs4.Queue;
import algs4.SET;

/**
 * Created by VGN on 7/28/15.
 */
public class PointSET {
    private SET<Point2D> point2Ds;

    // construct an empty set of points
    public PointSET()  {
        point2Ds = new SET<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty()     {
        return point2Ds.isEmpty();
    }

    // number of points in the set
    public  int size()   {
        return point2Ds.size();
    }

    // add the point to the set (if it is not already in the set)
    public  void insert(Point2D p)     {
        if (p == null)
            throw new NullPointerException();
        this.point2Ds.add(p);
    }

    // does the set contain point p?
    public  boolean contains(Point2D p)   {
        if (p == null)
            throw new NullPointerException();
        return this.point2Ds.contains(p);
    }

    // draw all points to standard draw
    public  void draw() {
        for (Point2D point2D: this.point2Ds){
            point2D.draw();
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect){
        if (rect == null)
            throw new NullPointerException();
        Queue<Point2D> point2DQueue = new Queue<Point2D>();
        for (Point2D point2D: this.point2Ds) {
            if (rect.contains(point2D)){
                point2DQueue.enqueue(point2D);
            }
        }
        return point2DQueue;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p)   {
        if (p == null)
            throw new NullPointerException();
        Point2D nearestNeighbor = null;
        for (Point2D point2D: this.point2Ds){
                if (nearestNeighbor == null){
                    nearestNeighbor = point2D;
                }
                else{
                    if (p.distanceTo(point2D)< (p.distanceTo(nearestNeighbor))){
                        nearestNeighbor = point2D;
                    }
                }

        }
        return nearestNeighbor;

    }

    // unit testing of the methods (optional)
    public static void main(String[] args)   {

    }
}
