package jatran.core;

import jatran.lexing.JavaTokenTypes;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import jemitter.IndentingPrintStream;
import antlr.CommonASTWithHiddenTokens;
import antlr.TokenStreamHiddenTokenFilter;
import antlr.CommonASTWithHiddenTokens;
import antlr.TokenStreamHiddenTokenFilter;
import antlr.collections.AST;

/**
 * SourcePrinter: A source to source transformer from Java to another language
 *
 * SourcePrinter takes an ANTLR Java AST and produces reasonably formatted, translated
 * code from it.Typically, the AST node that you pass would be the root of a tree -
 * the ROOT_ID node that represents a Java compilation unit.
 *
 * SourcePrinter was created out of Andy Tripp's JavaEmitter
 */

/**
 * Print the given AST. Call this function to print your Java code.
 *
 * <p> <b>Overall Approach</b><br>
 * It works by making recursive calls to print children. For example,
 * the root of the AST tree has type ROOT_ID. A Java AST node generated from the
 * ANTLR java.g file will have type ROOT_ID and will have
 * the following children, in this order:
 *     0 or 1 children of type PACKAGE_DEF
 *     0 or more children of type IMPORT
 *     One child of type CLASS_DEF or type INTERFACE_DEF
 *<p>
 * So the code below is one big "switch" statement on the passed AST type.
 * In the "ROOT_ID" case, the code here does the following:
 * <ol>
 * <li>calls getChild(ast, PACKAGE_DEF) to get the
 * child of type PACKAGE_DEF, and makes a recursive call to print() on that AST.
 * If there is no "PACKAGE_DEF" child, getChild() would return null and the
 * recursive print() call would print nothing.
 * <li>Calls printChildren(), passing the AST, the "\n" separator, and
 * the type "IMPORT". printChildren() will print all children of the AST,
 * separating each by the "\n" separator.
 * <li>Does the same thing will CLASS_DEF that it did with PACKAGE_DEF: calls
 * getChild() to get the child of type CLASS_DEF (or null if there is none),
 * and then makes a recursive call to print().
 * <li>Does the same thing for INTEFACE_DEF: call getChild() to get the child
 * of type INTEFACE_DEF, and make a recursive call.
 *</ol>
 *
 * <p> <b>Indenting</b><br>
 * One important issue is how to do proper indenting. The IndentingPrintStream
 * class handles indenting. Here, we simply create an IndentingPrintStream from
 * our normal PrintStream (either System.out or whatever was passed to setOut()).
 * And then we call increaseIndent() and decreaseIndent() as we see "{" and "}"
 * AST nodes.
 *
 * <p> <b>Adding Parentheses</b><br>
 *
 * The only other tricky part here is in printing parentheses, which are not kept
 * as AST nodes, but are built-in ("inherent") to the structure of the AST.
 * The printWithParens() method is used to print all unary and binary operators.
 * This method uses a precedence table to determine whether it needs to print
 * parentheses or not.
 */
public abstract class SourcePrinter implements JavaTokenTypes {
    private TokenStreamHiddenTokenFilter filter;
    public SourcePrinter() {
        setupTokenNames();
        setupKeywords();
    }

    /////////////// jmv ////////////////////////////
    public void setFilter( TokenStreamHiddenTokenFilter filter) {
            this.filter = filter;
    }
    /** walk list of hidden tokens in order, printing them out */
    public void dumpHidden(antlr.CommonHiddenStreamToken t) {
      for ( ; t!=null ; t= filter.getHiddenAfter(t) ) {
        print(t.getText());
      }
    }

    public void print(final AST ast, final PrintStream stream, final boolean untyped) {
        this.untyped = untyped;
        print(ast, stream);
    }

    public void print(final AST ast, PrintStream stream) {
        if (null == stream)
            stream = System.out;
        
        out = new IndentingPrintStream(stream);

        print(ast);
    }

