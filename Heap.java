public class Heap {
    int heap_size;
    HeapNode heap_array[];
    
    public Heap(int K){
        heap_size = K;
        heap_array = new HeapNode[heap_size+1];
        int i = 1;
        while(i <= heap_size){
            heap_array[i] = new HeapNode(i);
            i++;
        }
    }
    public HeapNode findMin(){
        return heap_array[1];//new customer aake choti line me lgegA,to ye function choti line find krega
    }

    public void buildHeap(){
        int i = heap_size/2;
        while(i > 0){
            percolateDown(i, heap_array[i]);
            i--;
        }
    }

    public void percolateUp(int index){
        if(index==1 || index==0){return;}
        
        HeapNode heap_node1 = heap_array[index];
        HeapNode heap_node2 = heap_array[index/2];
        
        if(fun(heap_node1, heap_node2)){
            // HeapNode temp_heap_node = heap_node1;
            heap_array[index] = heap_node2;
            heap_array[index/2] = heap_node1;
            percolateUp(index/2);
        }else{return;}
    }

    public boolean fun(HeapNode heap_node1, HeapNode heap_node2){
        if(heap_node1.waiting_queue.size() < heap_node2.waiting_queue.size() || (heap_node1.waiting_queue.size() == heap_node2.waiting_queue.size() && heap_node1.counter_num < heap_node2.counter_num)){
            return true;
        }
        else{
            return false;
        }
    }

    public void percolateDown(int index, HeapNode x){
        if(2*index > heap_size){
            heap_array[index] = x;
        }

        else if(2*index == heap_size){
            if(fun(heap_array[2*index], x)){
                heap_array[index] = heap_array[2*index];
                heap_array[2*index] = x;
            }else{heap_array[index] = x;}
        }
        
        else{
            int j = 0;
            if(fun(heap_array[2*index], heap_array[2*index + 1])){
                j = 2*index;
            }else{j = 2*index + 1;}
            
            if(fun(heap_array[j], x)){
                heap_array[index] = heap_array[j];
                percolateDown(j, x);
            }
            else{
                heap_array[index] = x;
            }
        }
    }
}
