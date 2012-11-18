package jatran.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import antlr.collections.AST;

/**
 * NOTES: 3. Move the primary constructor of class X extends Foo { ... } up.
 * This yields a class X(arg1:T1,...,argsN:TN) extends Foo(...) { ... }. If you
 * have several constructors, choose one to be the primary one. 7. If you made
 * Java fields just to store constructor arguments, omit those and add val to
 * your primary constructor argument class X(arg1:T1,...,val argJ: TJ, ...
 * argsN:TN). Sorry this does not work with mutable fields (var). 10. Turn all
 * for loops into while loops. You could also use for-loops, but it takes to
 * long to write. Pay attention to not forget the increment, decrement
 * operations, which you of course have to move to the end of the block. for(int
 * i = ...; test; inc) {body} becomes val i = ...; while(test) {body; inc}. Of
 * course this pollutes the scope with variables (which in Java you might have
 * reused in other loops), but those you can fix by renaming later. 12. If you
 * have static stuff in a class X, create an object X of the same name and move
 * the translated thingies there (without the static modifier). Change access
 * sites of a static method or field into qualified access ( i.e. foo becomes
 * X.foo ). For most uses of static, this is enough, e.g. the main method.
 * 
 * TODO: 14. (remove all return statements). (Those at the end you don't need),
 * returns that are somewhere in the middle of your method body are either
 * supported or unsupported, depending on whether they get translated via some
 * syntactic quirks. Add exit flag variables in loops and stuff. The same holds
 * for break statements in for/while loops. Get rid of those things.
 * 
 * 15. You call super class constructor in class declaration itself.
 * 
 * 16. If return is only statement (without value) print it, ie. if (cond)
 * return;
 * 
 * class Foo(a1:T1, a2:T2, ... an:Tn) extends Bar(a1, .., an) { }
 * 
 * todo: print all keyworkds used as variable names as __kwd_name
 * 
 * References:
 * http://blogs.sun.com/sundararajan/entry/scala_for_java_programmers
 * http://lamp.epfl.ch/~emir/bqbase/2005/01/21/java2scala.html
 * http://scala.sygneca.com/faqs/language
 * 
 * @author eokyere
 */
public class ScalaPrinter extends SourcePrinter {
    @Override protected void printRoot(final AST ast) {
        try {
            // first child is IDENT
            AST pkg = getChild(ast, PACKAGE_DEF).getFirstChild();
            if (!(null == pkg || pkg.getText().equals(""))) {
                print("package ");
                print(pkg);
                br(2);
            }
        } catch (Exception e) {
        }
        
        printImports(ast);
        print(getChild(ast, CLASS_DEF));
        print(getChild(ast, INTERFACE_DEF));
        br();
    }

    @Override protected void printComment(AST ast) {
       System.err.println("printComment() " + ast );
       System.err.println( ast.getText() );
        print(ast.getFirstChild());
        // ?????
    }

    //TODO:retrieve names of dot children, so we can for instance do System.out.println -> println
    @Override protected void printDot(final AST child1, final AST child2) {
        if (child2.getType() == LITERAL_class) {
            print("classOf[");
            print(child1);
            print("]");
        } else {
            print(child1);
            print(".");
            print(child2);
        }
    }


    @Override protected void printImport(final AST ast) {
        print("import ");
        print(ast.getFirstChild());
        printEmptyStatement();
        br();
    }

