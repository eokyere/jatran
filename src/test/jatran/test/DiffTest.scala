package jatran.test

//import java.util._
import java.util.{List => JavaList, Comparator}
import junit.framework.TestCase

import junit.framework.Assert._

import org.testng.annotations._
import scalax.io.Implicits._

import org.scalatest.testng.TestNGSuite

import org.incava.util.diff._

class DiffTest extends  TestNGSuite {
  @Test def testStrings1() {
    val a:Array[Object] = List("a", "b", "c", "e", "h", "j", "l", "m", "n", "p").toArray
    val b:Array[Object] = List("b", "c", "d", "e", "f", "j", "k", "l", "m", "r", "s", "t").toArray
    val expected:Array[Difference] = List(new Difference(0, 0, 0, -1), 
                                          new Difference(3, -1, 2, 2), 
                                          new Difference(4, 4, 4, 4), 
                                          new Difference(6, -1, 6, 6), 
                                          new Difference(8, 9, 9, 11)).toArray
    runDiff(a, b, expected)
  }

  @Test def testStrings2() {
    val a:Array[Object] = List("a", "b", "c", "d").toArray
    val b:Array[Object] = List("c", "d").toArray
    val expected:Array[Difference] = List(new Difference(0, 1, 0, -1)).toArray
    runDiff(a, b, expected)
  }

  @Test def testStrings3() {
    val a:Array[Object] = List("a", "b", "c", "d", "x", "y", "z").toArray
    val b:Array[Object] = List("c", "d").toArray
    val expected:Array[Difference] = List(new Difference(0, 1, 0, -1), new Difference(4, 6, 2, -1)).toArray
    runDiff(a, b, expected)
  }

  @Test def testStrings4() {
    val a:Array[Object] = List("a", "b", "c", "d", "e").toArray
    val b:Array[Object] = List("a", "x", "y", "b", "c", "j", "e").toArray
    val expected:Array[Difference] = List(new Difference(1, -1, 1, 2), new Difference(3, 3, 5, 5)).toArray
    runDiff(a, b, expected)
  }

  @Test def testInteger() {
    val a:Array[Object] = List(new Integer(1), new Integer(2), new Integer(3)).toArray
    val b:Array[Object] = List(new Integer(2), new Integer(3)).toArray
    val expected:Array[Difference] = List(new Difference(0, 0, 0, -1)).toArray
    runDiff(a, b, expected)
  }

  /*
  @Test def testComparator() {
    val a:Array[Object] = List(new NoncomparableObject("a"), new NoncomparableObject("b"), new NoncomparableObject("c"), new NoncomparableObject("g"), new NoncomparableObject("h1")).toArray
    val b:Array[Object] = List(new NoncomparableObject("b"), new NoncomparableObject("c"), new NoncomparableObject("d"), new NoncomparableObject("e"), new NoncomparableObject("f"), new NoncomparableObject("g"), new NoncomparableObject("h2")).toArray
    val expected:Array[Difference] = List(new Difference(0, 0, 0, -1), new Difference(3, -1, 2, 4), new Difference(4, 4, 6, 6)).toArray
    val comparator:Comparator = new Comparator() 

    runDiff(new Diff(a, b, comparator), expected)
  }
  */

  @Test def testComparableObject() {
    val a:Array[Object] = List(new ComparableObject("a"), new ComparableObject("b"), new ComparableObject("c"), new ComparableObject("g"), new ComparableObject("h1")).toArray
    val b:Array[Object] = List(new ComparableObject("b"), new ComparableObject("c"), new ComparableObject("d"), new ComparableObject("e"), new ComparableObject("f"), new ComparableObject("g"), new ComparableObject("h2")).toArray
    val expected:Array[Difference] = List(new Difference(0, 0, 0, -1), new Difference(3, -1, 2, 4), new Difference(4, 4, 6, 6)).toArray
    runDiff(a, b, expected)
  }

  @Test def testLongArray() {
    val a:Array[Object] = List("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l").toArray
    val b:Array[Object] = List("a", "b", "p", "q", "r", "s", "t", "c", "d", "e", "f", "g", "h", "i", "j", "u", "l").toArray
    val expected:Array[Difference] = List(new Difference(2, -1, 2, 6), new Difference(10, 10, 15, 15)).toArray
    runDiff(a, b, expected)
  }

