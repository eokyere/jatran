package jatran.stub

class ReturnExpressions {
  def shouldPrintReturns(start:Int, n:Int):Int = {

    {
      var i:Int = start
      while (i < n) {
        if (i / 5 > 1)
          return i
        i = i + 1
      }
      
    }
      
    return -1
  }

  def shouldNotPrintReturns(bool:Boolean):Boolean = {
    return bool
  }
}