    /**
     * prints an ident; an ident may have type arguments; this shd be handled
     */
    @Override protected void printIdent(final AST ast) {
        printASTText(ast);
        AST sib = ast.getNextSibling();
	
        if (null != sib && sib.getType() == TYPE_ARGUMENTS)
            printTypeArguments(sib);
    }
    
	
    /**
     * We defer printing package and import statements till we are in the class
     * definition level, so we can easily surround the class with the proper
     * package block, and add imports in the right location
     */
    @Override protected void printDefinition(final AST ast, final AST parent) {
        AST objectBlock = getChild(ast, OBJBLOCK);
        
        List<AST> methods = getChildren(objectBlock, METHOD_DEF);
        List<AST> imethods = new ArrayList<AST>();
        List<AST> omethods = new ArrayList<AST>();
        
        for (AST m : methods)
            if (hasStaticMod(m))
                omethods.add(m);
            else
                imethods.add(m);
        
        List<AST> vars = getChildren(objectBlock, VARIABLE_DEF);
        List<AST> ivars = new ArrayList<AST>();
        List<AST> ovars = new ArrayList<AST>();
        
        for (AST v : vars)
            if (hasStaticMod(v))
                ovars.add(v);
            else
                ivars.add(v);

        // if no class members, but have object members, do not print
        // class/trait
        isClass = ast.getType() == CLASS_DEF; // whether it is a class or trait
        boolean hasClassMembers = 0 < imethods.size() || 0 < ivars.size();
        boolean hasObjectMemebers = 0 < omethods.size() || 0 < ovars.size();
        boolean extOrImp = false;
        try {
            extOrImp = null != getChild(ast, EXTENDS_CLAUSE).getFirstChild() || 
                null != getChild(ast, IMPLEMENTS_CLAUSE).getFirstChild();
        } catch (NullPointerException e) {
            extOrImp = false;
        }
		
        boolean printClass = extOrImp || hasClassMembers || !hasObjectMemebers;
        
        if (printClass)
            printScalaClassOrTrait(ast, objectBlock, imethods, ivars);
        
        if (hasObjectMemebers) {
            if (printClass)
                br(2);
            printScalaObjectDefinition(ast, objectBlock, omethods, ovars);
        }
    }
    
    /**
     * ast.firstChild shd be an ident
     */
    @Override protected void printExtendsClause(final AST ast) {
        if (hasChildren(ast)) {
            AST ident = ast.getFirstChild();
            printExtends(ident);
        }
    }

    /**
     * instead of implements myInterface, write extends myInterface if its the
     * only one. Write with i1 ... with iN if there are several (this is a
     * oversimplified description, it might go wrong in some cases that I won't
     * go into here.)
     */
    @Override protected void printImplementsClause(final AST ast) {
        if (hasChildren(ast)) {
            List<AST> xs = getChildren(ast, IDENT);
            
            if (!extended && xs.size() > 0)
                printExtends(xs.remove(0));
            
            for (AST x : xs) {
                print("with ");
                print(x);
                print(" ");
            }
        }
    }

    @Override protected void printCtorDefinition(final AST ast) {
        getChild(ast, IDENT).setText("this");
        printMethodDefinition(ast);
    }

    // TODO: anonymous functions and function asst.
    // out("// the next method throws the following errors: ");
    // print(getChild(ast, LITERAL_throws));
    @Override protected void printMethodDefinition(final AST ast) {
        List<AST> modifiers = getChildren(getChild(ast, MODIFIERS));
        
        /**
         * ScalaRef: Since Scala has no checked exceptions, Scala methods must
         * be annotated with one or more throws annotations such that Java code
         * can catch exceptions thrown by a Scala method
         */
        print(getChild(ast, LITERAL_throws));
        
        if (0 < modifiers.size())
            for (AST m : modifiers) {
                switch (m.getType()) {
                case LITERAL_public:
                case LITERAL_static:
                case LITERAL_synchronized:
                    break;
                default:
                    print(m);
                    print(" ");
                }
            }
        AST ident = getChild(ast, IDENT);
        AST body = getChild(ast, SLIST);
        
        print("def ");
        print(ident);
        print(getChild(ast, PARAMETERS));
        
        if (!(ast.getType() == CTOR_DEF)) {
            print(":");
            print(getChild(ast, TYPE));
        }
        
        if (isClass) {
            print(" =");
            if (hasModifier(ast, LITERAL_synchronized))
                print(" synchronized");
            if (null == body) {
                print(" {}");
                br();
            } else {
                print(" ");
                print(body);
            }
            br();
        }
    }
    
    @Override protected void printSuperConstructorCall(final AST ast) {
        printConstructorCall(ast);
    }
    
    @Override protected void printConstructorCall(final AST ast) {
        print("this(");
        print(ast.getFirstChild());
        print(")");
    }
    
    @Override protected void printParameters(final AST ast) {
        print("(");
        printExpressionList(ast);
        print(")");
    }
    
    // TODO: final?
    @Override protected void printParamDef(final AST ast) {
        // print(getChild(ast, MODIFIERS));
        print(getChild(ast, IDENT));
        print(":");
        print(getChild(ast, TYPE));
    }

