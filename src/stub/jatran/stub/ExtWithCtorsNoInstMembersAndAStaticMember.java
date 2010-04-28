package jatran.stub;

public class ExtWithCtorsNoInstMembersAndAStaticMember extends RuntimeException
{
	private static final long serialVersionUID = 1084302907754518421L;

	public ExtWithCtorsNoInstMembersAndAStaticMember(String message)
	{
		super(message);
	}

	public ExtWithCtorsNoInstMembersAndAStaticMember(String message, Throwable cause)
	{
		super(message, cause);
	}

	public ExtWithCtorsNoInstMembersAndAStaticMember(Throwable cause){
		super(cause);
	}
}
