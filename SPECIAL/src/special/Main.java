package special;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to SPECIAL v0.1");
        System.out.println("Expression : ");
        String input = scan.nextLine();
        System.out.println("Input readback : " + input);
        
        String[] parsed = parse(input);
        
        String[] prefix = toPrefix(parsed);
        
        double output = evaluate(prefix);
        
        System.out.println(output);
    }
    
    static String[] parse(String input){
        int count = 0;
        for (int i=0; i<input.length(); i++){
            if (input.substring(i, i+1).equals(" ")){
                count++;
            }
        }
        int length = input.length() - count;
        String[] firstPass = new String[length];//Removes all spaces
        count = 0;
        for (int i=0; i<input.length(); i++){
            if (!input.substring(i, i+1).equals(" ")){
                firstPass[count++] = input.substring(i, i+1);
            }
        }
        length = 2 * firstPass.length + 1;
        
        String[] secondPass = new String[length];//Parse the expression
        for (int i=0; i<length; i+=2){
            secondPass[i] = "#";
        }
        for (int i=0; i<firstPass.length; i++){
            secondPass[i*2+1] = firstPass[i];
        }
        secondPass = detectFunctions(secondPass);
        count = -1;
        for (int i=0; i<length; i++){
            if (secondPass[i].equals("#")){
                count++;
            }
        }
        length = count;
        
        String[] thirdPass = new String[length];
        for (int i=0; i<length; i++){
            thirdPass[i] = "";
        }
        count = -1;
        for (int i=0; i<secondPass.length; i++){
            if (secondPass[i].equals("#")){
                count++;
            } else if (!secondPass[i].equals("@")){
                thirdPass[count] = thirdPass[count] + secondPass[i];
            }
        }
        if (thirdPass[0].equals("-")){
            thirdPass[0] = "-1";
        }
        for (int i=1; i<length; i++){
            if (isNotOperator(thirdPass[i-1])){
                if (thirdPass[i].equals("-")){
                    thirdPass[i] = "-1";
                }
            }
        }
        count = 0;
        for (int i=0; i<length-1; i++){
            if (!isOperator(thirdPass[i]) && !isOperator(thirdPass[i+1])){
                count++;
            }
        }
        length += count;
        
        String[] fourthPass = new String[length];
        count = 1;
        fourthPass[0] = thirdPass[0];
        for (int i=1; i<thirdPass.length; i++){
            if (!isOperator(thirdPass[i])){
                if (!isOperator(fourthPass[count-1])){
                    fourthPass[count++] = "*";
                }
            }
            fourthPass[count++] = thirdPass[i];
        }
        
        return fourthPass;
    }
    
    static String[] detectFunctions(String[] input){
        int length = input.length;
        //size 4
        for (int i=6; i<length; i++){
            if (input[i-6].equals("c")){
                if (input[i-4].equals("o")){
                    if (input[i-2].equals("s")){
                        if (input[i].equals("h")){
                            input[i-5] = "@";
                            input[i-3] = "@";
                            input[i-1] = "@";
                        }
                    }
                }
            }
            if (input[i-6].equals("s")){
                if (input[i-4].equals("i")){
                    if (input[i-2].equals("n")){
                        if (input[i].equals("h")){
                            input[i-5] = "@";
                            input[i-3] = "@";
                            input[i-1] = "@";
                        }
                    }
                }
            }
            if (input[i-6].equals("t")){
                if (input[i-4].equals("a")){
                    if (input[i-2].equals("n")){
                        if (input[i].equals("h")){
                            input[i-5] = "@";
                            input[i-3] = "@";
                            input[i-1] = "@";
                        }
                    }
                }
            }
        }
        //size 3
        for (int i=4; i<length; i++){
            if (input[i-4].equals("c")){
                if (input[i-2].equals("o")){
                    if (input[i].equals("s")){
                        input[i-3] = "@";
                        input[i-1] = "@";
                    }
                }
            }
            if (input[i-4].equals("e")){
                if (input[i-2].equals("x")){
                    if (input[i].equals("p")){
                        input[i-3] = "@";
                        input[i-1] = "@";
                    }
                }
            }
            if (input[i-4].equals("l")){
                if (input[i-2].equals("o")){
                    if (input[i].equals("g")){
                        input[i-3] = "@";
                        input[i-1] = "@";
                    }
                }
            }
            if (input[i-4].equals("s")){
                if (input[i-2].equals("i")){
                    if (input[i].equals("n")){
                        input[i-3] = "@";
                        input[i-1] = "@";
                    }
                }
            }
            if (input[i-4].equals("t")){
                if (input[i-2].equals("a")){
                    if (input[i].equals("n")){
                        input[i-3] = "@";
                        input[i-1] = "@";
                    }
                }
            }
        }
        //size 2
        for (int i=2; i<length; i++){
            if (input[i-2].equals("l")){
                if (input[i].equals("n")){
                    input[i-1] = "@";
                }
            }
            if (input[i-2].equals("p") || input[i-2].equals("P")){
                if (input[i].equals("i") || input[i].equals("I")){
                    input[i-2] = "@";
                    input[i-1] = Double.toString(Math.PI);
                    input[i] = "@";
                }
            }
        }
        //size 1
        for (int i=2; i<length; i++){
            if (input[i-1].equals(".")){
                input[i-2] = "@";
                input[i] = "@";
            }
        }
        return input;
    }
    
    static String[] toPrefix(String[] input){
        String prefix[];
        String postfix[];
        String infix[] = input;
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
    
    static double evaluate(String[] input){
        double result = 0;
        double operandA = 0;
        double operandB = 0;
        Stack stack = new Stack(input.length);
        for (int i = input.length - 1; i > -1; i--){
            if (!isNotOperator(input[i])){
                stack.push(input[i]);
            } else {
                switch (input[i]) {
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
                    case "sin":
                        stack.push(Double.toString(Math.sin(Double.parseDouble(stack.pop()))));
                        break;
                    case "cos":
                        stack.push(Double.toString(Math.cos(Double.parseDouble(stack.pop()))));
                        break;
                    case "tan":
                        stack.push(Double.toString(Math.tan(Double.parseDouble(stack.pop()))));
                        break;
                    case "sinh":
                        stack.push(Double.toString(Math.sinh(Double.parseDouble(stack.pop()))));
                        break;
                    case "cosh":
                        stack.push(Double.toString(Math.cosh(Double.parseDouble(stack.pop()))));
                        break;
                    case "tanh":
                        stack.push(Double.toString(Math.tanh(Double.parseDouble(stack.pop()))));
                        break;
                    case "exp":
                        stack.push(Double.toString(Math.exp(Double.parseDouble(stack.pop()))));
                        break;
                    case "log":
                        stack.push(Double.toString(Math.log10(Double.parseDouble(stack.pop()))));
                        break;
                    case "ln":
                        stack.push(Double.toString(Math.log(Double.parseDouble(stack.pop()))));
                        break;
                    default:
                        break;
                }
            }
        }
        result = Double.parseDouble(stack.pop());
        return result;
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
    
    static boolean isNotOperator(String input){
        if (input.equals("+") || input.equals("-") || input.equals("*") || input.equals("/") || input.equals("^")){
            return true;
        } else if (input.equals("(") || input.equals(")")){
            return true;
        } else if (input.contains("sin") || input.contains("cos") || input.contains("tan")){
            return true;
        } else if (input.contains("exp") || input.contains("log") || input.contains("ln")){
            return true;
        } else {
            return false;
        }
    }
    
    static boolean isOperator(String input){
         if (input.equals("+") || input.equals("-") || input.equals("*") || input.equals("/") || input.equals("^")){
            return true;
        } else if (input.equals("(") || input.equals(")")){
            return true;
        } else {
            return false;
        }
    }
    
}