    protected void print (final AST ast) {
        if (null == ast)
            return;

        AST parent = null;

        if (!stack.isEmpty())
            parent =  stack.peek();

        stack.push(ast);

        AST child1 = ast.getFirstChild();
        AST child2 = null;
        AST child3 = null;

       // jmv
       if (ast instanceof CommonASTWithHiddenTokens) {
               CommonASTWithHiddenTokens ast2 = (CommonASTWithHiddenTokens) ast;
               dumpHidden( ast2.getHiddenBefore() );
       } else {
               System.out.println("!!!! SourcePrinter.print(): " + ast.getClass() + " " + ast.toString() );
       }

        if (child1 != null) {
            child2 = child1.getNextSibling();
            if (child2 != null)
                child3 = child2.getNextSibling();
        }

        switch(ast.getType()) {
        case ROOT_ID:
            printRoot(ast); break;

        case PACKAGE_DEF: break;

        case IMPORT:
            printImport(ast); break;

        case CLASS_DEF:
        case INTERFACE_DEF:
            printDefinition(ast, parent); break;

        case MODIFIERS:
            printModifiers(ast); break;

        case EXTENDS_CLAUSE:
            printExtendsClause(ast); break;

        case IMPLEMENTS_CLAUSE:
            printImplementsClause(ast); break;

        case DOT:
            printDot(child1, child2); break;

        case OBJBLOCK:
            printObjectBlock(ast); break;

        case CTOR_DEF:
            printCtorDefinition(ast); break;

        case METHOD_DEF:
            printMethodDefinition(ast); break;

        case PARAMETERS:
            printParameters(ast); break;

        case PARAMETER_DEF:
            printParamDef(ast); break;

        case VARIABLE_DEF:
            printVariableDef(ast, parent); break;

        case TYPE:
            printType(ast); break;

        case ARRAY_DECLARATOR:
            printArrayDeclarator(child1); break;

        case ASSIGN:
            printAssignment(child1, child2); break;

        case EXPR:
            printExpression(parent, child1); break;

        case ARRAY_INIT:
            printArrayInitialization(ast); break;

        case SLIST:
            printStatementList(ast); break;

        case PLUS:
        case MINUS:
        case DIV:
        case MOD:
        case NOT_EQUAL:
        case EQUAL:
        case LT:
        case GT:
        case LE:
        case GE:
        case LOR:
        case LAND:
        case BOR:
        case BXOR:
        case BAND:
        case SL:
        case SR:
        case BSR:
        case LITERAL_instanceof:
        case PLUS_ASSIGN:
        case MINUS_ASSIGN:
        case STAR_ASSIGN:
        case DIV_ASSIGN:
        case MOD_ASSIGN:
        case SR_ASSIGN:
        case BSR_ASSIGN:
        case SL_ASSIGN:
        case BAND_ASSIGN:
        case BXOR_ASSIGN:
        case BOR_ASSIGN:
            printBinaryOperator(ast); break;

        case LITERAL_for:
            printForLoop(ast); break;

        case FOR_INIT:
        case FOR_CONDITION:
        case FOR_ITERATOR:
            print(child1); break;

        case FOR_EACH_CLAUSE:
            printForEach(ast); break;

        case STATIC_IMPORT:
            todo(ast); break;

        case ANNOTATIONS:
            AST dot = ast.getNextSibling();
            assert dot.getType() == DOT;
            print(dot);
            break;

        case VARIABLE_PARAMETER_DEF:
            printVariableParamDef(ast); break;

        case ENUM_DEF:
        case ENUM_CONSTANT_DEF:
        case TRIPLE_DOT:
            todo(ast); break;

        case TYPE_PARAMETERS:
            printTypeParameters(ast); break;
				
        case TYPE_PARAMETER:
            printTypeParameter(child1, child2); break;
				
        case TYPE_UPPER_BOUNDS:
            printUpperBoundTypeParameter(child1); break;

        case TYPE_LOWER_BOUNDS:
        case WILDCARD_TYPE:
            todo(ast); break;

        //type arguments must be called explicitly from ident now	
        case TYPE_ARGUMENTS:
        case TYPE_ARGUMENT: break;

        case ANNOTATION:
            printAnnotation(ast); break;

        case ANNOTATION_MEMBER_VALUE_PAIR:
            printAnnotationMemberValuePair(ast); break;

        case ANNOTATION_DEF:
        case ANNOTATION_FIELD_DEF:
        case ANNOTATION_ARRAY_INIT:
            todo(ast); break;

        case ELIST:
            printExpressionList(ast); break;
                    
        case POST_INC:
        case POST_DEC:
            printPostAssignment(ast, child1); break;
                    
        case BNOT:
        case LNOT:
        case UNARY_MINUS:
        case UNARY_PLUS:
            printUnary(ast, child1); break;
                    
        case INC:
        case DEC:
            printIncDec(ast, child1); break;
                                
        case LITERAL_new:
            printNew(child1, child2, child3); break;
                    
        case METHOD_CALL:
            printMethodCall(child1, child2); break;
                    
        case LITERAL_return:
            //TODO: figure if this is the last statement in a method
            printReturn(child1, child2); break;
                    
        case INSTANCE_INIT:
            printInstanceInit(child1); break;
                    
        case STATIC_INIT:
            printStaticInit(child1); break;
                    
        case TYPECAST:
            printTypeCast(child1, child2); break;
                                
        case LITERAL_switch:
            printSwitch(ast, child1); break;
                    
        case CASE_GROUP:
            printCaseGroup(ast); break;
                    
        case LITERAL_case:
            printCaseExpression(child1); break;

        case LITERAL_default:
            printDefaultCase(child1); break;

        case IDENT:
            printIdent(ast); break;
				
        case NUM_INT:
        case NUM_LONG:
        case CHAR_LITERAL:
        case STRING_LITERAL:
        case NUM_FLOAT:
        case NUM_DOUBLE:
            printASTText(ast); break;
                    
        case LITERAL_private:
        case LITERAL_public:
        case LITERAL_protected:
        case LITERAL_static:
        case LITERAL_transient:
        case LITERAL_native:
        case LITERAL_threadsafe:
        case LITERAL_synchronized:
        case LITERAL_volatile:
        case FINAL:
        case ABSTRACT:
        case LITERAL_package:
        case LITERAL_void:
        case LITERAL_boolean:
        case LITERAL_byte:
        case LITERAL_char:
        case LITERAL_short:
        case LITERAL_int:
        case LITERAL_float:
        case LITERAL_long:
        case LITERAL_double:
        case LITERAL_true:
        case LITERAL_false:
        case LITERAL_null:
        case SEMI:
            printASTName(ast); break;
                    
        case LITERAL_this:
        case LITERAL_super:
            printASTName(ast); break;
                    
        case LITERAL_continue:
        case LITERAL_break:
            printContinueBreak(ast, (parent.getType() != CASE_GROUP && parent.getType() != LITERAL_default)); break;
                    
        case INDEX_OP:
            printIndexOperator(child1, child2); break;
                    
        case EMPTY_STAT:
            printEmptyStatement(); break;
                    
        case STAR:
            printStar(ast); break;
                    
        case LITERAL_throws:
            printThrows(ast); break;
                    
        case LITERAL_if:
            printIfStatement(child1, child2, child3); break;
                    
        case LITERAL_while:
            br();
            printWhileLoop(child1, child2); break;
                    
        case LITERAL_do:
            printDoLoop(child1, child2); break;
                    
        case LITERAL_try:
            printTry(ast, child1); break;
                    
        case LITERAL_catch:
            printCatch(child1, child2); break;
                    
        case LITERAL_finally: 
            // handled from the try catch blocks (should i throw errors here?)
            //printFinally(child1, child2);
            break;
                    
        case LITERAL_throw:
            printThrow(child1); break;
                    
        case QUESTION:
            printTrinaryOp(child1, child2, child3); break;

        case LITERAL_class:
            printClassLiteral(); break;

        case SUPER_CTOR_CALL:
            printSuperConstructorCall(ast); break;
                    
        case CTOR_CALL:
            printConstructorCall(ast); break;
                    
        case SL_COMMENT:
        case ML_COMMENT: 
            err.println("hmmm, we've got some comments");
            break;
                    
        case LITERAL_assert:
            print("assert ("); 
            print(child1);
            print(")");
            br();
            break;
                    
                    
        default:
            err.println("Invalid type:" + ast.getType());
        }
		
        stack.pop();
        previousType = ast.getType();
        brApplied = false;
    }

