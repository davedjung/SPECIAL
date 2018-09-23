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
        
        System.out.println("Debugging infixToPreficConverter...");
        System.out.println(infixToPrefixConverter(scan.nextLine()));
    }
    
    public static String infixToPrefixConverter(String input){
        String prefix = "";
        String postfix[];
        String infix[];
        int indexPo = -1;
        int indexIn = -1;
        Stack stack;
        
        //Demo
        final int size = 7;
        postfix = new String[size];
        infix = new String[size];
        stack = new Stack(size);
        infix[0] = "(";
        infix[1] = "5";
        infix[2] = "-";
        infix[3] = "2";
        infix[4] = ")";
        infix[5] = "^";
        infix[6] = "2";
                
        
        for (indexIn = 0; indexIn < infix.length; indexIn++){
            if (!isOperator(infix[indexIn])){
                postfix[++indexPo] = infix[indexIn];
            } else if (infix[indexIn].equals("(")) {
                stack.push("(");
            } else if (infix[indexIn].equals(")")){
                while (!stack.lastItem().equals("(")){
                    postfix[++indexPo] = stack.pop();
                }
                stack.pop();
            } else {
                while (stack.lastItem()!=null && priority(infix[indexIn]) > priority(stack.lastItem())){
                    postfix[++indexPo] = stack.pop();
                }
                stack.push(infix[indexIn]);
            }    
        }
        while (stack.lastItem()!=null){
            postfix[++indexPo] = stack.pop();
        }
        for (int j=postfix.length-1; j>-1; j--){
            prefix = prefix + postfix[j];
        }
        
        return prefix;
        
        
        
        
        
        /*
            String output = "";
            String current = "";
            int operatorIndex[];
            int arrayIndex = -1;
            int size = 0;
            int index= 0;
            Stack stack = new Stack(input.length());
            
            for (int i=0; i<input.length(); i++){
                if (isOperator(input.charAt(i))){
                    size ++;
                }
            }
            
            operatorIndex = new int[size+1];
            for (int i=0; i<input.length(); i++){
                if (isOperator(input.charAt(i))){
                    operatorIndex[++arrayIndex] = i;
                }
            }
            operatorIndex[++arrayIndex] = input.length();
            arrayIndex = 0;
            
            while(operatorIndex[arrayIndex]!=input.length()){
                current = input.substring(index, operatorIndex[arrayIndex]);
                if (!isOperator(current)){
                    output = output + current;
                } else {
                    if(current.equals("(")){
                        stack.push("(");
                    } else if (current.equals(")")){
                        while (!stack.lastItem().equals("(")){
                            output = output + stack.pop();
                        }
                        stack.pop();
                    } else {
                        while (priority(current) > priority(stack.lastItem())){
                            output = output + stack.pop();
                        }
                        stack.push(current);
                    }
                            
                }
                index = operatorIndex[arrayIndex++];
            }
            while (!stack.isEmpty()){
                output = output + stack.pop();
            }
            return output;
        */
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
    
    public static boolean isOperator(String input){
        /*
        if (input.equals("+") || input.equals("-") || input.equals("*") || input.equals("/") || input.equals("(") || input.equals(")") || input.equals("^")){
            return true;
        } else {
            return false;
        }
        */
        if (input.charAt(0) >= 48 && input.charAt(0) <= 57){
            return false;
        } else {
            return true;
        }
    }
    
    public static int priority(String input){
        if (input.equals("+") || input.equals("-")){
            return 1;
        } else if (input.equals("*") || input.equals("-")){
            return 2;
        } else if (input.equals("^")){
            return 3;
        } else if (input.equals("(") || input.equals(")")){
            return 4;
        } else {
            //in case of empty input
            return 5;
        }
    }
}