  @Test def testRepeated() {
    val a:Array[Object] = List("a", "a", "a", "a", "b", "b", "b", "a", "a", "a", "a", "b", "b", "b", "a", "a", "a", "a", "b", "b", "b", "a", "a", "a", "a", "b", "b", "b").toArray
    val b:Array[Object] = List("a", "a", "a", "a", "b", "b", "b", "a", "b", "b", "b", "a", "a", "a", "a").toArray
    val expected:Array[Difference] = List(new Difference(8, 10, 8, -1), new Difference(18, 27, 15, -1)).toArray
    runDiff(a, b, expected)
  }

  @Test def testReallyBig() {
    val a:Array[Object] = List("A", "B", "C", "D", "E", "F", "G", "A", "H", "I", "J", "D", "K", "L", "C", "G", "M", "H", "N", "J", "I", "K", "O", "C", "G", "M", "P", "Q", "J", "R", "K", "S", "C", "C", "F", "G", "D", "T", "N", "G", "M", "U", "V", "J", "Q", "K", "W", "C", "G", "M", "X", "C", "V", "K", "Y", "C", "G", "G", "A", "Z", "AA", "J", "C", "Z", "G", "V", "K", "BB", "C", "G", "M", "CC", "DD", "J", "EE", "K", "FF", "C", "AA", "G", "M", "GG", "K", "HH", "C", "DD", "G", "M", "II", "II", "II").toArray
    val b:Array[Object] = List("A", "B", "C", "JJ", "G", "A", "II", "KK", "A", "B", "C", "D", "E", "F", "G", "A", "H", "I", "J", "D", "K", "L", "C", "G", "M", "H", "N", "J", "I", "K", "O", "C", "G", "M", "P", "Q", "J", "R", "K", "S", "C", "C", "F", "G", "D", "T", "N", "G", "M", "U", "V", "J", "Q", "K", "W", "C", "G", "M", "X", "C", "V", "K", "Y", "C", "G", "G", "A", "Z", "AA", "J", "C", "Z", "G", "V", "K", "BB", "C", "G", "M", "CC", "DD", "J", "EE", "K", "FF", "C", "AA", "G", "M", "GG", "K", "HH", "C", "DD", "G", "M", "II", "II", "II", "II").toArray
    val expected:Array[Difference] = List(new Difference(3, -1, 3, 10), new Difference(88, -1, 96, 96)).toArray
    runDiff(a, b, expected)
  }

  def misleading_diffs_testFromZipDiff() {
    val a:Array[Object] = List("{", "ZipEntry", "e", "=", "entry", ";", "if", "(", "e", "!=", "null", ")", "{", "switch", "(", "e", ".", "method", ")", "{", "case", "DEFLATED", ":", "if", "(", "(", "e", ".", "flag", "&", "8", ")", "==", "0", ")", "{", "if", "(", "e", ".", "size", "!=", "def", ".", "getTotalIn", "(", ")", ")", "{", "throw", "new", "ZipException", "(", "\"invalid entry size (expected \"", "+", "e", ".", "size", "+", "\" but got \"", "+", "def", ".", "getTotalIn", "(", ")", "+", "\" bytes)\"", ")", ";", "}", "if", "(", "e", ".", "csize", "!=", "def", ".", "getTotalOut", "(", ")", ")", "{", "throw", "new", "ZipException", "(", "\"invalid entry compressed size (expected \"", "+", "e", ".", "csize", "+", "\" but got \"", "+", "def", ".", "getTotalOut", "(", ")", "+", "\" bytes)\"", ")", ";", "}", "if", "(", "e", ".", "crc", "!=", "crc", ".", "getValue", "(", ")", ")", "{", "throw", "new", "ZipException", "(", "\"invalid entry CRC-32 (expected 0x\"", "+", "Long", ".", "toHexString", "(", "e", ".", "crc", ")", "+", "\" but got 0x\"", "+", "Long", ".", "toHexString", "(", "crc", ".", "getValue", "(", ")", ")", "+", "\")\"", ")", ";", "}", "}", "else", "{", "e", ".", "size", "=", "def", ".", "getTotalIn", "(", ")", ";", "e", ".", "csize", "=", "def", ".", "getTotalOut", "(", ")", ";", "e", ".", "crc", "=", "crc", ".", "getValue", "(", ")", ";", "writeEXT", "(", "e", ")", ";", "}", "def", ".", "reset", "(", ")", ";", "written", "+=", "e", ".", "csize", ";", "break", ";", "}", "}", "}").toArray
    val b:Array[Object] = List("{", "ZipEntry", "e", "=", "entry", ";", "if", "(", "e", "!=", "null", ")", "{", "switch", "(", "e", ".", "method", ")", "{", "case", "DEFLATED", ":", "if", "(", "(", "e", ".", "flag", "&", "8", ")", "==", "0", ")", "{", "if", "(", "e", ".", "size", "!=", "def", ".", "getBytesRead", "(", ")", ")", "{", "throw", "new", "ZipException", "(", "\"invalid entry size (expected \"", "+", "e", ".", "size", "+", "\" but got \"", "+", "def", ".", "getBytesRead", "(", ")", "+", "\" bytes)\"", ")", ";", "}", "if", "(", "e", ".", "csize", "!=", "def", ".", "getBytesWritten", "(", ")", ")", "{", "throw", "new", "ZipException", "(", "\"invalid entry compressed size (expected \"", "+", "e", ".", "csize", "+", "\" but got \"", "+", "def", ".", "getBytesWritten", "(", ")", "+", "\" bytes)\"", ")", ";", "}", "if", "(", "e", ".", "crc", "!=", "crc", ".", "getValue", "(", ")", ")", "{", "throw", "new", "ZipException", "(", "\"invalid entry CRC-32 (expected 0x\"", "+", "Long", ".", "toHexString", "(", "e", ".", "crc", ")", "+", "\" but got 0x\"", "+", "Long", ".", "toHexString", "(", "crc", ".", "getValue", "(", ")", ")", "+", "\")\"", ")", ";", "}", "}", "else", "{", "e", ".", "size", "=", "def", ".", "getBytesRead", "(", ")", ";", "e", ".", "csize", "=", "def", ".", "getBytesWritten", "(", ")", ";", "e", ".", "crc", "=", "crc", ".", "getValue", "(", ")", ";", "writeEXT", "(", "e", ")", ";", "}", "def", ".", "reset", "(", ")", ";", "written", "+=", "e", ".", "csize", ";", "break", ";", "}", "}", "}").toArray
    val expected:Array[Difference] = List(new Difference(3, -1, 3, 10), new Difference(88, -1, 96, 96)).toArray
    runDiff(a, b, expected)
  }