    protected void printComment(AST ast) {}

    protected void printIdent(final AST ast) {}

    protected void printTypeParameter(final AST child1, final AST child2) {}
    protected void printUpperBoundTypeParameter(final AST child1) {}


    protected void printAnnotation(final AST ast) {}

    protected void printAnnotationMemberValuePair(final AST ast) {}

    protected void printForEach(final AST ast) {}

    protected void printCtorDefinition(final AST ast) {
        printMethodDefinition(ast);
    }

    protected void printIncDec(final AST ast, final AST child1) {
        printUnary(ast, child1);
    }

    protected void printPostAssignment(final AST ast, final AST child1) {
        print(child1);
        printASTName(ast);
    }

    protected void printImports(final AST ast) {
        List<AST> imports = getChildren(ast, IMPORT);
		
        if(imports.size() <= 0)
            return;
		
        for (AST imp : imports)
            printImport(imp);
		
        br(2);
    }

    /**
     * some (but not all) of the children of a SEMI node should be suffixed by ';'.
     * for example, EXPR should be suffixed by ';' ( but only when its parent is a SLIST).
     */
    protected void printSemi(final AST parent) {
        if (parent!= null && parent.getType() == SLIST)
            printEmptyStatement();
    }


    /**
     * Print the children of the given AST
     * @param ast The AST to print
     * @param separator The separator to use (typically space or newline)
     * @returns true iff anything was printed
     */
    protected boolean printChildren(final AST ast, final String separator) {
        return printChildren(ast, separator, ALL);
    }

