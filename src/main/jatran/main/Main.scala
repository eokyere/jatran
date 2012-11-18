package jatran.main

import java.io._
import scala.io._
import antlr.ASTFactory
import antlr.collections.AST
import jatran.core.ScalaPrinter
import jatran.core.SourcePrinter
import jatran.lexing.JavaLexer
import jatran.lexing.JavaRecognizer

/**
 * @author eokyere, jmvanel
 */
object Main extends App {
	val helpHeader = """
			|  jatran v0.2
			|  (c) 2006-2008 Emmanuel Okyere
			|
			|""".stripMargin
			val usage = helpHeader + """
			--input\tsrc file or folder to transform
			--output\toutput folder; defaults to jatran-out under current dir
			--help\tShow help info
			"""  

			override def main(args:Array[String]) {  
		if (args.length == 0) { println(usage); exit }
		val options = nextOption(Map(), args.toList)
				val output = options.getOrElse( Symbol("output"), "jatran-out") .asInstanceOf[String]
						val input = options.get ( Symbol("input")) match {
						case Some(string) => string.toString
						case None => "" }

		val jatran = new Jatran()
		jatran.transform(input, output, false)
		println(  "Done translated " + input + " into " + output )
	}

	type OptionMap = Map[Symbol, Any]
			/** Recursively parse the arguments provided in remainingArguments
			 *  adding them to the parsedArguments map and returning the
			 *   completed map when done. */
			def nextOption(parsedArguments: OptionMap,
					remainingArguments: List[String]): OptionMap = {
			// Does a string look like it could be an option?
			def isOption(s: String) = s.startsWith("--")
					// Match the remaining arguments.
					remainingArguments match {

				// Nothing left so just return the parsed arguments
					case Nil => parsedArguments

							/* Option defining the input
							 * Use the value after the --input option as the input file or dir 
							 * and continue parsing with the remainder of the list. */
					case "--input" :: value :: tail =>
					nextOption(parsedArguments ++ Map(Symbol("input") -> value.toString() ),  tail)

					// The output directory. 
					case "--output" :: possibleOption :: tail =>
					nextOption(parsedArguments ++ Map(Symbol("output") -> possibleOption.toString() ),  tail)


					// output directory. This matches the last element in the list if it
					// doesn't look like an option. As we know there is nothing
					// left in the list use Nil for the remainingArguments passed
					// to the next iteration.
					case dir :: Nil
					if !isOption(dir) =>
					nextOption(parsedArguments ++ Map(Symbol("input") -> dir), Nil)

					case "--help" :: Nil =>
					println( usage  )
					exit(0)

					// Nothing else matched so this must be an unknown option.
					case unknownOption :: tail =>
					error("Unknown option " + unknownOption + usage  )
					exit(1)
			}
	}
}