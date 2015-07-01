package assign2;

import stdlib.StdIn;

/**
 * Created by VGN on 6/30/15.
 */
public class Subset {
    public static void main(String[] args){
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();
        while(!StdIn.isEmpty()) {
            String str = StdIn.readString();
            randomizedQueue.enqueue(str);
        }
        int index =0;
        while (index < k){
            System.out.println(randomizedQueue.dequeue());
            index++;
        }

    }
}
