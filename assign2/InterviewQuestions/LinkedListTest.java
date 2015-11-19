package assign2.InterviewQuestions;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by VGN on 11/13/15.
 */

class ListNode {
     int val;
     ListNode next;
      ListNode(int x) { val = x; }
  }

/**
 * Definition for singly-linked list with a random pointer.
 *
 */

 class RandomListNode {
     int label;
      RandomListNode next, random;
      RandomListNode(int x) { this.label = x; }
  }



class Solution{

    /*
        Function to delete a node (except the tail) in a singly linked list, given only access to that node.
        Supposed the linked list is 1 -> 2 -> 3 -> 4 and you are given the third node with value 3, the linked list should become 1 -> 2 -> 4 after calling your function.
    */
    public void deleteNode(ListNode node) {
        if (node.next == null){
            node = null;
        }
        int value = node.next.val;
        //edit the node value to have the next node's value
        node.val = value;
        //remove the next node
        node.next = node.next.next;
    }

    /*
    Remove all elements from a linked list of integers that have value val.

            Example
    Given: 1 --> 2 --> 6 --> 3 --> 4 --> 5 --> 6, val = 6
    Return: 1 --> 2 --> 3 --> 4 --> 5
    */
    public ListNode removeElements(ListNode head, int val) {
        ListNode start = head;
        ListNode prev = null;
        while (start != null){
            if (start.val == val){
                if (head == start) {
                    //first node of list
                    head = start.next;
                    start = start.next;
                }
                else{
                    //regular case when node is middle of list
                    prev.next = start.next;
                    start = start.next;
                }
            }
            else{
                prev = start;
                start = start.next;
            }
        }
        return head;
    }

    public void printlist(ListNode head){
        while (head != null){
            System.out.print(head.val);
            if (head.next != null){
                System.out.print("->");
            }
            head = head.next;
        }
    }

    public void printlist(RandomListNode head){
        while (head != null){
            System.out.print("MEMORY LOCATION: ");
            System.out.println(head);

            System.out.print("LABEL: ");
            System.out.println(head.label);
            System.out.print("RANDOM MEMORY LOCATION: ");
            if (head.random != null) {
                System.out.print(head.random);
                System.out.println(" VALUE:" +head.random.label);
            }
            else
                System.out.println("NULL");
            if (head.next != null){
                System.out.println("------");
                System.out.println("NEXT");
                System.out.println("-----");
            }
            head = head.next;
        }
    }

    /*
    Given a linked list, determine if it has a cycle in it.
     */
    public boolean hasCycle(ListNode head) {
        if (this.getCycleNode(head) != null) {
            return true;
        }
        return false;
    }

    /*
    Given link list, return the intersection node when cycle exists else return null
     */
    private ListNode getCycleNode(ListNode head){
        if (head == null)
            return head;
        ListNode slow = head;
        ListNode fast = head;
        while (true){
            slow = slow.next;
            if (slow == null)
                break;
            if (fast.next == null)
                break;
            fast = fast.next.next;
            if (fast == null)
                break;
            if (slow == fast)
                return slow;
        }
        return null;
    }

