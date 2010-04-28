package jemitter;

/* Copyright (c) 2003 Andy Tripp */
/**
 * A class that handles indenting nicely.
 * Can be used to output source code nicely, for example.
 * You wouldn't need a whole class for this, except for one problem:
 * After each newline, we need to indent to the appropriate level.
 * But we may then realize that we need to decrease the indent level,
 * but we've now already indented too far.
 * It's too late to "take back" one character from the input stream.
 */

import java.io.*;

public class IndentingPrintStream extends PrintStream {
	private String indent = "  ";
	private int depth = 0;
	private boolean indentPending = false;

	public IndentingPrintStream(OutputStream out, String indent) {
		this(out);
		setIndent(indent);
		bufLength = DEFAULT_BUFFER_LENGTH;
		buf = new byte[DEFAULT_BUFFER_LENGTH];
		count = 0;
	}

	public IndentingPrintStream(OutputStream out) {
		super(out);
	}

	public void setIndent(String indent) {
		this.indent = indent;
	}

	public void println() {
		super.println();
		indentPending = true;
	}

	public void increaseIndent() {
		depth++;
	}

	public void decreaseIndent() {
		depth--;
	}
    
    public int indent() {
        return depth;
    }

	public void print(Object o) {
		if (null == o)
			return;
		print(o.toString());
	}

	public void print(String s) {
		if ("".equals(s))
			return;
		if (indentPending) {
			for (int i = 0; i < depth; i++) {
				super.print(indent);
			}
			indentPending = false;
		}
		// handle newlines embedded in the string
		int index = s.indexOf("\n");
		if (index != -1) {
			super.print(s.substring(0, index));
			println();
			print(s.substring(index + 1));
		} else {
			super.print(s);
		}
	}

	/**
	 * Used to maintain the contract of #close()}.
	 */
	protected boolean hasBeenClosed = false;
	/**
	 * The internal buffer where data is stored.
	 */
	protected byte[] buf;
	/**
	 * The number of valid bytes in the buffer. This value is always
	 * in the range <tt>0</tt> through <tt>buf.length</tt>; elements
	 * <tt>buf[0]</tt> through <tt>buf[count-1]</tt> contain valid
	 * byte data.
	 */
	protected int count;
	/**
	 * Remembers the size of the buffer for speed.
	 */
	private int bufLength;
	/**
	 * The default number of bytes in the buffer. =2048
	 */
	public static final int DEFAULT_BUFFER_LENGTH = 2048;

	/**
	 * Closes this output stream and releases any system resources
	 * associated with this stream. The general contract of
	 * <code>close</code>
	 * is that it closes the output stream. A closed stream cannot
	 * perform
	 * output operations and cannot be reopened.
	 */
	public void close() {
		flush();
		hasBeenClosed = true;
	}

	/**
	 * Writes the specified byte to this output stream. The general
	 * contract for <code>write</code> is that one byte is written
	 * to the output stream. The byte to be written is the eight
	 * low-order bits of the argument <code>b</code>. The 24
	 * high-order bits of <code>b</code> are ignored.
	 *
	 * @param b the <code>byte</code> to write
	 * @throws IOException if an I/O error occurs. In particular,
	 * an <code>IOException</code> may be thrown if the output stream 
	 * has been closed.
	 */
	public void write(final int b) {
		if (hasBeenClosed) {
			return;
		}
		// would this be writing past the buffer?
		if (count == bufLength) {
			// grow the buffer
			final int newBufLength = bufLength + DEFAULT_BUFFER_LENGTH;
			final byte[] newBuf = new byte[newBufLength];
			System.arraycopy(buf, 0, newBuf, 0, bufLength);
			buf = newBuf;
			bufLength = newBufLength;
		}
		buf[count] = (byte) b;
		count++;
	}

	/**
	 * Flushes this output stream and forces any buffered output bytes
	 * to be written out. The general contract of <code>flush</code> is
	 * that calling it is an indication that, if any bytes previously
	 * written have been buffered by the implementation of the output
	 * stream, such bytes should immediately be written to their
	 * intended destination.
	 */
	public void flush() {
		if (count == 0) {
			return;
		}
		// don't print out blank lines; flushing from PrintStream puts
		// out these
		// For linux system
		if (count == 1 && ((char) buf[0]) == '\n') {
			reset();
			return;
		}
		// For mac system
		if (count == 1 && ((char) buf[0]) == '\r') {
			reset();
			return;
		}
		// On windows system
		if (count == 2 && (char) buf[0] == '\r' && (char) buf[1] == '\n') {
			reset();
			return;
		}
		
		final byte[] theBytes = new byte[count];
		System.arraycopy(buf, 0, theBytes, 0, count);
		reset();
	}

	private void reset() {
		// not resetting the buffer -- assuming that if it grew then it
		//   will likely grow similarly again
		count = 0;
	}
}
