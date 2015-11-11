package assign1.InterviewQuestions;

import org.testng.annotations.Test;
import java.util.Arrays;


import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

/**
 * Created by VGN on 11/9/15.
 */

class BinarySearchUtil {

    public static int computeThreeSum(int[] array){
        /* find unique triplets such that sum =0 */
        Arrays.sort(array);
        int count =0;
        for (int i=0; i < array.length; i++){
            for (int j=i+1; j < array.length; j++){
                int value = array[i] + array[j];
                if (j+1 == array.length)
                    continue;
                if (binarySearch(array, j+1, array.length-1, -value)){
                    System.out.println(array[i] + " " + array[j] + " " + -value);
                    count ++;
                }
            }
        }
        return count;
    }


    public static boolean binarySearch(int[] array, int value){
        return binarySearch(array, 0, array.length-1, value);
    }

    public static boolean binarySearch(int[] array, int start_index, int end_index, int value){
        //assumption: arrays sorted from ascending to descending order

        int middle = (end_index - start_index)/2 + start_index;
        if (array[middle] == value){
            return true;
        }
        if (start_index >= end_index){
            return false; //base case
        }
        if (array[middle] > value){
            //search 1st half of array
            return binarySearch(array, start_index, middle-1, value);
        }
        else{ // (array[middle] < value)
            return binarySearch(array, middle+1, end_index, value);
        }
    }

    public static boolean binarySearchDesc(int[] array, int start_index, int end_index, int value){
        //assumption: arrays sorted from descending to ascending order
        if (start_index > end_index){
            return false; //base case
        }
        int middle = (end_index - start_index)/2 + start_index;
        if (array[middle] == value){
            return true;
        }
        else if (array[middle] < value){
            //search 1st half of array
            return binarySearchDesc(array, start_index, middle-1, value);
        }
        else{ // (array[middle] > value)
            return binarySearchDesc(array, middle+1, end_index, value);
        }
    }

    public static int findMaxBitonicArray(int[] array){
        /*find max index in a bitonic array(increasing seq followed by decreasing seq)*/
        return findMaxBitonicArray(array, 0, array.length - 1);
    }

    public static int findMaxBitonicArray(int[] array, int start, int end){
        if (end - start <=2){
            if (array[end] > array[start])
                return end;
            return start;
        }
        int middle = (end-start)/2 + start;
        if (array[middle] > array[middle-1] && (array[middle] < array[middle+1])){
            return findMaxBitonicArray(array, middle + 1, end)   ;
        }
        else if (array[middle] < array[middle-1] && (array[middle] > array[middle+1])){
            return findMaxBitonicArray(array, start, middle - 1)   ;
        }
        else {
            return middle   ;
        }
    }

    public static boolean binarySearchBitonicArray(int[] array, int value){
        /* binary search in bitonic array by finding the max in 3logn time complexity */
        int maxIndex = findMaxBitonicArray(array);
        return (binarySearch(array, 0, maxIndex, value) || binarySearchDesc(array, maxIndex+1, array.length-1, value));
    }

    public static boolean binarySearchBitonicArrayOptimal(int[] array, int value) {
        /* binary search in bitonic array in  2logn time complexity */
        return binarySearchBitonicArrayOptimal(array, 0, array.length-1, value);
    }

    public static boolean binarySearchBitonicArrayOptimal(int[] array, int start_index, int end_index, int value){
        //assumption: arrays sorted from ascending to descending order and then descending to ascending

        int middle = (end_index - start_index)/2 + start_index;
        int middle_val = array[middle];
        if (middle_val == value){
            return true;
        }
        if (start_index >= end_index){
            return false; //base case
        }

        int middle_next = array[middle+1];

        if (middle_val < value && middle_val < middle_next ){
            // middle is part of increasing sequence, so max is in right subarray
            // search in right subarray
            return binarySearchBitonicArrayOptimal(array, middle + 1, end_index, value);
        }
        else if (middle_val <value && middle_val > middle_next ){
            // middle is part of decreasing sequence, so max in in left subarray
            //search in left subarray
            return binarySearchBitonicArrayOptimal(array, start_index, middle, value);
        }
        else {
            //search both subarrays
            return binarySearchBitonicArrayOptimal(array, start_index, middle, value) ||
                    binarySearchBitonicArrayOptimal(array, middle + 1, end_index, value);
        }
    }

    public static void main(String[] args){
        int[] array = new int[]{3 ,13, 17 ,19, 25, 8 ,4 ,1};
        System.out.println(BinarySearchUtil.findMaxBitonicArray(array));
    }

}

public class BinarySearchUtilTest{
    @Test
    public void testThreeSum(){
        /*
         compute threeSum
         */
        int[] array = new int[]{30,-40,-20,-10,40,0,10,5};
        assertTrue(BinarySearchUtil.computeThreeSum(array)== 4);

    }

    @Test
    public void testBinarySearchBitonicArray(){
        /*
         test binary search bitonic array
         */
        int[] array = new int[]{3 ,13, 17 ,19, 25, 8 ,4 ,1};
        assertTrue(BinarySearchUtil.binarySearchBitonicArray(array, 8));
        assertTrue(BinarySearchUtil.binarySearchBitonicArray(array, 4));
        assertTrue(BinarySearchUtil.binarySearchBitonicArray(array, 13));
        assertTrue(BinarySearchUtil.binarySearchBitonicArray(array, 3));
        assertFalse(BinarySearchUtil.binarySearchBitonicArray(array, 5));

    }

    @Test
    public void testBinarySearchBitonicArrayOptimal(){
        /*
         test binary search bitonic array
         */
        int[] array = new int[]{3 ,13, 17 ,19, 25, 8 ,4 ,1};
        assertTrue(BinarySearchUtil.binarySearchBitonicArrayOptimal(array, 8));
        assertTrue(BinarySearchUtil.binarySearchBitonicArrayOptimal(array, 4));
        assertTrue(BinarySearchUtil.binarySearchBitonicArrayOptimal(array, 13));
        assertTrue(BinarySearchUtil.binarySearchBitonicArrayOptimal(array, 3));
        assertFalse(BinarySearchUtil.binarySearchBitonicArrayOptimal(array, 5));
    }
}