    /*
    Given a linked list, return the node where the cycle begins. If there is no cycle, return null.

    Note: Do not modify the linked list.
     */
    public ListNode detectCycle(ListNode head) {
        ListNode node = this.getCycleNode(head);
        if (node == null) {
            //no cycle exists
            return node;
        }
        //now move slow pointer to beginning of list and move slow and fast at same rate,
        //node at which the pointers intersect is starting point of cycle
        ListNode slow = head;
        ListNode fast = node;
        while (slow != fast){
            //break loop when two pointers intersect
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    /*reverse a single link list*/
    public ListNode reverseList(ListNode head) {
        if (head == null)
            return head;
        ListNode forward = head; // pointer to forward list
        ListNode reverse = null; // pointer to reverse list
        while (forward != null){
            ListNode oldReverse = reverse;
            reverse = forward;
            forward = forward.next;
            reverse.next = oldReverse;
        }
        return reverse;

    }

    public boolean testDeepCopyRandomList(RandomListNode head, RandomListNode copy){
        if (head == null && copy != null){
            return false;
        }
        if (copy== null && head != null){
            return false;
        }
        while (head != null && copy != null){
            if (head == null && copy != null){
                return false;
            }
            else if (head != null && copy == null){
                return false;
            }
            else if (head.label != copy.label){
                return false;
            }
            else if (head == copy){
                return false;
            }
            else if (head.random == null && copy.random != null){
                return false;
            }
            else if (copy.random == null && head.random != null ){
                return false;
            }
            else if (head.random != null && copy.random != null ){
                if (head.random.label != copy.random.label){
                    System.out.println("FAils here");
                    return false;
                }
                if (head.random == copy.random){
                    return false;
                }
            }

            head = head.next;
            copy = copy.next;
        }
        return true;
    }

    /*
    A linked list is given such that each node contains an additional random pointer which could point to any node in the list or null.

    Return a deep copy of the list.
     */
    public RandomListNode copyRandomList(RandomListNode head) {
         if (head == null){
             return head;
         }
        RandomListNode original = head;
        RandomListNode next = null;

        while (original != null){
            next = original.next;
            //insert new node in between each node in original list
            RandomListNode newNode = new RandomListNode(original.label);
            original.next = newNode;
            newNode.next = next;
            original = next;
        }

        original = head;
        RandomListNode copy = original.next;
        RandomListNode headcopy = copy;
        while (original != null) {
            //setting random pointers for copy list
            copy = original.next;
            RandomListNode oldCopyNext = copy.next;
            if (original.random != null)
                copy.random = original.random.next;
            else
                copy.random = null;
            original = oldCopyNext;
        }
        original = head;
        while (original != null) {
            //restoring next pointers in original list and copy list
            copy = original.next;
            RandomListNode oldCopyNext = copy.next;
            original.next = oldCopyNext;
            if (oldCopyNext != null)
                copy.next = oldCopyNext.next;
            original = oldCopyNext;
        }
        return headcopy;
    }

    /*Sort a linked list using insertion sort.*/
    public ListNode insertionSortList(ListNode head) {
        ListNode sorted = null;
        while (head != null){
            if (sorted == null){
                // for first listnode
                sorted = head;
                head = head.next;
                sorted.next = null;
            }
            else if (head.val <= sorted.val){
                //easy case insert in front of list
                ListNode oldSorted = sorted;
                sorted = head;
                head = head.next;
                sorted.next = oldSorted;
            }
            else{
                //iterate through sorted list to find location to insert
                ListNode current = sorted;
                ListNode prev = null;
                while (current.val < head.val){
                    prev = current;
                    current = current.next;
                    if (current == null)
                        break;
                }
                ListNode copyhead = head.next;
                //insert node between prev and current
                ListNode oldcurrent = current;
                prev.next = head;
                head.next = current;
                head = copyhead;
            }

        }
        return sorted;

    }
}

public class LinkedListTest {
    @Test
    public void test1_removeElements() {
        ListNode node = new ListNode(1);
        assertTrue(new Solution().removeElements(node, 1) == null);
    }

    @Test
    public void test2_removeElements() {
        ListNode node = new ListNode(1);
        node.next = new ListNode(1);
        assertTrue(new Solution().removeElements(node, 1) == null);
    }

    @Test
    public void test3_removeElements() {
        ListNode node = new ListNode(1);
        node.next = new ListNode(2);
        assertTrue(new Solution().removeElements(node, 2).val == 1);
    }

    @Test
    public void test1_hasCycle() {
        assertFalse(new Solution().hasCycle(null));
    }

    @Test
    public void test1_randomPointer(){
        //{-1,8,7,-3,4,4,-3,#,#,-1}
        RandomListNode node1 = new RandomListNode(-1);
        RandomListNode node2 = new RandomListNode(8);
        RandomListNode node3 = new RandomListNode(7);
        RandomListNode node4 = new RandomListNode(-3);
        RandomListNode node5 = new RandomListNode(4);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node1.random = node5;
        node2.random = node4;
        node5.random = node1;
        RandomListNode nodeCopy = new Solution().copyRandomList(node1);
        assertTrue(new Solution().testDeepCopyRandomList(node1, nodeCopy));
    }

}
