package special;

public class Function {
    String name;
    String var;
    String expression;
    
    public Function(String name, String var, String expression){
        this.name = name;
        this.var = var;
        this.expression = expression;
    }
    
    public void setName(String name){this.name = name;}
    public void setVar(String var){this.var = var;}
    public void setExpression(String expression){this.expression = expression;}
    
    public String getName(){return name;}
    public String getVar(){return var;}
    public String getExpression(){return expression;}
    
}