    /**
     * Print all of the children of the given AST that are of the given type
     * @param ast The AST to print
     * @param separator The separator to use (typically space or newline)
     * @param type The type of child AST to print
     * @returns true if anything was printed
     */
    protected boolean printChildren(final AST ast, final String separator, final int type) {
        boolean ret = false;
        AST child = ast.getFirstChild();
		
        while (child != null) {
            if (type == ALL || child.getType() == type) {
                // print a separator before each printed child (except first)
                if (!(ast.getFirstChild() == child))
                    if (separator.endsWith("\n")) {
                        out.print(separator.substring(0,separator.length()-1));
                        br();
                    } else
                        out.print(separator);
                ret = true;
                print(child);
            }
            child = child.getNextSibling();
        }

        return ret;
    }

    /**
     * Prints an AST, adding parenthises if they are needed.
     * Parens are needed inside an expression when the precendence of the
     * parent AST is lower than the child AST.
     */
    protected void printWithParens(final AST parent, final AST ast) {
        boolean parensNeeded = getPrecedence(parent) < getPrecedence(ast);
        if (parensNeeded)
            print("(");

        print(ast);

        if (parensNeeded)
            print(")");
    }

    protected void printImport(final AST ast) {}
    
    protected void printRoot(final AST ast) {}

    protected void printDefinition(final AST ast, final AST parent) {}

    protected void printMethodDefinition(final AST ast) {}

    protected void printObjectBlock(final AST ast) {}
    
    protected void printSuperConstructorCall(final AST ast) {}
    
    protected void printConstructorCall(final AST ast) {}
    
    protected void printClassLiteral() {}
    
    protected void printTrinaryOp(final AST child1, final AST child2, final AST child3) {}
    
    protected void printThrow(final AST child1) {}
    
    protected void printFinally(final AST child1, final AST child2) {}
    
    protected void printCatch(final AST child1, final AST child2) {}
    
    protected void printTry(final AST ast, final AST child1) {}
    
    protected void printDoLoop(final AST child1, final AST child2) {}
    