    @Override protected void printVariableParamDef(final AST ast) {
        printParamDef(ast);
        print("*");
    }
    
    /**
     * @param ast  A Java 1.5 ANNOTATION AST node
     */
    @Override protected void printAnnotation(final AST ast) {
        AST ann = ast.getFirstChild();
        String txt = ann.getText();
        
        if (txt.equals("Override")) {
            print("override ");
            return;
        }
        
        print("@" + txt);

        List<AST> vps = getChildren(ast, ANNOTATION_MEMBER_VALUE_PAIR);

        if (vps.size() > 0) {
            print(vps.size() > 1 ? "{" : "(");
            print(vps.get(0));
            for (int i = 1; i < vps.size(); ++i) {
                print(", ");
                print(vps.get(i));
            }
            print(vps.size() > 1 ? "}" : ")");
        } else if (ann.getNextSibling() != null) {
            print("C");
            print(ann.getNextSibling());
            print(")");
        }
	
        br();
    }
    
    /**
     * @param ast FOR_EACH_CLAUSE
     */
    @Override protected void printForEach(final AST ast) {
        // NOTE: for each clause is parameter def and expression
        print("val ");
        print(getChild(getChild(ast, PARAMETER_DEF), IDENT));
        print(" <- ");
        print(getChild(ast, EXPR));
    }

    @Override protected void printAnnotationMemberValuePair(final AST ast) {
        print(ast.getFirstChild());
        print(" = ");
        print(ast.getFirstChild().getNextSibling());
    }

    /**
     * say goodbye to x++, change it to x and to x = x + 1;
     *TODO: check preAssignment
     */
    @Override protected void printPostAssignment(final AST ast, final AST child1) {
        printIncDec(ast, child1);
    }
    
    @Override protected void printIncDec(final AST ast, final AST child1) {
        print(child1);
        print(" = ");
        print(child1);
        print(ast.getText().equals("++") ? " + 1" : " - 1");
    }
    
    @Override protected void printExpressionList(final AST ast) {
        printChildren(ast, ", ");
    }
    
    @Override protected void printExpression(final AST parent, final AST child1) {
        print(child1);
        printSemi(parent);
    }
    
    @Override protected void printStatementList(final AST slist) {
        List<AST> xs = getChildren(slist, ALL);
        startBlock();

        for (AST x : xs) {
            print(x);
            br();
        }

        endBlock();
    }

    /**
     * @param condition EXPR
     * @param thenClause SLIST, RETURN or EXPR
     * @param elseClause SLIST
     */
    @Override protected void printIfStatement(final AST condition, final AST thenClause, final AST elseClause) {
        print("if (");
        print(condition);
        print(") ");
        
        printIndented(thenClause);
        
        if (elseClause != null) {
            print(thenClause.getType() == SLIST ? " else " : "else ");
            printIndented(elseClause);
        }
	
        br();
    }

    // the EXPR to switch on
    @Override protected void printSwitch(final AST ast, final AST expr) {
        print(expr);
        print(" match ");
        print(" ");
        startBlock();
        print(getChildren(ast, CASE_GROUP));
        endBlock();
    }

    // 13. switch statements require more care. First turn case pat: into case pat =>.
    // Now you should take some more involved measures: scala does not need a
    // break, which is convenient, but an unmentioned default case will not be
    // ignored by lead to a runtime error. If your switch does not have a default case,
    // add one case _ => (the right-hand side is empty.)
    @Override protected void printCaseGroup(final AST ast) {
        List<AST> cases = getChildren(ast, LITERAL_case);
        int n = cases.size();
        
        if (n > 0) {
            print("case ");
            print(getChild(cases.get(0), EXPR));
            for (int i = 1; i < n; ++i) {
                print(" | ");
                print(getChild(cases.get(i), EXPR));
            }
            print(" => ");
        } else {
            AST defcase = getChild(ast, LITERAL_default);
            if (null == defcase)
                printDefaultCase(null);
            else
                print(defcase);
        }
		
        List<AST> slist = getChildren(getChild(ast, SLIST), ALL);
        
        indentedSlist(slist);
    }

