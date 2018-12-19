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
        }
        return input;
    }
    
    static String[] toPrefix(String[] input){
        
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
