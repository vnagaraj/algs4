package assign4.InterviewQuestions;

import org.testng.Assert;
import org.testng.annotations.Test;



import static org.testng.Assert.*;

/**
 * Created by VGN on 11/21/15.
 */

class MedianFinder {

    PQ<Integer> minPQ = new PQ<Integer>(false);
    PQ<Integer> maxPQ = new PQ<Integer>(true);


        // Adds a number into the data structure.
        public void addNum(int num) {
            if (minPQ.getSize() == 0){
                minPQ.insert(num);
                return;
            }
            if (maxPQ.getSize() == 0){
                int val = minPQ.getMaxMin();
                if (num < val){
                    //smaller value should go in maxPQ
                    maxPQ.insert(num);
                }
                else{
                    //num > val
                    minPQ.deleteMaxMin();
                    minPQ.insert(num);
                    maxPQ.insert(val);

                }
                return;
            }
            int max = minPQ.getMaxMin();
            if (num > max){
                minPQ.insert(num);
            }
            else{
                maxPQ.insert(num);
            }
            resizeHeaps();

        }

         private void resizeHeaps(){
             //resize incase difference in heaps size is greater than 1
             int size1 = minPQ.getSize();
             int size2 = maxPQ.getSize();
             if (size1 > size2 + 1){
                 int val = minPQ.deleteMaxMin();
                 maxPQ.insert(val);
             }
             else if (size2 > size1 + 1){
                 int val = maxPQ.deleteMaxMin();
                 minPQ.insert(val);
             }
         }
        // Returns the median of current data stream
        public double findMedian() {
            int size1 = minPQ.getSize();
            int size2 = maxPQ.getSize();
            if (size1 == size2){
                return ((minPQ.getMaxMin()+ maxPQ.getMaxMin())/2.0);
            }
            else if (size1 > size2){
                return minPQ.getMaxMin();
            }
            else{
                return maxPQ.getMaxMin();
            }
        }
 }


class PQ<key extends Comparable<key>>{
    private key[] array;
    private int size ;
    private boolean isMax; //min or max priority queue

    PQ(boolean isMax){
        array = (key[])new Comparable[2];
        this.isMax = isMax;
    }

    public int getSize(){
        return size;
    }

    public void insert(key key){
        if (size + 1 == array.length){// due to 1 based indexing
            resize((size+1) * 2);
        }
        array[++size] = key;
        this.swim(size);
    }

    public key deleteMaxMin(){
        key val = array[1];
        //exchange with last value
        array[1] = array[size];
        array[size--] = null ;// to avoid loitering
        sink(1);
        return val;
    }

    private void sink(int index){
        int child = 2 * index;
        while (child <= size){
            child = computeMinMaxChild(child);
            if (compare(array[index],array[child])){
                //exchange element with parent
                key temp = array[child];
                array[child] = array[index];
                array[ index] = temp;
            }
            index = child;
            child = 2 * index;
        }
    }

    private int computeMinMaxChild(int child){
       int index = -1;
       if (child + 1 > size){
           return child;
       }
       int child1 = child +1;
       if (this.array[child].compareTo(this.array[child1]) < 0){
           if (this.isMax)
               index = child1;
           else
            index = child;
       }
       else{
           if (this.isMax)
               index = child;
           else
            index = child1;
       }
       return index;
    }

    /*
    get max value for max priority queue
    min value for min priority queue
     */
    public key getMaxMin(){
        return this.array[1];
    }
    /*
    Compares the 1st element with 2nd element
    Returns true if 1st element is smaller for max PQ
    Returns false if 1st element is larger for min PQ
     */
    private boolean compare(key element1, key element2){

        if (this.isMax){
            if (element1.compareTo(element2) < 0){
                return true;
            }
        }
        else{
            if (element1.compareTo(element2) > 0){
                return true;
            }
        }
        return false;
    }



    private void swim(int index){
        int parent = index/2;
        while (parent >= 1){
            if (compare(array[parent],array[index])){
                //exchange element with parent
                key temp = array[parent];
                array[parent] = array[index];
                array[ index] = temp;
            }
            index = parent;
            parent = index/2;
        }
    }

    private void resize(int length){
        key[] temp = (key[])new Comparable[length];
        for (int i=0; i < array.length; i++){
            temp[i] = array[i];
        }
        array = temp;
    }
}

public class MedianFinderTest {

    @Test
    public void test1_maxpriorityqueue_insert(){
        PQ<Integer> pq = new PQ<Integer>(true);
        pq.insert(5);
        pq.insert(30);
        assertTrue(pq.getMaxMin() == 30);
        pq.insert(40);
        assertTrue(pq.getMaxMin() == 40);
        pq.insert(23);
        assertTrue(pq.getMaxMin() == 40);
        pq.insert(19);
        assertTrue(pq.getMaxMin() == 40);
        pq.insert(51);
        assertTrue(pq.getMaxMin() == 51);
    }

    @Test
    public void test2_maxpriorityqueue_insert(){
        PQ<Integer> pq = new PQ<Integer>(true);
        pq.insert(6);
        pq.insert(2);
        pq.insert(5);
        pq.insert(0);
        assertTrue(pq.deleteMaxMin() == 6);
        assertTrue(pq.getMaxMin() == 5);
    }

    @Test
    public void test1_minpriorityqueue_insert(){
        PQ<Integer> pq = new PQ<Integer>(false);
        pq.insert(5);
        pq.insert(30);
        assertTrue(pq.getMaxMin() == 5);
        pq.insert(40);
        assertTrue(pq.getMaxMin() == 5);
        pq.insert(23);
        assertTrue(pq.getMaxMin() == 5);
        pq.insert(19);
        assertTrue(pq.getMaxMin() == 5);
        pq.insert(1);
        assertTrue(pq.getMaxMin() == 1);
    }

    @Test
    public void test3_maxpriorityqueue_delete(){
        PQ<Integer> pq = new PQ<Integer>(true);
        pq.insert(5);
        pq.insert(30);
        assertTrue(pq.getMaxMin() == 30);
        pq.deleteMaxMin();
        assertTrue(pq.getMaxMin() == 5);
        pq.insert(23);
        assertTrue(pq.getMaxMin() == 23);
        pq.deleteMaxMin();
        assertTrue(pq.getMaxMin() == 5);
    }

    @Test
    public void test4_minpriorityqueue_delete(){
        PQ<Integer> pq = new PQ<Integer>(false);
        pq.insert(3);
        pq.insert(4);
        pq.insert(5);
        pq.insert(6);
        assertTrue(pq.deleteMaxMin() == 3);
        assertTrue(pq.getMaxMin() == 4);
    }

    @Test
    public void test4_medianfinder(){
        MedianFinder mf = new MedianFinder();
        mf.addNum(1);
        mf.addNum(2);
        assertTrue(mf.findMedian() == 1.5);
        mf.addNum(3);
        assertTrue(mf.findMedian() == 2.0);
    }

    @Test
    public void test5_medianfinder(){
        MedianFinder mf = new MedianFinder();
        mf.addNum(1);
        mf.addNum(2);
        mf.addNum(3);
        mf.addNum(4);
        mf.addNum(5);
        mf.addNum(6);
        assertTrue(mf.findMedian() == 3.5);
    }

    @Test
    public void test6_medianfinder(){
        MedianFinder mf = new MedianFinder();
        mf.addNum(6);
        mf.addNum(10);
        mf.addNum(2);
        mf.addNum(6);
        mf.addNum(5);
        mf.addNum(0);
        System.out.println(mf.findMedian());
        assertTrue(mf.findMedian() == 5.5);
    }
}

