
class HeapNode {
    int counter_num;
    Queue waiting_queue = new Queue();

    public HeapNode() {
    }

    public HeapNode(int counter_num) {
        this.counter_num = counter_num;
    }
}

class QueueNode {
    int id;
    int arrivalT;
    int orderT = 0;
    int gEnterT = 0;
    int exitT = 0;
    int numb;
    int counter_num;
    QueueNode next;

    public QueueNode() {
    }

    public QueueNode(int id, int numb) {
        this.id = id;
        this.numb = numb;
    }

    public QueueNode(int id, int t, int numb) {
        this.id = id;
        this.arrivalT = t;
        this.numb = numb;
    }
}

public class MMBurgers implements MMBurgersInterface {

    int K = 0;
    int M = 0;
    int currentM = 0;//currently kitne burger bn rhe h tave pr

    int prev_time = 0;

    Heap heap;//each heap contains a line
    Heap2 heap2 = new Heap2();

    Queue griddle_queue = new Queue();
    Growable_Array Customers = new Growable_Array();

    public boolean isEmpty() {
        // your implementation
        int i = 1;
        while(i < heap.heap_size){
            if(heap.heap_array[i].waiting_queue.size!=0){
                //if any customer in particular line than return false
                return false;
            }
            i++;
        }
        if (griddle_queue.size() == 0 && heap2.gArray.top == 0) {
            //indicates no customer
            return true;
        }
        return false;
    }

    public void setK(int k) throws IllegalNumberException {
        // your implementation
        if(k <= 0 || K != 0){
            throw new IllegalNumberException("");
        }
        heap = new Heap(k);
        this.K = k;
    }

    public void setM(int m) throws IllegalNumberException {
        // your implementation
        if(m <= 0 || M != 0){
            throw new IllegalNumberException("");
        }
        this.M = m;
    }

    public int max(int a, int b) {
        if (a > b) {
            return a;
        } else {
            return b;
        }
    }

    public int min(int a, int b) {
        if (a < b) {
            return a;
        } else {
            return b;
        }
    }

    public void advanceTime(int t) throws IllegalNumberException {
        // your implementation
        if (t < 0) {
            throw new IllegalNumberException("");
        }
        prev_time = t;
        int i = 1;
        while (i <= heap.heap_size) {
            int global_time = 0;
            HeapNode temp_heap_node = heap.heap_array[i];
            QueueNode temp_queue_node = temp_heap_node.waiting_queue.head;

            while (temp_queue_node != null) {

                global_time = max(global_time, temp_queue_node.arrivalT) + temp_heap_node.counter_num;

                if (global_time > t) {
                    break;
                }

                temp_queue_node.orderT = global_time;

                heap2.gArray.push(temp_queue_node);

                heap2.percolateUp(heap2.gArray.top);

                temp_queue_node = temp_queue_node.next;

                temp_heap_node.waiting_queue.dequeue();

            }

            i++;
        }

        heap.buildHeap();

        int global_time = 0;

        while (heap2.heapSize() > 0) {
            QueueNode queue_node = heap2.findMin();

            if (griddle_queue.size() != 0 && (griddle_queue.head.gEnterT + 10 < queue_node.orderT || currentM == M)) {
                global_time = griddle_queue.head.gEnterT + 10;

                if (global_time > t){
                    break;
                }
                currentM -= griddle_queue.head.numb;
                griddle_queue.head.exitT = global_time + 1;
                griddle_queue.dequeue();
            }

            else {
                global_time = max(queue_node.orderT, global_time);
                if (global_time > t){
                    break;
                }
                if (queue_node.numb + currentM <= M) {

                    currentM += queue_node.numb;

                    queue_node.gEnterT = global_time;
                    griddle_queue.enqueue(queue_node);
                    
                    heap2.deleteMin();
                }

                else {
                    QueueNode temp_queue_node = new QueueNode(queue_node.id, M - currentM);

                    queue_node.numb = queue_node.numb - (M - currentM);
                    currentM = M;

                    temp_queue_node.gEnterT = global_time;
                    griddle_queue.enqueue(temp_queue_node);
                    

                }
                if(queue_node.gEnterT == 0){
                    queue_node.gEnterT = global_time;
                }
            }
        }
        while(heap2.heapSize() ==0 && griddle_queue.size() != 0){
            global_time = griddle_queue.head.gEnterT + 10;

            if (global_time > t){
                break;
            }
            currentM -= griddle_queue.head.numb;
            griddle_queue.head.exitT = global_time + 1;
            griddle_queue.dequeue();
        }

    }

    public void arriveCustomer(int id, int t, int numb) throws IllegalNumberException {
        // your implementation
        if (numb <= 0 || t < prev_time || Customers.top + 1 != id) {
            throw new IllegalNumberException("");
        }
        advanceTime(t);
        prev_time = t;

        HeapNode min_heap_node = heap.findMin();

        QueueNode queue_node = new QueueNode(id, t, numb);
        min_heap_node.waiting_queue.enqueue(queue_node);

        heap.percolateDown(1, min_heap_node);

        Customers.push(queue_node);
        Customers.array[Customers.top].counter_num = min_heap_node.counter_num;

    }

    public int customerState(int id, int t) throws IllegalNumberException {
        // your implementation
        if (t < prev_time) {
            throw new IllegalNumberException("");
        }
        prev_time = t;

        advanceTime(t);

        if (Customers.top < id) {
            return 0;
        } else {
            QueueNode queue_node = Customers.array[id];
            if (queue_node.orderT == 0) {
                return queue_node.counter_num;
            } else if (queue_node.exitT == 0) {
                return K + 1;
            } else {
                return K + 2;
            }
        }
    }

    public int griddleState(int t) throws IllegalNumberException {
        // your implementation
        if (t < prev_time) {
            throw new IllegalNumberException("");
        }
        prev_time = t;

        advanceTime(t);
        return currentM;
    }

    public int griddleWait(int t) throws IllegalNumberException {
        // your implementation
        if (t < prev_time) {
            throw new IllegalNumberException("");
        }
        prev_time = t;

        advanceTime(t);
        int i = 1;
        int ans = 0;
        while (i <= heap2.heapSize()) {
            ans += heap2.gArray.array[i].numb;
            i++;
        }
        return ans;
    }

    public int customerWaitTime(int id) throws IllegalNumberException {
        // your implementation
        if (Customers.top < id) {
            throw new IllegalNumberException("");
        }
        QueueNode queue_node = Customers.array[id];
        int ans = queue_node.exitT - queue_node.arrivalT;
        return ans;
    }

    public float avgWaitTime() {
        // your implementation
        int i = 1;
        int total_customer = Customers.top;
        float total_time = 0.0f;
        while (i <= total_customer) {
            QueueNode queue_node = Customers.array[i];
            total_time += queue_node.exitT - queue_node.arrivalT;
            i++;
        }
        float ans = total_time / total_customer;
        return ans;
    }
}