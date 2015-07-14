package assign3;
import stdlib.In;
import stdlib.StdDraw;

import java.util.Arrays;
/**
 * Created by VGN on 7/11/15.
 */
public class Brute {

    public static void main(String[] args){
        StdDraw.setXscale(0,32768);
        StdDraw.setYscale(0,32768);
        StdDraw.setPenColor(StdDraw.BOOK_BLUE);
        String filename = args[0];
        In in = new In(filename);
        int pointCount = Integer.parseInt(in.readString());
        Point[] pArray = new Point[pointCount];
        int count = 0;
        while (count < pointCount){
           int x = Integer.parseInt(in.readString());
           int y = Integer.parseInt(in.readString());
           Point p = new Point(x,y);
           p.draw();
           pArray[count++] = p;
        }
        Arrays.sort(pArray);
        for (int i =0; i< pArray.length; i++){
            for (int j=i+1; j< pArray.length; j++){
                for (int k= j+1; k< pArray.length; k++){
                    for (int l=k+1; l<pArray.length; l++){
                        Point p = pArray[i];
                        Point q = pArray[j];
                        Point r = pArray[k];
                        Point s = pArray[l];
                        if ((p.slopeTo(q) == (p.slopeTo(r)) && (p.slopeTo(q)== p.slopeTo(s)))){
                            System.out.println(p +"->"+ q + "->" + r + "->" + s);
                            p.drawTo(s);

                        }
                    }

                }

            }
            StdDraw.show(0);
        }





    }
}