    private void indentedSlist(List<AST> xs) {
        startIndent();
        for (AST x : xs) {
            print(x);
            br();
        }
        closeIndent();
    }

    // an EXPR
    @Override protected void printCaseExpression(final AST expr) {
        print(expr);
    }
    
    /**
     * @param child1
     *            EXPR
     */
    @Override protected void printDefaultCase(final AST child1) {
        print("case _ => ");
        print(child1);
    }

    /**
     * Change for loop into while loop
     */
    @Override 
        protected void printForLoop(final AST ast) {
        AST foreach = getChild(ast, FOR_EACH_CLAUSE);
        
        AST body;
        
        if (null != foreach)
            body = foreach.getNextSibling();
        else {
            //TODO: pick all children into a list, and remove children that are already rendered
            // whatever is left is the body
            body = getChild(ast, SLIST);
            if (null == body)
                body = getChild(ast, LITERAL_if);
            if (null == body)
                body = getChild(ast, EXPR);
        }

        if (null != foreach) {
            print("for (");
            print(foreach);
            print(") ");
        } else {
            br();
            startBlock();
            print(getChild(ast, FOR_INIT));
            br();
            print("while (");
            print(getChild(ast, FOR_CONDITION));
            print(") ");
        }
        
        if (null == body) {
            print("{}");
            br();
        } else if (null == foreach) {
            List<AST> slist;

            if (body.getType() == SLIST)
                slist = getChildren(body, ALL);
            else {
                slist = new ArrayList<AST>();
                slist.add(body);
            }
            
            startBlock();
            for (AST s : slist) {
                print(s);
                br();
            }
            print(getChild(ast, FOR_ITERATOR));
            endBlock();
        } else
            printIndented(body);
			
        if (null == foreach) {
            br();
            endBlock();
            br();
        }
    }

    /**
     * @param slist
     *            SLIST
     * @param condition
     *            EXPR
     */
    @Override 
        protected void printDoLoop(final AST slist, final AST condition) {
        print("do ");
        print(slist);
        print(" while (");
        print(condition);
        print(");");
    }

    @Override
        protected void printWhileLoop(final AST child1, final AST child2) {
        print("while (");
        print(child1); // the "while" condition: an EXPR
        print(") ");
        print(child2); // an SLIST
    }

    @Override
        protected void printContinueBreak(final AST ast, boolean notCaseGroup) {
        if (ast.getType() == LITERAL_break) {
            if (notCaseGroup)
                print("error(\"break\")");
            return;
        }
        printASTName(ast);
        printEmptyStatement();
    }

    @Override
        protected void printTry(final AST ast, final AST child1) {
        print("try ");
        print(child1); // an SLIST
        printCatchClauses(ast);
        //printFinally()
            
        printFinallyClause(ast);
    }

    private void printFinallyClause(final AST ast) {
        AST child2 = getChild(ast, LITERAL_finally); 
        if (null != child2) {
            print(" finally ");
            print(child2.getFirstChild()); // an SLIST
        }
    }

    private void printCatchClauses(final AST ast) {
        if (null == getChild(ast, LITERAL_catch))
            return;
        print(" catch {");
        printChildren(ast, " ", LITERAL_catch);
        print("}");
    }

    /**
     * @param param PARAMETER_DEF
     * @param slist SLIST
     */
    @Override
        protected void printCatch(final AST param, final AST slist) {
        // TODO: merge with with case print stms
        // print("catch (");
        startIndent();
        print("case "); print(param); print(" => ");
        startIndent();
        printChildren(slist, "\n", ALL);
        closeIndent();
        closeIndent();
    }

    @Override
        protected void printThrow(final AST child1) {
        print("throw ");
        print(child1);
        printEmptyStatement();
    }

    @Override
        protected void printEmptyStatement() {}

    /**
     * 1. about type declaration: T x[] and T[] x both become x: Array[T].
     */
    @Override
        protected void printArrayDeclarator(final AST ast) {
        if (ast == null)
            print("Array");
        else if (ast.getType() == EXPR) {
            print("[");
            print(ast);
            print("]");
        } else {
            print("Array[");
            print(ast);
            print("]");
        }
    }