    protected void printWhileLoop(final AST child1, final AST child2) {}
    
    protected void printIfStatement(final AST condition, final AST thenClause, final AST elseClause) {}
    
    protected void printThrows(final AST ast) {}
    
    protected void printStar(final AST ast) {}
    
    protected void printEmptyStatement() {}
    
    protected void printIndexOperator(final AST child1, final AST child2) {}
    
    protected void printContinueBreak(final AST ast, boolean notCaseGroup) {}
    
    protected void printDefaultCase(final AST child1) {}
    
    protected void printCaseExpression(final AST child1) {}
    
    protected void printCaseGroup(final AST ast) {}

    protected void printSwitch(final AST ast, final AST child1) {}
    
    protected void printTypeCast(final AST child1, final AST child2) {}
    
    protected void printStaticInit(final AST child1) {}
    
    protected void printInstanceInit(final AST child1) {}
    
    protected void printReturn(final AST child1, final AST child2) {}
    
    protected void printMethodCall(final AST child1, final AST child2) {}
    
    protected void printNew(final AST child1, final AST child2, final AST child3) {}
    
    protected void printUnary(final AST ast, final AST child1) {}
    
    protected void printExpressionList(final AST ast) {}
    
    protected void printForLoop(final AST ast) {}
    
    protected void printStatementList(final AST ast) {}
    
    protected void printArrayInitialization(final AST ast) {}
    
    protected void printExpression(final AST parent, final AST child1) {}
    
    protected void printAssignment(final AST child1, final AST child2) {}
    
    protected void printArrayDeclarator(final AST child1) {}
    
    protected void printType(final AST ast) {}
    
    protected void printVariableDef(final AST ast, final AST parent) {}
    
    protected void printParamDef(final AST ast) {}

    protected void printVariableParamDef(final AST ast) {}

    protected void printParameters(final AST ast) {}

    protected void printTypeArguments(final AST ast) {
        printTypeArguments(getChildren(ast, TYPE_ARGUMENT)); 
    }
	
    protected void printTypeArguments(final List<AST> list) {}
    
    protected void printTypeParameters(final AST ast) {}
    
    protected void printImplementsClause(final AST ast) {}
    
    protected void printExtendsClause(final AST ast) {}
    
    protected void printModifiers(final AST ast) {}
    
    //TODO:retrieve name of dot, so we can for instance do System.out.println -> println
    protected void printDot(final AST child1, final AST child2) {
        print(child1);
        print(".");
        print(child2);
    }

    protected void printBinaryOperator(final AST ast) {
        printWithParens(ast, ast.getFirstChild());
        print(" " + name(ast) + " ");
        printWithParens(ast, ast.getFirstChild().getNextSibling());
    }
    
    protected void printASTName(final AST ast) {
        print(name(ast));
    }
    
    protected void printASTText(final AST ast) {
        String s = ast.getText().trim();
        if (!(null == KEYWORDS.get(s))) {
            print("__kwd_" + s);
            return;
        }
	
        print(ast.getText());
    }
    
    public void debug(final AST ast) {
        debug(ast, 0);
    }
    
    // A simple method to print out an AST as a tree:
    public void debug(final AST t, final int level) {
        if ( t==null )
            return;
        err.print("                                                                                             ".substring(0, level));
        err.println("text:" + t.getText() + " type=" + t.getType());
        AST child = t.getFirstChild();
        debug(child, level+2);
        AST next = t.getNextSibling();
        debug(next, level);
    }
    
    /**
     *  Starts a block by printing "{" and increasing the indent level.
     */
    protected void startBlock() {
        print("{");
        startIndent();
    }
    
    /**
     *  Starts a block by decreasing the indent level and printing "}"
     */
    protected void endBlock() {
        if (!brApplied)
            br();
        out.decreaseIndent();
        print("}");
    }
    
    protected void print(final String s) {
        out.print(s);
    }
    
    protected void br() {
        br(1);
    }
    
