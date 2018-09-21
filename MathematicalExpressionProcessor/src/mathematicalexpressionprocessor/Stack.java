
package mathematicalexpressionprocessor;

public class Stack {
    String[] content;
    int index;
    int size;
    final int defaultSize = 1000;
            
    public Stack(){
        size = defaultSize;
        content = new String[size];
        index = -1;
    }
    
    public Stack(int size){
        this.size = size;
        content = new String[size];
        index = -1;
    }
    
    public void push(String input){
        content[++index] = input;
    }
    
    public String pop(){
        if (index!=-1){
            return content[index--];
        } else {
            return null;
        }
    }
    
    public String lastItem(){
        if (index!=-1){
            return content[index];
        } else {
            return null;
        }
    }
    
    public boolean isEmpty(){
        if (index==-1){
            return true;
        } else {
            return false;
        }
    }
    
    public int currentSize(){
        return index + 1;
    }
    
    public String toString(){
        String output = "";
        if (index==-1){
            return null;
        } else {
            for (int i = 0; i<=index; i++){
                output = output + content[i] + " ";
            }
        }
        return output;
    }
    
}