    /**
     * @param child1 IDENT
     * @param child2 EXPR
     */
    @Override
        protected void printIndexOperator(final AST child1, final AST child2) {
        print(child1);
        print("(");
        print(child2);
        print(")");
    }

    @Override
        protected void printVariableDef(final AST ast, final AST parent) {
        List<AST> modifiers = getChildren(getChild(ast, MODIFIERS));
        
        for (AST m : modifiers)
            if (!(m.getType() == LITERAL_public
                  || m.getType() == LITERAL_static || m.getType() == FINAL)) {
                print(m);
                print(" ");
            }
            
        print(isFinal(ast) ? "val " : "var ");
        print(getChild(ast, IDENT));

        AST type = getChild(ast, TYPE);
        
        if (!(untyped || null == type)) {
            print(":");
            print(type);
        }

        AST assign = getChild(ast, ASSIGN);
        
        if (null == assign)
            print(" = null");
        else
            print(assign);
        
        //printSemi(parent);
    }

    /**	
     * If we have two children, it's of the form "a=0" If just one child, it's
     * of the form "=0" (where the lhs is above this AST).
     * 
     *TODO: fix line breaks
     */
    @Override
        protected void printAssignment(final AST child1, final AST child2) {
        if (child2 != null) {
            print(child1);
            print(" = ");
            print(child2);
            
            //    if (!("(".equals(child2.getText().trim())))
            //   br();
        } else {
            print(" = ");
            print(child1);
            //br();
        }
    }

    @Override
        protected void printArrayInitialization(final AST ast) {
        print("(");
        printExpressionList(ast);
        print(")");
    }

    // nts: TYPE has exactly one child.
    @Override
	protected void printType(final AST ast) {
        AST type = ast.getFirstChild();
        AST typeargs = type.getNextSibling();
        print(type);
        print(typeargs);
    }

    @Override
	protected void printTypeArguments(final List<AST> list) {
        print("[");
        for (AST t : list)
            print(t.getFirstChild());
        print("]");
    }
	
    @Override
	protected void printTypeParameters(final AST ast) {
        print("[");
        printChildren(ast, ",", TYPE_PARAMETER);
        print("]");
    }

    @Override
	protected void printTypeParameter(AST child1, AST child2) {
        print(child1);
        print(" ");
        print(child2);
    }
	
    @Override
	protected void printUpperBoundTypeParameter(AST ast) {
        print("<: ");
        print(ast);
    }
	
	
    @Override
	protected void printModifiers(final AST ast) {
        if (hasChildren(ast)) {
            printChildren(ast, " ");
            print(" ");
        }
    }

    //TODO create LITERAL_if AST node and pass to print
    @Override protected void printTrinaryOp(final AST child1, final AST child2, final AST child3) {
        print("if (");
        print(child1);
        print(") ");
        print(child2);
        print(" else ");

        int t = child3.getType();
        boolean printBrack = PLUS == t || MINUS == t || DIV == t || MOD == t;
            
        if (printBrack) {
            print("(");
            print(child3);
            print(")");
        } else
            print(child3);
    }

    // ast has a list of IDENTs
    @Override
	protected void printThrows(final AST ast) {
        List<AST> ls = getChildren(ast, IDENT);
        for (AST t : ls) {
            print("@throws(classOf[");
            print(t);
            print("])");
            br();
        }
    }

    @Override
	protected void printStar(final AST ast) {
        if (hasChildren(ast))
            printBinaryOperator(ast);
        else
            print("_");
    }

    @Override
	protected void printStaticInit(final AST child1) {
        // print("static ");
        // printInstanceInit(child1);
    }

    @Override
	protected void printTypeCast(final AST child1, final AST child2) {
        print(child2);
        print(".asInstanceOf[");
        print(child1);
        print("] ");
    }

    @Override
	protected void printInstanceInit(final AST child1) {
        startBlock();
        print(child1);
        endBlock();
    }

    /**
     * A return expression return e must occur inside the body of some enclosing named 
     * method or function. The innermost enclosing named method or function in a 
     * source program, f , must have an explicitly declared result type, and the type of e 
     * must conform to it. The return expression evaluates the expression e and returns its 
     * value as the result of f . The evaluation of any statements or expressions following 
     * the return expression is omitted. The type of a return expression is scala.Nothing. 
     */
    @Override
        protected void printReturn(final AST child1, final AST child2) {
        /*if (null == child1)
          print("return");
          else {
          if (null != child2 || out.indent() > 0) //HACK: fix this per the return expressions spec
          print("return ");
          print(child1);
          }
          //printEmptyStatement();*/
        
        if (null == child1)
            print("return");
        else {
            print("return ");
            print(child1);
        }
        
    }