    protected void br(final int n) {
        for (int i = 0; i < n; ++i)
            out.println();
        brApplied = true;
    }
    
    /**
     * Find a child of the given AST that has the given type
     * @returns a child AST of the given type. If it can't find a child of the given type, return null.
     */
    protected AST getChild(final AST ast, final int type) {
        AST child = ast.getFirstChild();
        while (child != null) {
            if (ALL == type || child.getType() == type)
                return child;
            child = child.getNextSibling();
        }
        return null;
    }
    

    
    protected List<AST> getChildren(final AST ast) {
        return getChildren(ast, ALL);
    }
    
    protected List<AST> getChildren(final AST ast, final int type) {
        List<AST> list = new ArrayList<AST>();
        
        AST child = ast.getFirstChild();
        while(child != null) {
            if (ALL == type || child.getType() == type)
                list.add(child);
            child = child.getNextSibling();
        }
        
        return list;
    }

    protected void print(final List<AST> list) {
        int n = list.size();
	
        if (n > 0) {
            for (AST ast : list.subList(0, 1))
                print(ast);
            
            try {
                for (AST ast : list.subList(1, n)) {
                    br();
                    print(ast);
                }
            } catch (IndexOutOfBoundsException e) {
                //br();
            }
        }
    }
    
    protected void closeIndent(final AST ast) {
        if (indentable(ast))
            closeIndent();
    }
    
    protected void closeIndent() {
        out.decreaseIndent();
        br();
    }
    
    protected void startIndent(final AST ast) {
        if (indentable(ast))
            startIndent();
    }
    
    protected void startIndent() {
        br();
        out.increaseIndent();
    }

    protected boolean indentable(final AST ast) {
        return !(null == ast || ast.getType() == SLIST); //|| ast.getType() == LITERAL_if
    }


    /**
     * Tells whether an AST has any children or not.
     * @returns true iff the AST has at least one child
     */
    protected boolean hasChildren(final AST ast) {
        return ast.getFirstChild() != null;
    }

    protected String name(final AST ast) {
        return name(ast.getType());
    }

    protected String name(final int type) {
        return TOKEN_NAMES[type];
    }

    protected void todo(AST ast) {
        err.println(">>>>>>>>>>>>>>>>>>>>>>  THIS IS NOT DONE!!");
        debug(ast);
    }

    // A Precendence table. Here are operator precendences (from java.g):
    //    lowest  (13)  = *= /= %= += -= <<= >>= >>>= &= ^= |=
    //            (12)  ?:
    //            (11)  ||
    //            (10)  &&
    //            ( 9)  |
    //            ( 8)  ^
    //            ( 7)  &
    //            ( 6)  == !=
    //            ( 5)  < <= > >=
    //            ( 4)  << >>
    //            ( 3)  +(binary) -(binary)
    //            ( 2)  * / %
    //            ( 1)  ++ -- +(unary) -(unary)  ~  !  (type)
    //                  []   () (method call)  . (dot -- identifier qualification)
    //                  new   ()  (explicit parenthesis)
    
    protected static int getPrecedence(final AST ast) {
        if (null == ast)
            return -2;
        
        switch (ast.getType()) {
        case EXPR:
            return getPrecedence(ast.getFirstChild());
            
        case ASSIGN:
        case PLUS_ASSIGN:
        case MINUS_ASSIGN:
        case STAR_ASSIGN:
        case DIV_ASSIGN:
        case MOD_ASSIGN:
        case SR_ASSIGN:
        case BSR_ASSIGN:
        case SL_ASSIGN:
        case BAND_ASSIGN:
        case BXOR_ASSIGN:
        case BOR_ASSIGN:
            return 13;
            
        case QUESTION:
            return 12;
        case LOR:
            return 11;
        case LAND:
            return 10;
        case BOR:
            return 9;
        case BXOR:
            return 8;
        case BAND:
            return 7;
            
        case NOT_EQUAL:
        case EQUAL:
            return 6;
            
        case LT:
        case GT:
        case LE:
        case GE:
        case LITERAL_instanceof:
            return 5;
            
        case SL:
        case SR:
        case BSR:	// not in chart above, but I would guess it goes here
            return 4;
            
        case PLUS:
        case MINUS:
            return 3;
            
        case DIV:
        case MOD:
        case STAR:
            return 2;
            
        case INC:
        case DEC:
        case POST_INC:
        case POST_DEC:
        case UNARY_PLUS:
        case UNARY_MINUS:
        case LNOT:
        case BNOT:
        case TYPE:
            return 1;
            
        case METHOD_CALL:
        case ARRAY_DECLARATOR:
        case DOT:
            return 0;

        case LITERAL_new:
            return -1;
            
        }
        // for any non-operator, return a value which will cause it to NOT need parens.
        return -2;
    }

