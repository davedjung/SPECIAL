package mathematicalexpressionprocessor;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        
        Scanner scan = new Scanner(System.in);
        
        System.out.println("Mathematical Expression Processor version 0.1");
        System.out.println();
        
        System.out.println("Debugging Stack class...");
        Stack stack = new Stack(100);
        System.out.println("Testing push...");
        stack.push("This");
        stack.push("is");
        stack.push("a");
        stack.push("testing");
        stack.push("procedure");
        System.out.print("Testing toString : " + stack.toString());
        System.out.println(" (Desired output : This is a testing procedure)");
        System.out.print("Testing pop : " + stack.pop());
        System.out.println(" (Desired output : procedure)");
        System.out.print("Tesing lastItem : " + stack.lastItem());
        System.out.println(" (Desired output : testing)");
        System.out.print("Testing isEmpty : " + stack.isEmpty());
        System.out.println(" (Desired output : false)");
        System.out.print("Tesing currentSize : " + stack.currentSize());
        System.out.println(" (Desired output : 4)");
        System.out.println("Testing boundary condition... ");
        while (!stack.isEmpty()){
            stack.pop();
        }
        System.out.print("Current stack status : " + stack.toString());
        System.out.println(" (Desired output : null)");
        System.out.print("Testing isEmpty : " + stack.isEmpty());
        System.out.println(" (Desired output : true)");
        System.out.println("Enter testing input : ");
        String testingInput = scan.nextLine();
        String temp = testingInput + " ";
        stack = new Stack(temp.length());
        System.out.println("Checking input...");
        System.out.println("Parsing by space...");
        //Remove all spaces at the beginning of the String
        while(temp.indexOf(" ")==0){
            temp = temp.substring(1);
        }
        //Push all substrings ending with space
        outerloop:
        while (temp.contains(" ")){
            //Remove repeated spaces
            while (temp.charAt(0)==' '){
                if (temp.length()>1){
                   temp = temp.substring(1);
                } else {
                    temp = "";  
                    break outerloop;
                }
            }   
            stack.push(temp.substring(0, temp.indexOf(" ")));
            temp = temp.substring(temp.indexOf(" ")+1);
        }
        if (!temp.equals("")){
            stack.push(temp);
        }
        System.out.println("Stack content : " + stack.toString());
        System.out.println("Stack size : " + stack.currentSize());
        System.out.println("Stack class debugging complete.");
        System.out.println();
        
        System.out.println(isOperator('3'));
        System.out.println(isOperator('+'));
    }
    
    public static String infixToPrefixConverter(String input){
            String output = "";
            String current = "";
            while(!input.equals("")){
                current = input.substring(0,1);
                
            }
            return output;
    }
    
    public static boolean isOperator(char input){
        /*
        if (input.equals("+") || input.equals("-") || input.equals("*") || input.equals("/") || input.equals("(") || input.equals(")") || input.equals("^")){
            return true;
        } else {
            return false;
        }
        */
        if (input >= 48 && input <= 57){
            return false;
        } else {
            return true;
        }
    }
    
}
