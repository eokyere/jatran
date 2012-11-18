package jatran.main

import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import java.io.PrintStream

import scala.collection.Seq.apply

import antlr.ASTFactory
import jatran.core.ScalaPrinter
import jatran.core.SourcePrinter
import jatran.lexing.JavaLexer
import jatran.lexing.JavaRecognizer
import jatran.lexing.JavaTokenTypes
import antlr.TokenStreamHiddenTokenFilter

import RichFile._

class Jatran {
	def transform(src:String, out:String, untyped:Boolean) {
		transform(new File(src), out, untyped)
	}

	 def transform(src:File, out:String, untyped:Boolean) {
    for (f <- src.flatten; if f.name.endsWith(".java") && 5 <= f.name.length) {
        val i = new BufferedReader(new FileReader(f))

        val lexer = new JavaLexer(i)
        lexer.setFilename(f.name)

        // jmv - cf http://www.antlr.org/article/whitespace/index.html

        // use the special token objects
        lexer.setTokenObjectClass("antlr.CommonHiddenStreamToken");
        // create the stream filter; hide WS and SL_COMMENT
        val filter = new TokenStreamHiddenTokenFilter(lexer);
        filter.hide( JavaTokenTypes.ML_COMMENT );
        filter.hide( JavaTokenTypes.SL_COMMENT );
  
//        val parser = new JavaRecognizer(lexer)
        val parser = new JavaRecognizer(filter)
        parser.setFilename(f.name)
        // create trees that copy hidden tokens into tree also
        parser.setASTNodeClass("antlr.CommonASTWithHiddenTokens");

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
    
        //TODO: insert a virtual fileoutstream here for testing
        val scalaPrinter = new ScalaPrinter()
        scalaPrinter.setFilter(filter)
        scalaPrinter.print(root, new PrintStream(new FileOutputStream(fname)), untyped)
//        scalaPrinter.print( parser.getAST(), new PrintStream(new FileOutputStream(fname)), untyped)
    }
  }
	 
	def transform_old(src:File, out:String, untyped:Boolean) {
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
			Seq(file) ++ children.flatMap(child => new RichFile(child).flatten)

			def name = file.getName()
			def lines = scala.io.Source.fromFile(file).getLines

			private def children = new Iterable[File] {
	def iterator = if (file.isDirectory) file.listFiles.iterator else 
		Iterator.empty
}
}

object RichFile {
	implicit def toRichFile(file: File): RichFile = new RichFile(file)
}