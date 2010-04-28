package jatran.stub

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.util.ArrayList
import java.util.Iterator
import java.util.List

import org.incava.util.diff._

class FileDiff {
  /*
  def this(fromFile:String, toFile:String) = {
    this(new File(fromFile), new File(toFile))
  }

  def this(f1:File, f2:File) = {
    var aLines:Array[String] = read(f1)
    var bLines:Array[String] = read(f2)
    var diffs:List[Difference] = new Diff(aLines, bLines).diff()
    var it:Iterator[Difference] = diffs.iterator()

    while (it.hasNext()) {
      var diff:Difference = it.next().asInstanceOf[Difference]
      var delStart:Int = diff.getDeletedStart()
      var delEnd:Int = diff.getDeletedEnd()
      var addStart:Int = diff.getAddedStart()
      var addEnd:Int = diff.getAddedEnd()
      var from:String = toString(delStart, delEnd)
      var to:String = toString(addStart, addEnd)
      var __kwd_type:String = if (delEnd != Difference.NONE && addEnd != Difference.NONE) "c" else if (delEnd == Difference.NONE) "a" else "d"

      System.out.println(from + __kwd_type + to)
      
      if (delEnd != Difference.NONE) {
        printLines(delStart, delEnd, "<", aLines)
        if (addEnd != Difference.NONE) {
          System.out.println("---")
        }
      }
      if (addEnd != Difference.NONE) {
        printLines(addStart, addEnd, ">", bLines)
      }
    }
  }
  */
   
  protected def printLines(start:Int, end:Int, ind:String, lines:Array[String]):Unit = {
    
    {
      var lnum:Int = start
      while (lnum <= end) {
        System.out.println(ind + " " + lines(lnum))
        lnum = lnum + 1
      }
    }

  }

  protected def toString(start:Int, end:Int):String = {
    var buf:StringBuffer = new StringBuffer()
    
    buf.append(if (end == Difference.NONE) start else (1 + start))

    if (end != Difference.NONE && start != end) {
      buf.append(",").append(1 + end)
    }

    return buf.toString()
  }

  private def read(file:File):Array[String] = {
    try {
      var br:BufferedReader = new BufferedReader(new FileReader(file))
      var contents:List[String] = new ArrayList[String]()
      var in:String = null
      while ((in = br.readLine()) != null) {
        contents.add(in)
      }
      return contents.toArray(new Array[String]())
    } catch {
      case e:Exception =>
        System.err.println("error reading " + file.getName() + ": " + e)
        System.exit(1)
        return null
    }
  }
}

object FileDiff {
  def main(args:Array[String]):Unit = {
    if (args.length == 2) {
      new FileDiff(args(0), args(1))
    } else {
      System.err.println("usage: org.incava.diffj.FileDiff from-file to-file")
    }
  }
}
