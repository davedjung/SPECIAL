package mathematicalexpressionprocessor;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
                
        Scanner scan = new Scanner(System.in);
        
        System.out.println("Mathematical Expression Processor version 0.2");
        System.out.println();
                
        System.out.println("Enter infix expression : ");        
        
        String input = scan.nextLine();
        String[] parsedInput = expressionParser(input);
        String[] prefixExpression = infixToPrefixConverter(parsedInput);
        double result = evaluate(prefixExpression);
        
        System.out.println("Result is : " + result);
    }
    
    public static String[] expressionParser(String input){
        
        int arraySize = input.length()*2+1;
        String[] expression = new String[arraySize];
        
        //Use \\ to parse the expression
        //@ indicates "can be ignored"
        for (int i=0; i<=input.length()*2; i+=2){
            expression[i] = "\\";
        }
        for (int j=0; j<input.length(); j++){
            expression[j*2+1] = input.substring(j,j+1);
        }
        
        for (int k=1; k<arraySize-1; k++){
            //Remove blank spaces
            if (expression[k-1].equals(" ")){
                expression[k-1] = "@";
                expression[k] = "@";
            }
            //Bring together numbers
            if (isNumber(expression[k-1]) && isNumber(expression[k+1])){
                expression[k] = "@";
            }
            //Dealing with unary negation "-"
            if (!isNumber(expression[k-1]) && expression[k+1].equals("-")){
                expression[k+2] = "@";
            }            
            //Identifying exp()
            if (expression[k-1].equals("e") && expression[k+1].equals("x")){
                if (k+3 < arraySize){
                    if (expression[k+3].equals("p")){
                        expression[k+1] = "^";
                        expression[k+2] = "@";
                        expression[k+3] = "@";
                    }
                }
            }
            //Identifying sin()
            if (expression[k-1].equals("s") && expression[k+1].equals("i")){
                if (k+3 < arraySize){
                    if (expression[k+3].equals("n")){
                        expression[k] = "@";
                        expression[k+2] = "@";
                    }
                }
            }
            //Identifying cos()
            if (expression[k-1].equals("c") && expression[k+1].equals("o")){
                if (k+3 < arraySize){
                    if (expression[k+3].equals("s")){
                        expression[k] = "@";
                        expression[k+2] = "@";
                    }
                }
            }
            //Identifying tan()
            if (expression[k-1].equals("t") && expression[k+1].equals("a")){
                if (k+3 < arraySize){
                    if (expression[k+3].equals("n")){
                        expression[k] = "@";
                        expression[k+2] = "@";
                    }
                }
            }
            //Identifying csc()
            if (expression[k-1].equals("c") && expression[k+1].equals("s")){
                if (k+3 < arraySize){
                    if (expression[k+3].equals("c")){
                        expression[k] = "@";
                        expression[k+2] = "@";
                    }
                }
            }
            //Identifying sec()
            if (expression[k-1].equals("s") && expression[k+1].equals("e")){
                if (k+3 < arraySize){
                    if (expression[k+3].equals("c")){
                        expression[k] = "@";
                        expression[k+2] = "@";
                    }
                }
            }
            //Identifying cot()
            if (expression[k-1].equals("c") && expression[k+1].equals("o")){
                if (k+3 < arraySize){
                    if (expression[k+3].equals("t")){
                        expression[k] = "@";
                        expression[k+2] = "@";
                    }
                }
            }
            //Identifying arc()
            if (expression[k-1].equals("a") && expression[k+1].equals("r")){
                if (k+3 < arraySize){
                    if (expression[k+3].equals("c")){
                        expression[k] = "@";
                        expression[k+2] = "@";
                        expression[k+4] = "@";
                    }
                }
            }
            //Identifying sinh()
            if (expression[k-1].equals("s") && expression[k+1].equals("i")){
                if (k+5 < arraySize){
                    if (expression[k+3].equals("n") && expression[k+5].equals("h")){
                        expression[k] = "@";
                        expression[k+2] = "@";
                        expression[k+4] = "@";
                    }
                }
            }
            //Identifying cosh()
            if (expression[k-1].equals("c") && expression[k+1].equals("o")){
                if (k+5 < arraySize){
                    if (expression[k+3].equals("s") && expression[k+5].equals("h")){
                        expression[k] = "@";
                        expression[k+2] = "@";
                        expression[k+4] = "@";
                    }
                }
            }
            //Identifying tanh()
            if (expression[k-1].equals("t") && expression[k+1].equals("a")){
                if (k+5 < arraySize){
                    if (expression[k+3].equals("n") && expression[k+5].equals("h")){
                        expression[k] = "@";
                        expression[k+2] = "@";
                        expression[k+4] = "@";
                    }
                }
            }
            //Identifying sqrt()
            if (expression[k-1].equals("s") && expression[k+1].equals("q")){
                if (k+5 < arraySize){
                    if (expression[k+3].equals("r") && expression[k+5].equals("t")){
                        expression[k] = "@";
                        expression[k+2] = "@";
                        expression[k+4] = "@";
                    }
                }
            }
        }
        
        //Handling possible blank space at the end
        //Special treatment to avoid index out of bound error
        if (expression[arraySize-2].equals(" ")){
            expression[arraySize-2] = "@";
        }
        
        //Rearrange the expression so that \\ and @ are removed
        String[] tokenized = new String[input.length()];
        
        for (int i=0; i<input.length(); i++){
            tokenized[i] = "";
        }
        
        int index = 0;
        for (int l=1; l<arraySize; l++){
            if (!expression[l].equals("\\")){
                if (!expression[l].equals("@")){
                    tokenized[index] = tokenized[index] + expression[l];
                }
            } else {
                index++;
            }
        }
        
        //Clean up the wasted spaces at the end of the array
        //by copying to a new array "infix" which is also the return value
        int endIndex = tokenized.length;
        for (int i=0; i<tokenized.length; i++){
            if (tokenized[i].equals("")) {
                endIndex = i;
                break;
            }
        }
        
        String[] infix = new String[endIndex];
        for (int i=0; i<endIndex; i++){
            infix[i] = tokenized[i];
        }
        
        return infix;
    }
    
    public static boolean isNumber(String input){
        if (input.charAt(0) >= 48 && input.charAt(0) <= 57){
            return true;
        } else {
            return false;
        }
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
    
}