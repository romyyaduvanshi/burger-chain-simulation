public class Queue {
    int size;
    QueueNode head;
    QueueNode tail;
    
    public void enqueue(QueueNode queue_node){
        if(head == null){
            head = queue_node;
            tail = head;
        }
        else{
            QueueNode temp_queue_node = queue_node;
            tail.next = temp_queue_node;
            tail = tail.next;
        }
        size++;
    }

    public QueueNode dequeue(){
        if(head == null){
            return null;
        }
        else{
            QueueNode temp_queue_node = head;
            if(head == tail){
                head = tail = null;
            }
            else{
                head = head.next;
            }
            size--;
            return temp_queue_node;
        }
    }

    public int size(){
        return size;
    }
}
