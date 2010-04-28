package jatran.stub;

public class TryCatchPatterns {
	void foo() {
		try {
			
		} finally {
			
		}
	}
	
	void bar() {
		try {			
		} catch (Exception e)  {			
		}
	}
	
	void baz() {
		try {
			bar();
		} catch (Exception e) {
		} finally {
			foo();
		}
	}
	
	void buzz() {
		try {
			
		} finally {
			baz();
		}
	}
}