  @Test def testBlanks() {
    val a:Array[Object] = List("same", "same", "same", "", "same", "del", "", "del").toArray
    val b:Array[Object] = List("ins", "", "same", "same", "same", "", "same").toArray
    val expected:Array[Difference] = List(new Difference(0, -1, 0, 1), new Difference(5, 7, 7, -1)).toArray
    runDiff(a, b, expected)
  }

  protected def assertLCS(a:Array[Object], b:Array[Object], expected:Array[Integer]) {
    val diff:Diff = new Diff(a, b)

    val lcses:Array[Integer] = diff.getLongestCommonSubsequences()
 
    assertEquals("length of output", expected.length, lcses.length)
    
    {
      var ei:int = 0

      while (ei < expected.length) {
        val exp:Integer = expected(ei)
        val lcs:Integer = lcses(ei)
        assertEquals("expected[" + ei + "]", exp, lcs)
        ei = ei + 1
      }
    }
  }

  protected def runDiff(a:Array[Object], b:Array[Object], expected:Array[Difference]) {
    val diff:Diff = new Diff(a, b)
    runDiff(diff, expected)
  }

  protected def runDiff(diff:Diff, expected:Array[Difference]) {
    val diffOut = diff.diff()

    assertEquals("length of output", expected.length, diffOut.size())
    
    {
      var ei:int = 0

      while (ei < expected.length) {
        val d = diffOut.get(ei)
        val exp:Difference = expected(ei)
        assertEquals("expected[" + ei + "]", exp, d)
        ei = ei + 1
      }
    }
  }

  case class NoncomparableObject(x:String) {
    override def equals(obj:Any):boolean = {
      compareTo(obj) == 0
    }

    def compareTo(obj:Any):int = {
      if (obj.isInstanceOf[NoncomparableObject]) {
        val other:NoncomparableObject = obj.asInstanceOf[NoncomparableObject] 

        x.compareTo(other.x)
      } else {
        -1
      }
    }

    override def toString():String = {
      getClass().getName() + " \"" + x + "\""
    }
  }
  
  case class ComparableObject(x:String) extends Comparable[Any] {
    override def equals(obj:Any):boolean = {
      compareTo(obj) == 0
    }

    def compareTo(obj:Any):int = {
      if (obj.isInstanceOf[ComparableObject]) {
        val other:ComparableObject = obj.asInstanceOf[ComparableObject] 

        x.compareTo(other.x)
      } else {
        -1
      }
    }

    override def toString():String = {
      getClass().getName() + " \"" + x + "\""
    }
  }
}
