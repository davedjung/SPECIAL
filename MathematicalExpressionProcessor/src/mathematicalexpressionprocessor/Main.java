package mathematicalexpressionprocessor;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
                
        Scanner scan = new Scanner(System.in);
        
        System.out.println("Mathematical Expression Processor version 0.1");
        System.out.println();
                
        System.out.println("Enter infix expression : ");        
        //double result = evaluate(infixToPrefixConverter(expressionParser(scan.nextLine())));
        //Expression above is equivalent to the following lines of code
        
        String rawInput = scan.nextLine();
        String[] parsedInput = expressionParser(rawInput);
        String[] prefixExpression = infixToPrefixConverter(parsedInput);
        double result = evaluate(prefixExpression);
        
        System.out.println("Result is : " + result);
        //Binary tree conversion and evaluation are to be implemented
        /*
        BinaryTree tree = new BinaryTree();
        convertToTree(prefixExpression, tree);
        result = traverse(tree);
        System.out.println("Result is : " + result);
        */
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
                while (priority(infix_reversed[j]) < priority(stack.lastItem())){
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
            //priority of parentheses are set to 0 due to infix-postfix conversion algorithm
            return 0;
        } else {
            //in case of empty input. Also due to infix-postfix conversion algorithm
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
                switch (prefixExpression[i]) {
                    case "+":
                        stack.push(Double.toString(Double.parseDouble(stack.pop()) + Double.parseDouble(stack.pop())));
                        //Expression above is equivalent to the following lines of code
                        /*
                        operandA = Double.parseDouble(stack.pop());
                        operandB = Double.parseDouble(stack.pop());
                        stack.push(Double.toString(operandA + operandB));
                        */
                        break;
                    case "-":
                        stack.push(Double.toString(Double.parseDouble(stack.pop()) - Double.parseDouble(stack.pop())));
                        break;
                    case "*":
                        stack.push(Double.toString(Double.parseDouble(stack.pop()) * Double.parseDouble(stack.pop())));
                        break;
                    case "/":
                        stack.push(Double.toString(Double.parseDouble(stack.pop()) / Double.parseDouble(stack.pop())));
                        break;
                    case "^":
                        stack.push(Double.toString(Math.pow(Double.parseDouble(stack.pop()), Double.parseDouble(stack.pop()))));
                        break;
                    default:
                        break;
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