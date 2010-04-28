package jatran.stub

object VariableParameterDef {
  def main(args:Array[String]):Unit = {
    System.out.println(sum(3, 44))
    System.out.println(sum(4, 4, 5, 45, 45))
  }
    
  def sum(values:Int*):Int = {
    var res:Int = null
    for (val i <- values) {
      res += i
    }
    
    return res
  } 
}

