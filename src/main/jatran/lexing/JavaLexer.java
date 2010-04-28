package jatran.lexing;

// $ANTLR 2.7.7 (20060906): "java15.g" -> "JavaLexer.java"$

import java.io.InputStream;
import java.io.Reader;
import java.util.Hashtable;

import antlr.ANTLRHashString;
import antlr.ByteBuffer;
import antlr.CharBuffer;
import antlr.CharStreamException;
import antlr.CharStreamIOException;
import antlr.InputBuffer;
import antlr.LexerSharedInputState;
import antlr.NoViableAltForCharException;
import antlr.RecognitionException;
import antlr.Token;
import antlr.TokenStream;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.TokenStreamRecognitionException;
import antlr.collections.impl.BitSet;

public class JavaLexer extends antlr.CharScanner implements JavaTokenTypes, TokenStream
 {

	/** flag for enabling the "assert" keyword */
	private boolean assertEnabled = true;
	/** flag for enabling the "enum" keyword */
	private boolean enumEnabled = true;

	/** Enable the "assert" keyword */
	public void enableAssert(final boolean shouldEnable) { assertEnabled = shouldEnable; }
	/** Query the "assert" keyword state */
	public boolean isAssertEnabled() { return assertEnabled; }
	/** Enable the "enum" keyword */
	public void enableEnum(final boolean shouldEnable) { enumEnabled = shouldEnable; }
	/** Query the "enum" keyword state */
	public boolean isEnumEnabled() { return enumEnabled; }
public JavaLexer(final InputStream in) {
	this(new ByteBuffer(in));
}
public JavaLexer(final Reader in) {
	this(new CharBuffer(in));
}
public JavaLexer(final InputBuffer ib) {
	this(new LexerSharedInputState(ib));
}
public JavaLexer(final LexerSharedInputState state) {
	super(state);
	caseSensitiveLiterals = true;
	setCaseSensitive(true);
	literals = new Hashtable();
	literals.put(new ANTLRHashString("byte", this), new Integer(80));
	literals.put(new ANTLRHashString("public", this), new Integer(89));
	literals.put(new ANTLRHashString("case", this), new Integer(123));
	literals.put(new ANTLRHashString("short", this), new Integer(82));
	literals.put(new ANTLRHashString("break", this), new Integer(116));
	literals.put(new ANTLRHashString("while", this), new Integer(114));
	literals.put(new ANTLRHashString("new", this), new Integer(159));
	literals.put(new ANTLRHashString("instanceof", this), new Integer(146));
	literals.put(new ANTLRHashString("implements", this), new Integer(107));
	literals.put(new ANTLRHashString("synchronized", this), new Integer(94));
	literals.put(new ANTLRHashString("float", this), new Integer(84));
	literals.put(new ANTLRHashString("package", this), new Integer(62));
	literals.put(new ANTLRHashString("return", this), new Integer(118));
	literals.put(new ANTLRHashString("throw", this), new Integer(120));
	literals.put(new ANTLRHashString("null", this), new Integer(158));
	literals.put(new ANTLRHashString("threadsafe", this), new Integer(93));
	literals.put(new ANTLRHashString("protected", this), new Integer(90));
	literals.put(new ANTLRHashString("class", this), new Integer(102));
	literals.put(new ANTLRHashString("throws", this), new Integer(109));
	literals.put(new ANTLRHashString("do", this), new Integer(115));
	literals.put(new ANTLRHashString("strictfp", this), new Integer(41));
	literals.put(new ANTLRHashString("super", this), new Integer(72));
	literals.put(new ANTLRHashString("transient", this), new Integer(91));
	literals.put(new ANTLRHashString("native", this), new Integer(92));
	literals.put(new ANTLRHashString("interface", this), new Integer(103));
	literals.put(new ANTLRHashString("final", this), new Integer(39));
	literals.put(new ANTLRHashString("if", this), new Integer(112));
	literals.put(new ANTLRHashString("double", this), new Integer(86));
	literals.put(new ANTLRHashString("volatile", this), new Integer(95));
	literals.put(new ANTLRHashString("assert", this), new Integer(121));
	literals.put(new ANTLRHashString("catch", this), new Integer(126));
	literals.put(new ANTLRHashString("try", this), new Integer(124));
	literals.put(new ANTLRHashString("enum", this), new Integer(104));
	literals.put(new ANTLRHashString("int", this), new Integer(83));
	literals.put(new ANTLRHashString("for", this), new Integer(122));
	literals.put(new ANTLRHashString("extends", this), new Integer(71));
	literals.put(new ANTLRHashString("boolean", this), new Integer(79));
	literals.put(new ANTLRHashString("char", this), new Integer(81));
	literals.put(new ANTLRHashString("private", this), new Integer(88));
	literals.put(new ANTLRHashString("default", this), new Integer(106));
	literals.put(new ANTLRHashString("false", this), new Integer(157));
	literals.put(new ANTLRHashString("this", this), new Integer(108));
	literals.put(new ANTLRHashString("static", this), new Integer(65));
	literals.put(new ANTLRHashString("abstract", this), new Integer(40));
	literals.put(new ANTLRHashString("continue", this), new Integer(117));
	literals.put(new ANTLRHashString("finally", this), new Integer(125));
	literals.put(new ANTLRHashString("else", this), new Integer(113));
	literals.put(new ANTLRHashString("import", this), new Integer(64));
	literals.put(new ANTLRHashString("void", this), new Integer(78));
	literals.put(new ANTLRHashString("switch", this), new Integer(119));
	literals.put(new ANTLRHashString("true", this), new Integer(156));
	literals.put(new ANTLRHashString("long", this), new Integer(85));
}

public Token nextToken() throws TokenStreamException {
	tryAgain:
	for (;;) {
		int _ttype = Token.INVALID_TYPE;
		resetText();
		try {   // for char stream error handling
			try {   // for lexical error handling
				switch ( LA(1)) {
				case '?':
				{
					mQUESTION(true);
					break;
				}
				case '(':
				{
					mLPAREN(true);
					break;
				}
				case ')':
				{
					mRPAREN(true);
					break;
				}
				case '[':
				{
					mLBRACK(true);
					break;
				}
				case ']':
				{
					mRBRACK(true);
					break;
				}
				case '{':
				{
					mLCURLY(true);
					break;
				}
				case '}':
				{
					mRCURLY(true);
					break;
				}
				case ':':
				{
					mCOLON(true);
					break;
				}
				case ',':
				{
					mCOMMA(true);
					break;
				}
				case '~':
				{
					mBNOT(true);
					break;
				}
				case ';':
				{
					mSEMI(true);
					break;
				}
				case '\t':  case '\n':  case '\u000c':  case '\r':
				case ' ':
				{
					mWS(true);
					break;
				}
				case '\'':
				{
					mCHAR_LITERAL(true);
					break;
				}
				case '"':
				{
					mSTRING_LITERAL(true);
					break;
				}
				case '$':  case 'A':  case 'B':  case 'C':
				case 'D':  case 'E':  case 'F':  case 'G':
				case 'H':  case 'I':  case 'J':  case 'K':
				case 'L':  case 'M':  case 'N':  case 'O':
				case 'P':  case 'Q':  case 'R':  case 'S':
				case 'T':  case 'U':  case 'V':  case 'W':
				case 'X':  case 'Y':  case 'Z':  case '_':
				case 'a':  case 'b':  case 'c':  case 'd':
				case 'e':  case 'f':  case 'g':  case 'h':
				case 'i':  case 'j':  case 'k':  case 'l':
				case 'm':  case 'n':  case 'o':  case 'p':
				case 'q':  case 'r':  case 's':  case 't':
				case 'u':  case 'v':  case 'w':  case 'x':
				case 'y':  case 'z':
				{
					mIDENT(true);
					break;
				}
				case '.':  case '0':  case '1':  case '2':
				case '3':  case '4':  case '5':  case '6':
				case '7':  case '8':  case '9':
				{
					mNUM_INT(true);
					break;
				}
				case '@':
				{
					mAT(true);
					break;
				}
				default:
					if (LA(1)=='>' && LA(2)=='>' && LA(3)=='>' && LA(4)=='=') {
						mBSR_ASSIGN(true);
					}
					else if (LA(1)=='>' && LA(2)=='>' && LA(3)=='=') {
						mSR_ASSIGN(true);
					}
					else if (LA(1)=='>' && LA(2)=='>' && LA(3)=='>' && true) {
						mBSR(true);
					}
					else if (LA(1)=='<' && LA(2)=='<' && LA(3)=='=') {
						mSL_ASSIGN(true);
					}
					else if (LA(1)=='=' && LA(2)=='=') {
						mEQUAL(true);
					}
					else if (LA(1)=='!' && LA(2)=='=') {
						mNOT_EQUAL(true);
					}
					else if (LA(1)=='/' && LA(2)=='=') {
						mDIV_ASSIGN(true);
					}
					else if (LA(1)=='+' && LA(2)=='=') {
						mPLUS_ASSIGN(true);
					}
					else if (LA(1)=='+' && LA(2)=='+') {
						mINC(true);
					}
					else if (LA(1)=='-' && LA(2)=='=') {
						mMINUS_ASSIGN(true);
					}
					else if (LA(1)=='-' && LA(2)=='-') {
						mDEC(true);
					}
					else if (LA(1)=='*' && LA(2)=='=') {
						mSTAR_ASSIGN(true);
					}
					else if (LA(1)=='%' && LA(2)=='=') {
						mMOD_ASSIGN(true);
					}
					else if (LA(1)=='>' && LA(2)=='>' && true) {
						mSR(true);
					}
					else if (LA(1)=='>' && LA(2)=='=') {
						mGE(true);
					}
					else if (LA(1)=='<' && LA(2)=='<' && true) {
						mSL(true);
					}
					else if (LA(1)=='<' && LA(2)=='=') {
						mLE(true);
					}
					else if (LA(1)=='^' && LA(2)=='=') {
						mBXOR_ASSIGN(true);
					}
					else if (LA(1)=='|' && LA(2)=='=') {
						mBOR_ASSIGN(true);
					}
					else if (LA(1)=='|' && LA(2)=='|') {
						mLOR(true);
					}
					else if (LA(1)=='&' && LA(2)=='=') {
						mBAND_ASSIGN(true);
					}
					else if (LA(1)=='&' && LA(2)=='&') {
						mLAND(true);
					}
					else if (LA(1)=='/' && LA(2)=='/') {
						mSL_COMMENT(true);
					}
					else if (LA(1)=='/' && LA(2)=='*') {
						mML_COMMENT(true);
					}
					else if (LA(1)=='=' && true) {
						mASSIGN(true);
					}
					else if (LA(1)=='!' && true) {
						mLNOT(true);
					}
					else if (LA(1)=='/' && true) {
						mDIV(true);
					}
					else if (LA(1)=='+' && true) {
						mPLUS(true);
					}
					else if (LA(1)=='-' && true) {
						mMINUS(true);
					}
					else if (LA(1)=='*' && true) {
						mSTAR(true);
					}
					else if (LA(1)=='%' && true) {
						mMOD(true);
					}
					else if (LA(1)=='>' && true) {
						mGT(true);
					}
					else if (LA(1)=='<' && true) {
						mLT(true);
					}
					else if (LA(1)=='^' && true) {
						mBXOR(true);
					}
					else if (LA(1)=='|' && true) {
						mBOR(true);
					}
					else if (LA(1)=='&' && true) {
						mBAND(true);
					} else if (LA(1)==EOF_CHAR) {uponEOF(); _returnToken = makeToken(Token.EOF_TYPE);} else
						throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
				}
				if ( _returnToken==null ) continue tryAgain; // found SKIP token
				_ttype = _returnToken.getType();
				_returnToken.setType(_ttype);
				return _returnToken;
			}
			catch (RecognitionException e) {
				throw new TokenStreamRecognitionException(e);
			}
		}
		catch (CharStreamException cse) {
			if ( cse instanceof CharStreamIOException )
				throw new TokenStreamIOException(((CharStreamIOException)cse).io);
			else
				throw new TokenStreamException(cse.getMessage());
		}
	}
}

	public final void mQUESTION(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = QUESTION;
		match('?');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mLPAREN(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = LPAREN;
		match('(');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mRPAREN(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = RPAREN;
		match(')');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mLBRACK(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = LBRACK;
		match('[');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mRBRACK(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = RBRACK;
		match(']');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mLCURLY(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = LCURLY;
		match('{');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mRCURLY(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = RCURLY;
		match('}');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mCOLON(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = COLON;
		match(':');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mCOMMA(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = COMMA;
		match(',');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mASSIGN(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = ASSIGN;
		match('=');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mEQUAL(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = EQUAL;
		match("==");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mLNOT(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = LNOT;
		match('!');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mBNOT(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = BNOT;
		match('~');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mNOT_EQUAL(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = NOT_EQUAL;
		match("!=");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mDIV(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = DIV;
		match('/');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mDIV_ASSIGN(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = DIV_ASSIGN;
		match("/=");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mPLUS(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = PLUS;
		match('+');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mPLUS_ASSIGN(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = PLUS_ASSIGN;
		match("+=");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mINC(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = INC;
		match("++");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mMINUS(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = MINUS;
		match('-');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mMINUS_ASSIGN(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = MINUS_ASSIGN;
		match("-=");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mDEC(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = DEC;
		match("--");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mSTAR(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = STAR;
		match('*');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mSTAR_ASSIGN(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = STAR_ASSIGN;
		match("*=");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mMOD(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = MOD;
		match('%');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mMOD_ASSIGN(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = MOD_ASSIGN;
		match("%=");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mSR(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = SR;
		match(">>");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mSR_ASSIGN(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = SR_ASSIGN;
		match(">>=");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mBSR(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = BSR;
		match(">>>");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mBSR_ASSIGN(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = BSR_ASSIGN;
		match(">>>=");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mGE(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = GE;
		match(">=");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mGT(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = GT;
		match(">");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mSL(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = SL;
		match("<<");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mSL_ASSIGN(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = SL_ASSIGN;
		match("<<=");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mLE(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = LE;
		match("<=");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mLT(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = LT;
		match('<');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mBXOR(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = BXOR;
		match('^');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mBXOR_ASSIGN(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = BXOR_ASSIGN;
		match("^=");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mBOR(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = BOR;
		match('|');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mBOR_ASSIGN(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = BOR_ASSIGN;
		match("|=");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mLOR(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = LOR;
		match("||");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mBAND(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = BAND;
		match('&');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mBAND_ASSIGN(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = BAND_ASSIGN;
		match("&=");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mLAND(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = LAND;
		match("&&");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mSEMI(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = SEMI;
		match(';');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mWS(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = WS;
		{
		int _cnt369=0;
		_loop369:
		do {
			switch ( LA(1)) {
			case ' ':
			{
				match(' ');
				break;
			}
			case '\t':
			{
				match('\t');
				break;
			}
			case '\u000c':
			{
				match('\f');
				break;
			}
			case '\n':  case '\r':
			{
				{
				if (LA(1)=='\r' && LA(2)=='\n' && true && true)
					match("\r\n");
				else if (LA(1)=='\r' && true && true && true)
					match('\r');
				else if (LA(1)=='\n')
					match('\n');
				else
					throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());

				}
				if ( inputState.guessing==0 )
					newline();
				break;
			}
			default:
			{
				if ( _cnt369>=1 )
					break _loop369;
				else
					throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
			}
			}
			_cnt369++;
		} while (true);
		}
		if ( inputState.guessing==0 )
			_ttype = Token.SKIP;
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mSL_COMMENT(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = SL_COMMENT;
		match("//");
		{
		_loop373:
		do
			if (_tokenSet_0.member(LA(1))) {
			match(_tokenSet_0);
			} else
				break _loop373;
		while (true);
		}
		{
		switch ( LA(1)) {
		case '\n':
		{
			match('\n');
			break;
		}
		case '\r':
		{
			match('\r');
			{
			if (LA(1)=='\n')
				match('\n');
			else {
			}

			}
			break;
		}
		default:
		{
			throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		if ( inputState.guessing==0 ) {
			_ttype = Token.SKIP; newline();
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mML_COMMENT(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = ML_COMMENT;
		match("/*");
		{
		_loop379:
		do
			if (LA(1)=='\r' && LA(2)=='\n' && LA(3) >= '\u0003' && LA(3) <= '\uffff' && LA(4) >= '\u0003' && LA(4) <= '\uffff') {
				match('\r');
				match('\n');
				if ( inputState.guessing==0 )
					newline();
			}
			else if (LA(1)=='*' && LA(2) >= '\u0003' && LA(2) <= '\uffff' && LA(3) >= '\u0003' && LA(3) <= '\uffff'&&LA(2)!='/')
				match('*');
			else if (LA(1)=='\r' && LA(2) >= '\u0003' && LA(2) <= '\uffff' && LA(3) >= '\u0003' && LA(3) <= '\uffff' && true) {
				match('\r');
				if ( inputState.guessing==0 )
					newline();
			}
			else if (LA(1)=='\n') {
				match('\n');
				if ( inputState.guessing==0 )
					newline();
			}
			else if (_tokenSet_1.member(LA(1))) {
			match(_tokenSet_1);
			} else
				break _loop379;
		while (true);
		}
		match("*/");
		if ( inputState.guessing==0 )
			_ttype = Token.SKIP;
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mCHAR_LITERAL(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = CHAR_LITERAL;
		match('\'');
		{
		if (LA(1)=='\\')
			mESC(false);
		else if (_tokenSet_2.member(LA(1))) {
		match(_tokenSet_2);
		} else
			throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());

		}
		match('\'');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	protected final void mESC(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = ESC;
		match('\\');
		{
		switch ( LA(1)) {
		case 'n':
		{
			match('n');
			break;
		}
		case 'r':
		{
			match('r');
			break;
		}
		case 't':
		{
			match('t');
			break;
		}
		case 'b':
		{
			match('b');
			break;
		}
		case 'f':
		{
			match('f');
			break;
		}
		case '"':
		{
			match('"');
			break;
		}
		case '\'':
		{
			match('\'');
			break;
		}
		case '\\':
		{
			match('\\');
			break;
		}
		case 'u':
		{
			{
			int _cnt390=0;
			_loop390:
			do {
				if (LA(1)=='u')
					match('u');
				else if ( _cnt390>=1 )
					break _loop390;
				else
					throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());

				_cnt390++;
			} while (true);
			}
			mHEX_DIGIT(false);
			mHEX_DIGIT(false);
			mHEX_DIGIT(false);
			mHEX_DIGIT(false);
			break;
		}
		case '0':  case '1':  case '2':  case '3':
		{
			matchRange('0','3');
			{
			if (LA(1) >= '0' && LA(1) <= '7' && _tokenSet_0.member(LA(2)) && true && true) {
				matchRange('0','7');
				{
				if (LA(1) >= '0' && LA(1) <= '7' && _tokenSet_0.member(LA(2)) && true && true)
					matchRange('0','7');
				else if (_tokenSet_0.member(LA(1)) && true && true && true) {
				} else
					throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());

				}
			}
			else if (_tokenSet_0.member(LA(1)) && true && true && true) {
			} else
				throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());

			}
			break;
		}
		case '4':  case '5':  case '6':  case '7':
		{
			matchRange('4','7');
			{
			if (LA(1) >= '0' && LA(1) <= '7' && _tokenSet_0.member(LA(2)) && true && true)
				matchRange('0','7');
			else if (_tokenSet_0.member(LA(1)) && true && true && true) {
			} else
				throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());

			}
			break;
		}
		default:
		{
			throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mSTRING_LITERAL(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = STRING_LITERAL;
		match('"');
		{
		_loop386:
		do
			if (LA(1)=='\\')
				mESC(false);
			else if (_tokenSet_3.member(LA(1))) {
			match(_tokenSet_3);
			} else
				break _loop386;
		while (true);
		}
		match('"');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	protected final void mHEX_DIGIT(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = HEX_DIGIT;
		{
		switch ( LA(1)) {
		case '0':  case '1':  case '2':  case '3':
		case '4':  case '5':  case '6':  case '7':
		case '8':  case '9':
		{
			matchRange('0','9');
			break;
		}
		case 'A':  case 'B':  case 'C':  case 'D':
		case 'E':  case 'F':
		{
			matchRange('A','F');
			break;
		}
		case 'a':  case 'b':  case 'c':  case 'd':
		case 'e':  case 'f':
		{
			matchRange('a','f');
			break;
		}
		default:
		{
			throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	protected final void mVOCAB(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = VOCAB;
		matchRange('\3','\377');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mIDENT(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = IDENT;
		{
		switch ( LA(1)) {
		case 'a':  case 'b':  case 'c':  case 'd':
		case 'e':  case 'f':  case 'g':  case 'h':
		case 'i':  case 'j':  case 'k':  case 'l':
		case 'm':  case 'n':  case 'o':  case 'p':
		case 'q':  case 'r':  case 's':  case 't':
		case 'u':  case 'v':  case 'w':  case 'x':
		case 'y':  case 'z':
		{
			matchRange('a','z');
			break;
		}
		case 'A':  case 'B':  case 'C':  case 'D':
		case 'E':  case 'F':  case 'G':  case 'H':
		case 'I':  case 'J':  case 'K':  case 'L':
		case 'M':  case 'N':  case 'O':  case 'P':
		case 'Q':  case 'R':  case 'S':  case 'T':
		case 'U':  case 'V':  case 'W':  case 'X':
		case 'Y':  case 'Z':
		{
			matchRange('A','Z');
			break;
		}
		case '_':
		{
			match('_');
			break;
		}
		case '$':
		{
			match('$');
			break;
		}
		default:
		{
			throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		{
		_loop400:
		do
			switch ( LA(1)) {
			case 'a':  case 'b':  case 'c':  case 'd':
			case 'e':  case 'f':  case 'g':  case 'h':
			case 'i':  case 'j':  case 'k':  case 'l':
			case 'm':  case 'n':  case 'o':  case 'p':
			case 'q':  case 'r':  case 's':  case 't':
			case 'u':  case 'v':  case 'w':  case 'x':
			case 'y':  case 'z':
			{
				matchRange('a','z');
				break;
			}
			case 'A':  case 'B':  case 'C':  case 'D':
			case 'E':  case 'F':  case 'G':  case 'H':
			case 'I':  case 'J':  case 'K':  case 'L':
			case 'M':  case 'N':  case 'O':  case 'P':
			case 'Q':  case 'R':  case 'S':  case 'T':
			case 'U':  case 'V':  case 'W':  case 'X':
			case 'Y':  case 'Z':
			{
				matchRange('A','Z');
				break;
			}
			case '_':
			{
				match('_');
				break;
			}
			case '0':  case '1':  case '2':  case '3':
			case '4':  case '5':  case '6':  case '7':
			case '8':  case '9':
			{
				matchRange('0','9');
				break;
			}
			case '$':
			{
				match('$');
				break;
			}
			default:
			{
				break _loop400;
			}
			}
		while (true);
		}
		if ( inputState.guessing==0 ) {

						// check if "assert" keyword is enabled
						if (assertEnabled && "assert".equals(new String(text.getBuffer(),_begin,text.length()-_begin)))
							_ttype = LITERAL_assert; // set token type for the rule in the parser
						// check if "enum" keyword is enabled
						if (enumEnabled && "enum".equals(new String(text.getBuffer(),_begin,text.length()-_begin)))
							_ttype = LITERAL_enum; // set token type for the rule in the parser

		}
		_ttype = testLiteralsTable(_ttype);
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mNUM_INT(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = NUM_INT;
		Token f1=null;
		Token f2=null;
		Token f3=null;
		Token f4=null;
		boolean isDecimal=false; Token t=null;

		switch ( LA(1)) {
		case '.':
		{
			match('.');
			if ( inputState.guessing==0 )
				_ttype = DOT;
			{
			switch ( LA(1)) {
			case '0':  case '1':  case '2':  case '3':
			case '4':  case '5':  case '6':  case '7':
			case '8':  case '9':
			{
				{
				{
				int _cnt405=0;
				_loop405:
				do {
					if (LA(1) >= '0' && LA(1) <= '9')
						matchRange('0','9');
					else if ( _cnt405>=1 )
						break _loop405;
					else
						throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());

					_cnt405++;
				} while (true);
				}
				{
				if (LA(1)=='E'||LA(1)=='e')
					mEXPONENT(false);
				else {
				}

				}
				{
				if (LA(1)=='D'||LA(1)=='F'||LA(1)=='d'||LA(1)=='f') {
					mFLOAT_SUFFIX(true);
					f1=_returnToken;
					if ( inputState.guessing==0 )
						t=f1;
				}
				else {
				}

				}
				if ( inputState.guessing==0 )
					if (t != null && t.getText().toUpperCase().indexOf('F')>=0)
						_ttype = NUM_FLOAT;
					else
						_ttype = NUM_DOUBLE; // assume double
				}
				break;
			}
			case '.':
			{
				{
				match("..");
				if ( inputState.guessing==0 )
					_ttype = TRIPLE_DOT;
				}
				break;
			}
			default:
				{
				}
			}
			}
			break;
		}
		case '0':  case '1':  case '2':  case '3':
		case '4':  case '5':  case '6':  case '7':
		case '8':  case '9':
		{
			{
			switch ( LA(1)) {
			case '0':
			{
				match('0');
				if ( inputState.guessing==0 )
					isDecimal = true;
				{
				if (LA(1)=='X'||LA(1)=='x') {
					{
					switch ( LA(1)) {
					case 'x':
					{
						match('x');
						break;
					}
					case 'X':
					{
						match('X');
						break;
					}
					default:
					{
						throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
					}
					}
					}
					{
					int _cnt413=0;
					_loop413:
					do {
						if (_tokenSet_4.member(LA(1)) && true && true && true)
							mHEX_DIGIT(false);
						else if ( _cnt413>=1 )
							break _loop413;
						else
							throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());

						_cnt413++;
					} while (true);
					}
				}
				else {
					boolean synPredMatched418 = false;
					if (LA(1) >= '0' && LA(1) <= '9' && true && true && true) {
						int _m418 = mark();
						synPredMatched418 = true;
						inputState.guessing++;
						try {
							{
							{
							int _cnt416=0;
							_loop416:
							do {
								if (LA(1) >= '0' && LA(1) <= '9')
									matchRange('0','9');
								else if ( _cnt416>=1 )
									break _loop416;
								else
									throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());

								_cnt416++;
							} while (true);
							}
							{
							switch ( LA(1)) {
							case '.':
							{
								match('.');
								break;
							}
							case 'E':  case 'e':
							{
								mEXPONENT(false);
								break;
							}
							case 'D':  case 'F':  case 'd':  case 'f':
							{
								mFLOAT_SUFFIX(false);
								break;
							}
							default:
							{
								throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
							}
							}
							}
							}
						}
						catch (RecognitionException pe) {
							synPredMatched418 = false;
						}
						rewind(_m418);
inputState.guessing--;
					}
					if ( synPredMatched418 ) {
					int _cnt420=0;
					_loop420:
					do {
						if (LA(1) >= '0' && LA(1) <= '9')
							matchRange('0','9');
						else if ( _cnt420>=1 )
							break _loop420;
						else
							throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());

						_cnt420++;
					} while (true);
					} else if (LA(1) >= '0' && LA(1) <= '7' && true && true && true) {
					int _cnt422=0;
					_loop422:
					do {
						if (LA(1) >= '0' && LA(1) <= '7')
							matchRange('0','7');
						else if ( _cnt422>=1 )
							break _loop422;
						else
							throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());

						_cnt422++;
					} while (true);
					} else {
					}
					}
					}
					break;
				}
				case '1':  case '2':  case '3':  case '4':
				case '5':  case '6':  case '7':  case '8':
				case '9':
				{
					{
					matchRange('1','9');
					}
					{
					_loop425:
					do
						if (LA(1) >= '0' && LA(1) <= '9')
							matchRange('0','9');
						else
							break _loop425;
					while (true);
					}
					if ( inputState.guessing==0 )
						isDecimal=true;
					break;
				}
				default:
				{
					throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
				}
				}
				}
				{
				if (LA(1)=='L'||LA(1)=='l') {
					{
					switch ( LA(1)) {
					case 'l':
					{
						match('l');
						break;
					}
					case 'L':
					{
						match('L');
						break;
					}
					default:
					{
						throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
					}
					}
					}
					if ( inputState.guessing==0 )
						_ttype = NUM_LONG;
				}
				else if ((LA(1)=='.'||LA(1)=='D'||LA(1)=='E'||LA(1)=='F'||LA(1)=='d'||LA(1)=='e'||LA(1)=='f')&&isDecimal) {
					{
					switch ( LA(1)) {
					case '.':
					{
						match('.');
						{
						_loop430:
						do
							if (LA(1) >= '0' && LA(1) <= '9')
								matchRange('0','9');
							else
								break _loop430;
						while (true);
						}
						{
						if (LA(1)=='E'||LA(1)=='e')
							mEXPONENT(false);
						else {
						}

						}
						{
						if (LA(1)=='D'||LA(1)=='F'||LA(1)=='d'||LA(1)=='f') {
							mFLOAT_SUFFIX(true);
							f2=_returnToken;
							if ( inputState.guessing==0 )
								t=f2;
						}
						else {
						}

						}
						break;
					}
					case 'E':  case 'e':
					{
						mEXPONENT(false);
						{
						if (LA(1)=='D'||LA(1)=='F'||LA(1)=='d'||LA(1)=='f') {
							mFLOAT_SUFFIX(true);
							f3=_returnToken;
							if ( inputState.guessing==0 )
								t=f3;
						}
						else {
						}

						}
						break;
					}
					case 'D':  case 'F':  case 'd':  case 'f':
					{
						mFLOAT_SUFFIX(true);
						f4=_returnToken;
						if ( inputState.guessing==0 )
							t=f4;
						break;
					}
					default:
					{
						throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
					}
					}
					}
					if ( inputState.guessing==0 )
						if (t != null && t.getText().toUpperCase() .indexOf('F') >= 0)
							_ttype = NUM_FLOAT;
						else
							_ttype = NUM_DOUBLE; // assume double
				}
				else {
				}

				}
				break;
			}
			default:
			{
				throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
			}
			}
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		}

	protected final void mEXPONENT(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = EXPONENT;
		{
		switch ( LA(1)) {
		case 'e':
		{
			match('e');
			break;
		}
		case 'E':
		{
			match('E');
			break;
		}
		default:
		{
			throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		{
		switch ( LA(1)) {
		case '+':
		{
			match('+');
			break;
		}
		case '-':
		{
			match('-');
			break;
		}
		case '0':  case '1':  case '2':  case '3':
		case '4':  case '5':  case '6':  case '7':
		case '8':  case '9':
		{
			break;
		}
		default:
		{
			throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		{
		int _cnt439=0;
		_loop439:
		do {
			if (LA(1) >= '0' && LA(1) <= '9')
				matchRange('0','9');
			else if ( _cnt439>=1 )
				break _loop439;
			else
				throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());

			_cnt439++;
		} while (true);
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	protected final void mFLOAT_SUFFIX(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = FLOAT_SUFFIX;
		switch ( LA(1)) {
		case 'f':
		{
			match('f');
			break;
		}
		case 'F':
		{
			match('F');
			break;
		}
		case 'd':
		{
			match('d');
			break;
		}
		case 'D':
		{
			match('D');
			break;
		}
		default:
		{
			throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}

	public final void mAT(final boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = AT;
		match('@');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}


	private static final long[] mk_tokenSet_0() {
		long[] data = new long[2048];
		data[0]=-9224L;
		for (int i = 1; i<=1023; i++)
			data[i]=-1L;
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = new long[2048];
		data[0]=-4398046520328L;
		for (int i = 1; i<=1023; i++)
			data[i]=-1L;
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = new long[2048];
		data[0]=-549755823112L;
		data[1]=-268435457L;
		for (int i = 2; i<=1023; i++)
			data[i]=-1L;
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = new long[2048];
		data[0]=-17179878408L;
		data[1]=-268435457L;
		for (int i = 2; i<=1023; i++)
			data[i]=-1L;
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	private static final long[] mk_tokenSet_4() {
		long[] data = new long[1025];
		data[0]=287948901175001088L;
		data[1]=541165879422L;
		return data;
	}
	public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());

	}
