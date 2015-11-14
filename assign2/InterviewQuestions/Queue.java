package assign2.InterviewQuestions;

/**
 * Created by VGN on 11/13/15.
 */
public interface Queue<Item> {
    public void enqueue(Item item);

    public Item dequeue();

    public Item peek();

    public boolean isEmpty();

    public int size();
}
