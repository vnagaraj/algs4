package assign5;

import algs4.Point2D;
import algs4.Queue;
import stdlib.In;
import stdlib.StdDraw;

/**
 * Created by VGN on 7/28/15.
 */
public class KdTree {

    private Node root;
    private int size =0;

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        private boolean isHorizontal; // for drawing splitting lines

        Node(Point2D p){
            this.p = p;
        }
    }

    // construct an empty set of points
    public KdTree()  {

    }

    // is the set empty?
    public boolean isEmpty()     {
        return size == 0;
    }

    // number of points in the set
    public  int size()   {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public  void insert(Point2D p)     {
        if (p == null)
            throw new NullPointerException();
        boolean isX = true;
        double xmin = 0;
        double ymin = 0;
        double xmax = 1;
        double ymax = 1;
        if (!this.contains(p))
            root = this.insert(root, p, true, 0, 0 ,1,1 );
    }

    private Node insert(Node x, Point2D p, boolean isX, double xmin, double ymin, double xmax, double ymax){
        if (x == null){
            size += 1;
            Node n = new Node(p);
            n.rect = new RectHV(xmin, ymin, xmax, ymax);
            return n;
        }
        if (isX){
            if (p.x() <  x.p.x()) {
                xmax = x.p.x();
                x.lb = insert(x.lb, p, !isX, xmin,ymin, xmax, ymax);
            }
            else{
                xmin = x.p.x();
                x.rt = insert(x.rt, p, !isX,xmin,ymin, xmax, ymax);
            }
        }
        else{
            if (p.y() < x.p.y()){
                ymax = x.p.y();
                x.lb = insert(x.lb, p, !isX, xmin,ymin, xmax, ymax);
            }
            else{
                ymin = x.p.y();
                x.rt = insert(x.rt, p, !isX, xmin,ymin, xmax, ymax);
            }
        }
        return x;
    }

    // does the set contain point p?
    public  boolean contains(Point2D p)   {
        if (p == null)
            throw new NullPointerException();
        return this.contains(root, p, true);
    }

    private boolean contains(Node x, Point2D p, boolean isX){
        if (x == null) return false;
        if (p.equals(x.p)){
            return true;
        }
        if (isX){
            if (p.x() <  x.p.x()) {
                return contains(x.lb,p,!isX);
            }
            else {
                return contains(x.rt,p,!isX);
            }
        }
        else {
            if (p.y() <  x.p.y()) {
                return contains(x.lb,p,!isX);
            }
            else {
               return contains(x.rt,p,!isX);
            }
        }

    }

    private void addNodeChildren(Queue<Node> queue, Node node){
        if (node.lb != null){
            queue.enqueue(node.lb);
            if (node.isHorizontal){
                node.lb.isHorizontal = false;
            }
            else{
                node.lb.isHorizontal = true;
            }

        }
        if (node.rt != null){
            queue.enqueue(node.rt);
            if (node.isHorizontal){
                node.rt.isHorizontal = false;
            }
            else{
                node.rt.isHorizontal = true;
            }
        }
    }

    private void drawPoint(Node node){
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        node.p.draw();
    }

    private void drawSplitLines(Node node){
        StdDraw.setPenRadius();
        if (node.isHorizontal){
            double ycord = node.p.y();
            double xmin = node.rect.xmin();
            double xmax = node.rect.xmax();
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(xmin,ycord,xmax,ycord);
        }
        else{
            double xcord = node.p.x();
            double ymin = node.rect.ymin();
            double ymax = node.rect.ymax();
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(xcord,ymin,xcord,ymax);
        }
    }

    // draw all points to standard draw
    public  void draw() {
        Queue<Node> queue = new Queue<Node>();
        queue.enqueue(root);
        while (!queue.isEmpty()){
           Node node = queue.dequeue();
           System.out.println(node.p);
           drawSplitLines(node);
           drawPoint(node);
           StdDraw.show(50);
           addNodeChildren(queue, node);
        }

    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect){
        if (rect == null)
            throw new NullPointerException();
        Queue<Point2D> point2DQueue = new Queue<Point2D>();
        rangeSearch(point2DQueue, rect, root);
        return point2DQueue;
    }

    private void rangeSearch(Queue<Point2D> queue, RectHV rect , Node node){
        if (node == null)
            return;
        if (rect.intersects(node.rect)){
            if (rect.contains(node.p)) {
                queue.enqueue(node.p);
            }
            rangeSearch(queue, rect, node.lb);
            rangeSearch(queue, rect, node.rt);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p)   {
        if (p == null)
            throw new NullPointerException();
        if (root == null) //size is 0
                return null;
        Point2D nearestNeighbor = root.p;
        return nearestNeighbor(p,root, nearestNeighbor);
    }

    private Point2D nearestNeighbor(Point2D p, Node node, Point2D nearestNeighbor){
        if (node == null)
            return nearestNeighbor;
        if (nearestNeighbor.distanceSquaredTo(p) > (node.rect.distanceSquaredTo(p))){
            if (nearestNeighbor.distanceSquaredTo(p) > node.p.distanceSquaredTo(p)){
                nearestNeighbor = node.p;
            }
            if (node.lb != null && node.lb.rect.contains(p)) {
                nearestNeighbor = nearestNeighbor(p, node.lb, nearestNeighbor);
                nearestNeighbor = nearestNeighbor(p, node.rt, nearestNeighbor);
            }
            else{
                nearestNeighbor = nearestNeighbor(p, node.rt, nearestNeighbor);
                nearestNeighbor = nearestNeighbor(p, node.lb, nearestNeighbor);
            }
        }
        return nearestNeighbor;

    }

    // unit testing of the methods (optional)
    public static void main(String[] args)   {
        String filename = args[0];
        In in = new In(filename);
        StdDraw.show(0);

        // initialize the data structures with N points from standard input
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            brute.insert(p);
        }
        kdtree.draw();
        Point2D p = new Point2D(.81, .30);
        System.out.println("Nearest neighbor");
        System.out.println("KD" + kdtree.nearest(p));
        System.out.println("Brute" + brute.nearest(p));



    }
}
