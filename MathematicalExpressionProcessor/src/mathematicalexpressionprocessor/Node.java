package mathematicalexpressionprocessor;

public class Node {
    
    private Node left;
    private Node right;
    private int value;
    
    public Node(){
        this.left = null;
        this.right = null;
        this.value = -1;
    }
    
    public Node(int value){
        this.value = value;
    }
    
    public void insertLeft(Node node){
        this.left = node;
    }
    
    public void insertRight(Node node){
        this.right = node;
    }
    
    public void setValue(int value) { this.value = value;}
    public int getValue() { return value; }
    public Node getLeft() { return left; }
    public Node getRight() { return right; }
    
    public boolean hasLeft() {
        if (this.left == null){
            return false;
        } else {
            return true;
        }
    }
    
    public boolean hasRight() {
        if (this.right == null){
            return false;
        } else {
            return true;
        }
    }
    
    public void deleteLeft() { this.left = null; }
    public void deleteRight() { this.right = null; }
    
    public void print(){
        if (this.left != null){
            this.left.print();
        }
        System.out.print(this.value + " ");
        if (this.right != null){
            this.right.print();
        }
    }
}