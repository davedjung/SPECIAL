/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mathematicalexpressionprocessor;

/**
 *
 * @author daved
 */

public class BinaryTree {
    private Node root;
    
    public BinaryTree(){
        this.root = null;
    }
    
    public BinaryTree(Node root){
        this.root = root;
    }
    
    public void insert(Node node){
        Node temp = this.root;
        while (true){
            if (temp.getValue() >= node.getValue()){
                if (temp.getLeft() == null){
                    temp.insertLeft(node);
                    break;
                } else {
                    temp = temp.getLeft();
                }
            } else {
                if (temp.getRight() == null){
                    temp.insertRight(node);
                    break;
                } else {
                    temp = temp.getRight();
                }
            }
        }
    }
    
    public void print(){
        this.root.print();
    }
    
    public void setRoot(Node root) { this.root = root; }
    public Node getRoot() { return this.root; }
    
}