    protected void setupKeywords() {
        KEYWORDS = new HashMap<String, Integer>();
    }
    
    // Map each AST token type to a String
    protected void setupTokenNames() {
        if (null != TOKEN_NAMES)
            return;
        
        TOKEN_NAMES = new String[200];
        for (int i=0; i<TOKEN_NAMES.length; i++)
            TOKEN_NAMES[i] = "ERROR:" + i;
        
        TOKEN_NAMES[POST_INC]="++";
        TOKEN_NAMES[POST_DEC]="--";
        TOKEN_NAMES[UNARY_MINUS]="-";
        TOKEN_NAMES[UNARY_PLUS]="+";
        TOKEN_NAMES[STAR]="*";
        TOKEN_NAMES[ASSIGN]="=";
        TOKEN_NAMES[PLUS_ASSIGN]="+=";
        TOKEN_NAMES[MINUS_ASSIGN]="-=";
        TOKEN_NAMES[STAR_ASSIGN]="*=";
        TOKEN_NAMES[DIV_ASSIGN]="/=";
        TOKEN_NAMES[MOD_ASSIGN]="%=";
        TOKEN_NAMES[SR_ASSIGN]=">>=";
        TOKEN_NAMES[BSR_ASSIGN]=">>>=";
        TOKEN_NAMES[SL_ASSIGN]="<<=";
        TOKEN_NAMES[BAND_ASSIGN]="&=";
        TOKEN_NAMES[BXOR_ASSIGN]="^=";
        TOKEN_NAMES[BOR_ASSIGN]="|=";
        TOKEN_NAMES[QUESTION]="?";
        TOKEN_NAMES[LOR]="||";
        TOKEN_NAMES[LAND]="&&";
        TOKEN_NAMES[BOR]="|";
        TOKEN_NAMES[BXOR]="^";
        TOKEN_NAMES[BAND]="&";
        TOKEN_NAMES[NOT_EQUAL]="!=";
        TOKEN_NAMES[EQUAL]="==";
        TOKEN_NAMES[LT]="<";
        TOKEN_NAMES[GT]=">";
        TOKEN_NAMES[LE]="<=";
        TOKEN_NAMES[GE]=">=";
        TOKEN_NAMES[SL]="<<";
        TOKEN_NAMES[SR]=">>";
        TOKEN_NAMES[BSR]=">>>";
        TOKEN_NAMES[PLUS]="+";
        TOKEN_NAMES[MINUS]="-";
        TOKEN_NAMES[DIV]="/";
        TOKEN_NAMES[MOD]="%";
        TOKEN_NAMES[INC]="++";
        TOKEN_NAMES[DEC]="--";
        TOKEN_NAMES[BNOT]="~";
        TOKEN_NAMES[LNOT]="!";
        TOKEN_NAMES[FINAL]="";
    }
    
    public final static int ROOT_ID = 0;
    
    protected static int ALL = -1;
    protected IndentingPrintStream out; //TODO: make dynamic
    protected PrintStream err = System.out;
    protected Stack<AST> stack = new Stack<AST>();
    protected boolean untyped = false;
    
    protected static String[] TOKEN_NAMES = null;
    protected static Map<String, Integer> KEYWORDS = null;
    protected int previousType = -1;
    protected boolean brApplied = false;
}
