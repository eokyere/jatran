package jatran.stub

class TryCatchPatterns {
  def foo():Unit = {
    try {
    } finally {
    }
  }

  def bar():Unit = {
    try {
    } catch { 
      case e:Exception => 
    }
  }
  
  def baz():Unit = {
    try {
      bar()
    } catch {
      case e:Exception =>
    } finally {
      foo()
    }
  }
	
  def buzz():Unit = {
    try {
    } finally {
      baz()
    }
  }
  
}
