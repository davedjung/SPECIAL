package mathematicalexpressionprocessor;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        
        Scanner scan = new Scanner(System.in);
        
        System.out.println("Mathematical Expression Processor version 0.1");
        System.out.println();
        
        //Testing procedure for Stack class
        /*
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
        System.out.println("Parsing by space...");
        //Remove all spaces at the beginning of the String
        while(temp.indexOf(" ")==0){
            temp = temp.substring(1);
        }
        //Push all substrings ending with space into the stack
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
        */
        
        System.out.println("Enter infix expression : ");
        String rawInput = scan.nextLine();
        String[] parsedInput = expressionParser(rawInput);
        String[] prefixExpression = infixToPrefixConverter(parsedInput);
        double result = evaluate(prefixExpression);
        System.out.println("Result is : " + result);
        
        BinaryTree tree = new BinaryTree();
        convertToTree(prefixExpression, tree);
        result = traverse(tree);
        System.out.println("Result is : " + result);
    }
    
    public static String[] expressionParser(String rawInput){
        String infix[];
        int size = 0;
        boolean prev_isOperator = true;
        
        //Determine the size of the expression (# of operators and operands)
        for (int i = 0; i < rawInput.length(); i++){
            if (isOperator(rawInput.charAt(i))){
                if (!prev_isOperator){
                    size += 2;
                } else {
                    size += 1;                    
                }
                prev_isOperator = true;
            } else {
                if (prev_isOperator){
                    prev_isOperator = false;
                }
            }
        }
        if (!prev_isOperator){
            size += 1;
        }
        
        //Initialize infix array according to the size
        infix = new String[size];
        int indexInfix = -1;
        int lastOperatorIndex = -1;
        prev_isOperator = true;
        
        //parse the expression
        for (int j = 0; j < rawInput.length(); j++){
            if (isOperator(rawInput.charAt(j))){
                if (!prev_isOperator){
                    infix[++indexInfix] = rawInput.substring(lastOperatorIndex + 1, j);
                }
                infix[++indexInfix] = rawInput.substring(j, j+1);
                prev_isOperator = true;
                lastOperatorIndex = j;
            } else {
                if (prev_isOperator){
                    prev_isOperator = false;
                }
            }
        }
        if (!prev_isOperator){
            infix[++indexInfix] = rawInput.substring(lastOperatorIndex + 1);
        }
        
        return infix; 
    }
    
    public static String[] infixToPrefixConverter(String[] parsedInput){
        String prefix[];
        String postfix[];
        String infix[] = parsedInput;
        String infix_reversed[];
        int indexPo = -1;
        int size = infix.length;
        int effectiveSize = 0;
        Stack stack;
        
        for (int i = 0; i < infix.length; i++){
            if (!infix[i].equals("(") && !infix[i].equals(")")){
                effectiveSize++;
            }
        }
        
        postfix = new String[effectiveSize];
        prefix = new String[effectiveSize];
        infix_reversed = new String[size + 1];
        stack = new Stack(size);
        
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
                prefix[postfix.length - 1 -  j] = postfix[j];
            }
        }
        
        return prefix;       
    }
    
    public static boolean isOperator(char input){
        //Comprehensive version for expressions with variables (characters)
        if (input == '+' || input == '-' || input == '*' || input == '/' || input == '(' || input == ')' || input == '^'){
            return true;
        } else {
            return false;
        }
        
        
        //Simplified version for expressions without variables (characters)
        /*
        if (input >= 48 && input <= 57){
            return false;
        } else {
            return true;
        }
        */
    }
    
    public static boolean isOperator(String input){
        //Comprehensive version for expressions with variables (characters)
        
        if (input.equals("+") || input.equals("-") || input.equals("*") || input.equals("/") || input.equals("(") || input.equals(")") || input.equals("^")){
            return true;
        } else {
            return false;
        }
        
        
        //Simplified version for expressions without variables (characters)
        /*
        if (input.charAt(0) >= 48 && input.charAt(0) <= 57){
            return false;
        } else {
            return true;
        }
        */
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
    
    public static double evaluate(String[] prefixExpression){
        double result = 0;
        double operandA = 0;
        double operandB = 0;
        Stack stack = new Stack(prefixExpression.length);
        for (int i = prefixExpression.length - 1; i > -1; i--){
            if (!isOperator(prefixExpression[i])){
                stack.push(prefixExpression[i]);
            } else {
                if (prefixExpression[i].equals("+")){
                    operandB = Double.parseDouble(stack.pop());
                    operandA = Double.parseDouble(stack.pop());
                    stack.push(Double.toString(operandA + operandB));
                } else if (prefixExpression[i].equals("-")){
                    operandB = Double.parseDouble(stack.pop());
                    operandA = Double.parseDouble(stack.pop());
                    stack.push(Double.toString(operandA - operandB));
                } else if (prefixExpression[i].equals("*")){
                    operandB = Double.parseDouble(stack.pop());
                    operandA = Double.parseDouble(stack.pop());
                    stack.push(Double.toString(operandA * operandB));
                } else if (prefixExpression.equals("/")){
                   operandB = Double.parseDouble(stack.pop());
                    operandA = Double.parseDouble(stack.pop());
                    stack.push(Double.toString(operandA / operandB));
                } else if (prefixExpression[i].equals("^")){
                    operandB = Double.parseDouble(stack.pop());
                    operandA = Double.parseDouble(stack.pop());
                    stack.push(Double.toString(Math.pow(operandA, operandB)));
                }
            }
        }
        result = Double.parseDouble(stack.pop());
        return result;
    }
    
    public static void convertToTree(String[] prefixExpression, BinaryTree tree){
        //Implementation
    }
    
    public static double traverse(BinaryTree tree){
        double result = 0;
        //Implementation
        return result;
    }
}