package jatran.main

/**
 * @author eokyere
 */
object Tester {
  
  val jatran = new Jatran()
  
  def main(argv:Array[String]) {  
    //val rife = "/Users/eokyere/Desktop/rife-source-1.6.2-snapshot-20080428"
    //parse(rife + "/src", "rife-out", false)
    
    //parse("/Users/eokyere/Desktop/java-diff-1.0.5", "tmp/diff-out", false)
    
    //"/Users/eokyere/KonoLabs/pebble/src"
    
    val src = "/Users/eokyere/Labs/atleap/application/src"
    val out = "/Users/eokyere/KonoLabs/atleap-scala-new"
    
    val x = "/Users/eokyere/Desktop/today/libraries-tmp/libraries/LiftUpload/ajax-upload2/src/"
    val xout = "/Users/eokyere/Desktop/upload-scala"
    
    //jatran.transform(x, xout, true)
    jatran.transform(x, xout + "1", false)
    //val src = "/Users/eokyere/KonoLabs/currentcms/src"
    //val src = "/Users/eokyere/Labs/jatran2/src/stub/jatran/stub/FooUpperBoundFTP.java"
    
    
  
    //transform_elsie()
    println("done")
  }
  
  private def transform_rife() = {
    val proj = "/Users/eokyere/KonoLabs/rife-source-1.6.2-snapshot-20080428"
    val src = proj + "/src"
    val out = proj + "/scala"
    
    jatran.transform(src + "/framework", out + "/framework", false)
    jatran.transform(src + "/unittests", out + "/unittests", false)
    
  }
  
  private def transform_graffito() = {
    val proj = "/Users/eokyere/KonoLabs/graffito"
    val out = proj + "/scala"
    
    jatran.transform(proj + "/api/src/java", out + "/api/src", false)
  }
  
  private def transform_atleap() = {
    val atleap = "/Users/eokyere/KonoLabs/atleap/application"
    val out = atleap + "/scala"
    
    List("common", "dao", "service", "web").foreach {fname =>
      jatran.transform(atleap + "/src/" + fname, out + "/src/" + fname, false)
    }
    
  }

  private def transform_elsie() = {
    val wiki = "/Users/eokyere/KonoLabs/elsie-wiki"
    val out = wiki + "/scala"
    
    List("wiki-api", "actions", "servlet", "page-filesystem-impl", 
         "user-filesystem-impl", "assemble-impl", "parser-impl").foreach {fname =>
      jatran.transform(wiki + "/elsie-" + fname + "/src/main/java", out + "/src/main/" + fname, false)
    }
    
  }
}


