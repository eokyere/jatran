package jatran.stub;


/**
 * return [Expr]
 *
 * A return expression return e must occur inside the body of some enclosing named 
 * method or function. The innermost enclosing named method or function in a 
 * source program, f , must have an explicitly declared result type, and the type of e 
 * must conform to it. The return expression evaluates the expression e and returns its 
 * value as the result of f . The evaluation of any statements or expressions following 
 * the return expression is omitted. The type of a return expression is scala.Nothing. 
 */
public class ReturnExpressions {
    public int shouldPrintReturns(int start, int n) {
        for (int i = start; i < n; ++i)
            if (i / 5 > 1)
                return i;
        return -1;
    }

    public boolean shouldNotPrintReturns(boolean bool) {
        return bool;
    }   
}