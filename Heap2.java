public class Heap2 {
    Growable_Array gArray = new Growable_Array();
//heap2 growable(dynamic) array ki heap h,ND geowable array ke element queue node h
    public QueueNode findMin(){
        return gArray.array[1];
    }

    public int heapSize(){
        return gArray.top;
    }

    public boolean fun(QueueNode queue_node1, QueueNode queue_node2){
        if(queue_node1.orderT < queue_node2.orderT || (queue_node1.orderT == queue_node2.orderT && queue_node1.counter_num > queue_node2.counter_num)){
            return true;
        }
        else{
            return false;
        }
    }

    public void percolateUp(int index){
        if(index == 1 || index == 0){return;}

        QueueNode queue_node1 = gArray.array[index];
        QueueNode queue_node2 = gArray.array[index/2];

        if(fun(queue_node1, queue_node2)){
            gArray.array[index] = queue_node2;
            gArray.array[index/2] = queue_node1;
            percolateUp(index/2);
        }
        else{return;}
    }

    public void percolateDown(int index, QueueNode x){
        if(2*index > heapSize()){
            gArray.array[index] = x;
        }

        else if(2*index == heapSize()){
            if(fun(gArray.array[2*index], x)){
                gArray.array[index] = gArray.array[2*index];
                gArray.array[2*index] = x;
            }else{gArray.array[index] = x;}
        }

        else{
            int j = 0;
            if(fun(gArray.array[2*index], gArray.array[2*index + 1])){
                j = 2*index;
            }else{j = 2*index + 1;}

            if(fun(gArray.array[j], x)){
                gArray.array[index] = gArray.array[j];
                percolateDown(j, x);
            }else{gArray.array[index] = x;}
        }
    }

    public void deleteMin(){
        QueueNode last_queue_node = gArray.array[gArray.top];
        gArray.array[gArray.top] = null;
        gArray.top = gArray.top - 1;

        gArray.array[1] = last_queue_node;
        percolateDown(1, gArray.array[1]);
    }
}
