package assign3.InterviewQuestions;
import java.util.*;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;


/**
 * Created by VGN on 11/16/15.
 */
class Sort {
    /*
    Given an array with n objects colored red, white or blue, sort them so that objects of the same color are adjacent, with the colors in the order red, white and blue.
    Here, we will use the integers 0, 1, and 2 to represent the color red, white, and blue respectively.
    Implemented using insertion sort
     */
    public void sortColors(int[] nums) {
        for (int i=0; i < nums.length; i++){
                for (int j=i; j > 0; j--){
                    if (nums[j] < nums[j-1]){
                        //swap entries
                        int temp = nums[j];
                        nums[j] = nums[j-1];
                        nums[j-1] = temp;
                    }
                    else
                        break;
            }
        }

    }
}

/**
 * Definition for an interval.
 * */
class Interval {
     int start;
      int end;
    private Object o;

    Interval() { start = 0; end = 0; }
      Interval(int s, int e) { start = s; end = e; }

    public String toString(){
        return "Start: "+ start + "End: " + end;
    }

    @Override
    public boolean equals(Object o){

        if (this.o == o)
            return true;
        if (this.getClass() != o.getClass())
            return false;
        Interval that = (Interval)o ;
        if (this.start == that.start && this.end == that.end )
            return true;
        return false;
    }
  }

class IntervalComparator implements Comparator<Interval>{

    @Override
    public int compare(Interval i1, Interval i2) {
        if (i1.start > i2.start)
            return 1;
        if (i1.start == i2.start && i1.end > i2.end){
            return 1;
        }
        if (i1.start == i2.start && i1.end == i2.end){
            return 0;
        }
        return -1;
    }
}

class Solution {
    /**
    Given a collection of intervals, merge all overlapping intervals.

    For example,
    Given [1,3],[2,6],[8,10],[15,18],
            return [1,6],[8,10],[15,18].
     **/
    public List<Interval> merge(List<Interval> intervals) {
        if (intervals.size() <=1){
            return intervals;
        }
        Interval[] intervalsArray = new Interval[intervals.size()];
        intervalsArray = intervals.toArray(intervalsArray);
        Arrays.sort(intervalsArray, new IntervalComparator());
        ArrayList<Interval> mergedList = new ArrayList<Interval>();
        int index = 0;
        Interval interval1 = intervalsArray[index++];
        Interval interval2 = null;
        while (index < intervalsArray.length){
            interval2 = intervalsArray[index];
            if (isOverlapInterval(interval1, interval2)){
                int max = -1;
                if (interval1.end < interval2.end )
                    max = interval2.end;
                else
                    max = interval1.end;
                interval1 = new Interval(interval1.start, max);
            }
            else{
                mergedList.add(interval1);
                interval1 = interval2;
            }
            index ++;

        }
        mergedList.add(interval1);
        return mergedList;
    }

    private boolean isOverlapInterval(Interval i1, Interval i2){
        if ((i1.end >= i2.start) || (i1.start == i2.start && i1.end < i2.end)){
           return true;
        }
        return false;
    }

    /*
    Given two sorted integer arrays nums1 and nums2, merge nums2 into nums1 as one sorted array.
    Note:
    You may assume that nums1 has enough space (size that is greater or equal to m + n) to hold additional elements from nums2. The number of elements initialized in nums1 and nums2 are m and n respectively.
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        assert (nums1.length >= m+n);
        if (n == 0)
            return ;
        //copy nums1 into auxillary array
        int[] aux = new int[m];
        for (int i =0; i< m; i++){
            aux[i] = nums1[i];
        }
        int i = 0;// pointer to aux
        int j =0;// pointer to nums2
        //compare aux with nums1
        for (int k=0; k< nums1.length; k++){
            if (i >= m){
                nums1[k] = nums2[j++];
            }
            else if (j >= n){
                nums1[k] = aux[i++];
            }
            else if (aux[i] > nums2[j]){
                nums1[k] = nums2[j++];
            }
            else{
                nums1[k] = aux[i++];
            }
        }

    }
}




public class SortTest{

    @Test
    public void test1_mergeInterval(){
        List<Interval> intervalList = new ArrayList<Interval>();
        intervalList.add(new Interval(1,3));
        intervalList.add(new Interval(15,18));
        intervalList.add(new Interval(8, 10));
        intervalList.add(new Interval(2,6));
        List<Interval> solution = new Solution().merge(intervalList);
        Assert.assertTrue(solution.size() ==3 );
        Assert.assertTrue(solution.get(0).equals(new Interval(1,6)));
        Assert.assertTrue(solution.get(1).equals(new Interval(8,10)));
        Assert.assertTrue(solution.get(2).equals(new Interval(15,18)));

    }

    @Test
    public void test2_mergeInterval(){
        List<Interval> intervalList = new ArrayList<Interval>();
        intervalList.add(new Interval(1,4));
        intervalList.add(new Interval(1, 4));
        List<Interval> solution = new Solution().merge(intervalList);
        Assert.assertTrue(solution.size() ==1 );
        Assert.assertTrue(solution.get(0).start == 1);
        Assert.assertTrue(solution.get(0).end == 4);
    }

    @Test
    public void test3_mergeInterval(){
        List<Interval> intervalList = new ArrayList<Interval>();
        intervalList.add(new Interval(1,4));
        intervalList.add(new Interval(2, 3));
        List<Interval> solution = new Solution().merge(intervalList);
        Assert.assertTrue(solution.size() ==1 );
        Assert.assertTrue(solution.get(0).start == 1);
        Assert.assertTrue(solution.get(0).end == 4);
    }

    @Test
    public void test4_mergeSortedArrays(){
        int[] nums1 = new int[] {0};
        int[] nums2 = new int[] {1};
        int m=0;
        int n=1;
        new Solution().merge(nums1, m, nums2, n);
        Assert.assertTrue(nums1[0] == 1);

    }

}