    @Override
	protected void printMethodCall(final AST child1, final AST child2) {
        print(child1);
        print("(");
        print(child2);
        print(")");
    }

    @Override
	protected void printUnary(final AST ast, final AST child1) {
        printASTName(ast);
        printWithParens(ast, child1);
    }

    /**
     * @param child1 IDENT
     * @param child2 can be an ARRAY_DECLARATOR, or EXPR
     * @param child3 an array initializer (ARRAY_INIT); so, in new String[] {...}, the stuff in {} is child3
     */
    @Override
	protected void printNew(final AST child1, final AST child2, final AST child3) {
        //TODO: change this to List(a,b,c).toArray if 3 is blah
        boolean isArray = child2.getType() == ARRAY_DECLARATOR;
        boolean simple = !(isArray || child2.getType() == TYPE_ARGUMENTS);
            
        print("new ");
            
        if (isArray) {
            print("Array[");
            print(child1);
            print("]");
        } else {
            print(child1);
        }
		
        if (simple)
            print("(");
            
        if (!isArray)
            print(child2);

        if (simple)
            print(")");
		
        if (null != child3)
            if (ELIST == child3.getType() && null == child3.getNextSibling())
                print("()");
            else 
                print(child3);
    }

    /**
     * Note: <code>a instanceof b</code> becomes
     * <code>a.isInstanceOf[b]</code>
     */
    @Override protected void printBinaryOperator(final AST ast) {
        boolean b = ast.getType() == LITERAL_instanceof;
            
        printWithParens(ast, ast.getFirstChild());
        print(b ? ".isInstanceOf[" : " " + name(ast) + " ");
        printWithParens(ast, ast.getFirstChild().getNextSibling());
            
        if (b)
            print("]");
    }

    @Override protected void setupTokenNames() {
        if (null != TOKEN_NAMES)
            return;
        super.setupTokenNames();
        TOKEN_NAMES[ABSTRACT] = "abstract";
        TOKEN_NAMES[FINAL] = "final";
        TOKEN_NAMES[LITERAL_package] = "package";
        TOKEN_NAMES[LITERAL_import] = "import";
        TOKEN_NAMES[LITERAL_void] = "Unit";
        TOKEN_NAMES[LITERAL_boolean] = "Boolean";
        TOKEN_NAMES[LITERAL_byte] = "byte";
        TOKEN_NAMES[LITERAL_char] = "char";
        TOKEN_NAMES[LITERAL_short] = "short";
        TOKEN_NAMES[LITERAL_int] = "Int";
        TOKEN_NAMES[LITERAL_float] = "Float";
        TOKEN_NAMES[LITERAL_long] = "Long";
        TOKEN_NAMES[LITERAL_double] = "Double";
        TOKEN_NAMES[LITERAL_private] = "private";
        TOKEN_NAMES[LITERAL_public] = "public";
        TOKEN_NAMES[LITERAL_protected] = "protected";
        TOKEN_NAMES[LITERAL_static] = "error(static)";
        TOKEN_NAMES[LITERAL_transient] = "@transient";
        TOKEN_NAMES[LITERAL_native] = "@native";
        TOKEN_NAMES[LITERAL_threadsafe] = "threadsafe";
        TOKEN_NAMES[LITERAL_synchronized] = "synchronized";
        TOKEN_NAMES[LITERAL_volatile] = "@volatile";
        TOKEN_NAMES[LITERAL_class] = "class";
        TOKEN_NAMES[LITERAL_extends] = "extends";
        TOKEN_NAMES[LITERAL_interface] = "error(interface)";
        TOKEN_NAMES[LITERAL_implements] = "error(implements)";
        TOKEN_NAMES[LITERAL_throws] = "throws";
        TOKEN_NAMES[LITERAL_if] = "if";
        TOKEN_NAMES[LITERAL_else] = "else";
        TOKEN_NAMES[LITERAL_for] = "for";
        TOKEN_NAMES[LITERAL_while] = "while";
        TOKEN_NAMES[LITERAL_do] = "do";
        TOKEN_NAMES[LITERAL_break] = "break";
        TOKEN_NAMES[LITERAL_continue] = "continue";
        TOKEN_NAMES[LITERAL_return] = "return";
        TOKEN_NAMES[LITERAL_switch] = "switch";
        TOKEN_NAMES[LITERAL_throw] = "throw";
        TOKEN_NAMES[LITERAL_case] = "case";
        TOKEN_NAMES[LITERAL_default] = "default";
        TOKEN_NAMES[LITERAL_try] = "try";
        TOKEN_NAMES[LITERAL_finally] = "finally";
        TOKEN_NAMES[LITERAL_catch] = "catch";
        TOKEN_NAMES[LITERAL_instanceof] = "instanceOf";
        TOKEN_NAMES[LITERAL_this] = "this";
        TOKEN_NAMES[LITERAL_super] = "super";
        TOKEN_NAMES[LITERAL_true] = "true";
        TOKEN_NAMES[LITERAL_false] = "false";
        TOKEN_NAMES[LITERAL_null] = "null";
        TOKEN_NAMES[LITERAL_new] = "new";
    }
    
