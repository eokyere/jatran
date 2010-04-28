package jatran.test

import java.io.File
import junitx.framework.FileAssert.{assertBinaryEquals => be}
import org.testng.annotations._
import scalax.io.Implicits._

import org.scalatest.testng.TestNGSuite

import jatran.main.Jatran

import org.incava.util.diff._

class ScalaPrinterTest extends  TestNGSuite {
  val stub = new File("src/stub/jatran/stub")
  val gstub =  new File("tmp/generated/jatran/stub")
  
  @BeforeSuite 
  def generateStubs {
    val test = new File("src/stub")
    val jatran = new Jatran()

    jatran.transform(test, "tmp/generated", false)
  }
  
  
  @Test def mynewTest {
    val t = true
    assert(t)
  }
  
  @Test def fooIsAFile {
    test("Foo")
  }

  @Test def staticMembersAreChangedToMembersOfCompanionObject {
    test("StaticMembersToCompanionObject")
  }

  @Test def forLoopsChangedToWhileLoopEquivalents {
    test("ForLoopsChangeToWhileLoops")
  }
  
  @Test def testReturnExpressions {
    test("ReturnExpressions")
  }

  @Test def helloWorldApplication {
    test("HelloWorldApplication")
  }
  
  @Test def extensionWithCtorsNoInstMembersAndAStaticMember {
    test("ExtWithCtorsNoInstMembersAndAStaticMember")
  }
  
  @Test def testDiffFileClass {
    test("FileDiff")
  }
  
  @Test def literalClassToClassOf {
    test("LiteralClassToClassOf")
  }
  
  @Test def upperBoundFormalTypeParameter {
    test("FooUpperBoundFTP")
  }
  
  @Test def multipleImplementedInterfaces {
    test("FooImplMultipleInterfaces")
  }
  
  @Test def aFairlyComplexClassImplementingAStrutsAction {
    test("AStrutsAction")
  }
  
  @Test def tryCatchFinallyTransformations {
    test("TryCatchPatterns")
  }
  
  @Test def whenToPrintReturnStatement {
    test("ReturnStatementPatterns")
  }


  @Test def testVariableParameterDef {
    test("VariableParameterDef")
  }

  
  def test(clz:String) {
    val name = clz + ".scala"
    val f = stub/name
    val o = gstub/name
    
    assert(f.isFile, f + " was not found")
    assert(o.isFile, o + " was not found")
    
    val diff = new FileDiff(f, o, false) //diff the files and discard newlines
    assert(0 == diff.diffs.size())
  }
}
