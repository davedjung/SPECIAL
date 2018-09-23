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
        String infix_reversed[];
        int indexPo = -1;
        int indexIn = -1;
        Stack stack;
        
        //Demo
        final int size = 13;
        postfix = new String[size];
        infix = new String[size];
        infix_reversed = new String[size + 1];
        stack = new Stack(size);
        infix[0] = "4";
        infix[1] = "-";
        infix[2] = "49";
        infix[3] = "^";
        infix[4] = "(";
        infix[5] = "(";
        infix[6] = "4";
        infix[7] = "-";
        infix[8] = "3";
        infix[9] = ")";
        infix[10] = "*";
        infix[11] = "3";
        infix[12] = ")";
        
        //Reversing infix expression for conversion
        for (int i = 0; i < infix.length; i++){
            if (infix[infix.length - 1 - i].equals("(")){
                infix_reversed[i] = ")";
            } else if (infix[infix.length - 1 - i].equals(")")){
                infix_reversed[i] = "(";
            } else {
                infix_reversed[i] = infix[infix.length - 1 - i];
            }
        }
        
        //Step 1: Add ")" to the end of the reversed infix expression
        infix_reversed[infix_reversed.length - 1] = ")";
        
        //Step 2: Push "(" onto the stack
        stack.push("(");
        
        //Step 3: Process each element until the expression is scanned to the end
        for (int j = 0; j < infix_reversed.length; j++){
            if (!isOperator(infix_reversed[j])){
                postfix[++indexPo] = infix_reversed[j];
            } else if (infix_reversed[j].equals("(")){
                stack.push("(");
            } else if (infix_reversed[j].equals(")")){
                while (!stack.lastItem().equals("(")){
                    postfix[++indexPo] = stack.pop();
                }
                stack.pop();
            } else {
                while (priority(infix_reversed[j]) <= priority(stack.lastItem())){
                    postfix[++indexPo] = stack.pop();
                }
                stack.push(infix_reversed[j]);
            }
        }
        
        //Step 4: Pop from the stack until the stack is empty
        while (stack.lastItem()!=null){
            postfix[++indexPo] = stack.pop();
        }
        
        //Step 5: Reverse the postfix expression to obtain prefix expression
        for (int j=postfix.length-1; j>-1; j--){
            if (postfix[j]!=null){
                prefix = prefix + postfix[j];
            }
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
        //Comprehensive version for expressions with variables (characters)
        /*
        if (input.equals("+") || input.equals("-") || input.equals("*") || input.equals("/") || input.equals("(") || input.equals(")") || input.equals("^")){
            return true;
        } else {
            return false;
        }
        */
        
        //Simplified version for expressions without variables (characters)
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
            return 0;
        } else {
            //in case of empty input
            return 5;
        }
    }
}