    @Override protected void setupKeywords() {
        KEYWORDS = new HashMap<String, Integer>();
        KEYWORDS.put("def", 1); 
        KEYWORDS.put("forSome", 1); 
        KEYWORDS.put("implicit", 1); 
        KEYWORDS.put("lazy", 1); 
        KEYWORDS.put("match", 1); 
        KEYWORDS.put("object", 1); 
        KEYWORDS.put("override", 1); 
        KEYWORDS.put("requires", 1); 
        KEYWORDS.put("sealed", 1); 
        KEYWORDS.put("trait", 1); 
        KEYWORDS.put("type", 1); 
        KEYWORDS.put("val", 1); 
        KEYWORDS.put("var", 1); 
        KEYWORDS.put("with", 1); 
        KEYWORDS.put("yield", 1);		
    }
    

    private void printExtends(final AST ident) {
        print("extends ");
        extended = true;
        print(ident);
        print(" ");
    }
    
    private void printIndented(final AST ast) {
        startIndent(ast);
        print(ast);
        closeIndent(ast);
    }

    private void printScalaClassOrTrait(final AST ast, final AST obj, 
                                        final List<AST> imethods, final List<AST> ivars) {
        print(isClass ? "class " : "trait ");
        print(getChild(ast, IDENT));
            
        print(getChild(ast, TYPE_PARAMETERS));
            
        print(" ");
        print(getChild(ast, EXTENDS_CLAUSE));
        print(getChild(ast, IMPLEMENTS_CLAUSE));
        startBlock();
        printConstructors(obj);
        print(imethods);
        print(getChildren(obj, INSTANCE_INIT));
        print(ivars);
        printChildren(obj, "\n", CLASS_DEF);
        endBlock();
    }

    private void printConstructors(final AST obj) {
        List<AST> ctors = getChildren(obj, CTOR_DEF);
        int n = ctors.size();
            
        if (0 < n) {
            print("/*");
            br();
		
            print(ctors.get(0));
            for (int i = 1; i < n; ++i) {
                br();
                print(ctors.get(i));
            }
                
            print("*/");
            br();
        }
    }

    private void printScalaObjectDefinition(final AST ast, final AST obj,
                                            final List<AST> omethods, final List<AST> ovars) {
        print("object ");
        print(getChild(ast, IDENT));
        print(" ");
        startBlock();
        print(omethods);
        print(getChildren(obj, STATIC_INIT));
        print(ovars);
        endBlock();
    }

    private boolean hasStaticMod(final AST method) {
        return hasModifier(method, LITERAL_static);
    }

    private boolean isFinal(final AST ast) {
        return hasModifier(ast, FINAL);
    }

    private boolean hasModifier(final AST method, final int t) {
        AST modifiers = getChild(method, MODIFIERS);
            
        for (AST c : getChildren(modifiers))
            if (c.getType() == t)
                return true;
        return false;
    }

    private boolean isClass = false;
    private boolean extended = false;
}
