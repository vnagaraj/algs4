package assign3;

import stdlib.In;
import stdlib.StdDraw;

import java.util.Arrays;

/**
 * Created by VGN on 7/11/15.
 */
public class Fast {
    public static void main(String[] args){
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0,32768);
        StdDraw.setPenColor(StdDraw.BOOK_BLUE);
        String filename = args[0];
        In in = new In(filename);
        int pointCount = Integer.parseInt(in.readString());
        Point[] pArray = new Point[pointCount];
        Point[] slopeArray = new Point[pointCount];
        int count = 0;
        while (count < pointCount){
            int x = Integer.parseInt(in.readString());
            int y = Integer.parseInt(in.readString());
            Point p = new Point(x,y);
            p.draw();
            pArray[count] = p;
            slopeArray[count] = p;
            count++;
        }
        if (pArray.length < 4){
            return;
        }
        Arrays.sort(slopeArray);
        for (Point p: pArray){
            Arrays.sort(slopeArray, p.SLOPE_ORDER);
            int i =1;
            int j =2;
            while (true){
                double slope = slopeArray[0].slopeTo(slopeArray[i]);
                double slope2 = slopeArray[0].slopeTo(slopeArray[j]);
                if (slope != slope2){
                    if (j-i >= 3){
                            printPoints(slopeArray, i, j);
                    }
                    i = j;
                }
                j++;
                if (j>= slopeArray.length){
                    break;
                }
                if (i>= slopeArray.length){
                    break;
                }
            }
            int length = j-i;
            if (length >=3){
                    printPoints(slopeArray, i, j);
            }
            Arrays.sort(slopeArray);

        }
    }


    private static void printPoints(Point[] slopeArray, int i, int j) {
        if (slopeArray[0].compareTo(slopeArray[i]) >=0){
            return;
        }
        System.out.print(slopeArray[0]);
        for (int count=i; count < j; count ++){
            if (count != j)
                System.out.print("->");
            if (count == j-1){
                System.out.println(slopeArray[count]);
            }
            else
                System.out.print(slopeArray[count]);
        }
        slopeArray[0].drawTo(slopeArray[j-1]);
    }

}
