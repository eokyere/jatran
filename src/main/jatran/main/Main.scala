package jatran.main

import java.io._

import scala.io._
import scalax.io._
import RichFile._

import antlr.ASTFactory
import antlr.collections.AST

import jatran.core.ScalaPrinter
import jatran.core.SourcePrinter

import jatran.lexing.JavaLexer
import jatran.lexing.JavaRecognizer

/**
 * @author eokyere
 */
object Main {
  def main(argv:Array[String]) {  
    object Options extends CommandLineParser {
      val input = new StringOption('i', "input", "src file or folder to transform") with AllowAll
      val output = new StringOption('o', "output", "output folder; defaults to jatran-out under current dir") with AllowAll
      val help = new Flag('h', "help", "Show help info") with AllowNone
      
      override def helpHeader = """
          |  jatran v0.2
          |  (c) 2006-2008 Emmanuel Okyere
          |
          |""".stripMargin
    }
    
    val jatran = new Jatran()

    Options.parseOrHelp(argv) { cmd =>
      if(cmd(Options.help)) {
      	 Options.showHelp(System.out)
         return
      }
      
      (cmd(Options.input), cmd(Options.output)) match {
        case (Some(i), Some(o)) =>
          jatran.transform(i, o, false)
        case (Some(i), None) =>
          jatran.transform(i, "jatran-out", false)
        case _ =>
          Options.showHelp(System.out)
      }
    }
  }
}

class Jatran {
  def transform(src:String, out:String, untyped:Boolean) {
    transform(new File(src), out, untyped)
  }
  
  def transform(src:File, out:String, untyped:Boolean) {
    for (f <- src.flatten; if f.name.endsWith(".java") && 5 <= f.name.length) {
        val i = new BufferedReader(new FileReader(f))

        val lexer = new JavaLexer(i)
        lexer.setFilename(f.name)

        val parser = new JavaRecognizer(lexer)
        parser.setFilename(f.name)

        val root = new ASTFactory().create(SourcePrinter.ROOT_ID,"AST ROOT")
        parser.compilationUnit()
        root.setFirstChild(parser.getAST())

        val pkg = packageName(f)
        val folder = new File(out + File.separator + pkg.replace(".", File.separator))
        folder.mkdirs()
        val fname = folder.getAbsolutePath() + File.separator + getClassName(f) + ".scala"
    
        val fl = new File(fname)                        
        if (fl.exists())
          fl.delete()
    
        //TODO: insert a virtual fileoutsteram here for testing
        new ScalaPrinter().print(root, new PrintStream(new FileOutputStream(fname)), untyped)
    }
  }
  
  private def packageName(file:File):String = {
    file.lines.foreach { line =>
      val s = line.trim
      if (s.startsWith("package"))
        return s.substring(7, s.length - 1).trim
    }
    
    ""
  }

  private def getClassName(file:File):String = {
    val len = file.name.lastIndexOf('.')
    file.name.substring(0, len)
  }
}

class RichFile(file: File) {
  def flatten : Iterable[File] = 
    Seq.single(file) ++ children.flatMap(child => new RichFile(child).flatten)

  def name = file.getName()
  def lines = scala.io.Source.fromFile(file).getLines
  
  private def children = new Iterable[File] {
    def elements = if (file.isDirectory) file.listFiles.elements else Iterator.empty
  }
}

object RichFile {
  implicit def toRichFile(file: File) = new RichFile(file)
}

