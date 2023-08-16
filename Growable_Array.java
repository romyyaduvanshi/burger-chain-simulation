public class Growable_Array {
    int top = 0;
    QueueNode array[] = new QueueNode[2];

    public void push(QueueNode queue_node){
        if(size() == array.length){
            QueueNode new_array[] = new QueueNode[2*size()-1];
            int i = 1;
            while(i < size()){
                new_array[i] = array[i];
                i++;
            }
            array = new_array;
        }
        array[top + 1] = queue_node;
        top++;
    }

    public int size(){
        return top + 1;
    }
}
//growable means dynamic array
