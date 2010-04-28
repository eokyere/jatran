/*
 *  Copyright 2004 Blandware (http://www.blandware.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package jatran.stub;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.codec.binary.Base64;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.util.*;


/**
 * <p>String Utility Class.
 * This is used to encode passwords programmatically.
 * It also encodes string for inserting in database
 * </p>
 * <p><a href="StringUtil.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Sergey Zubtsovskiy <a href="mailto:sergey.zubtsovskiy@blandware.com">&lt;sergey.zubtsovskiy@blandware.com&gt;</a>
 * @author Andrey Grebnev <a href="mailto:andrey.grebnev@blandware.com">&lt;andrey.grebnev@blandware.com&gt;</a>
 * @author Matt Raible <a href="mailto:matt@raibledesigns.com">&lt;matt@raibledesigns.com&gt;</a>
 * @version $Revision: 1.27 $ $Date: 2008/04/29 17:48:04 $
 */
public class StringUtil {
	//~ Static fields/initializers =============================================

	//~ Methods ================================================================

	/**
	 * Encodes a string using algorithm specified in web.xml and return the
	 * resulting encrypted password. If exception, the plain credentials
	 * string is returned
	 *
	 * @param password  Password or other credentials to use in authenticating
	 *                  this username
	 * @param algorithm Algorithm used to do the digest
	 * @return encypted password based on the algorithm.
	 */
	public static String encodePassword(String password, String algorithm) {

		if ( password == null ) {
			return null;
		}

		Log log = LogFactory.getLog(StringUtil.class);
		byte[] unencodedPassword = password.getBytes();

		MessageDigest md = null;

		try {
			// first create an instance, given the provider
			md = MessageDigest.getInstance(algorithm);
		} catch ( Exception e ) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			if ( log.isErrorEnabled() ) {
				log.error(sw.toString());
			}
			return password;
		}

		md.reset();

		// call the update method one or more times
		// (useful when you don't know the size of your data, e.g. stream)
		md.update(unencodedPassword);

		// now calculate the hash
		byte[] encodedPassword = md.digest();

		StringBuffer buf = new StringBuffer();

		for ( int i = 0; i < encodedPassword.length; i++ ) {
			if ( (encodedPassword[i] & 0xff) < 0x10 ) {
				buf.append("0");
			}

			buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
		}

		return buf.toString();
	}

	/**
	 * Encodes a string using Base64 encoding. Used when storing passwords
	 * as cookies.
	 * <p>	 * This is weak encoding in that anyone can use the decodeString
	 * routine to reverse the encoding.
	 *
	 * @param str The string to encode
	 * @return Encoded string
	 */
	public static String encodeString(String str) {
        Base64 encoder = new Base64();
        return new String(encoder.encode(str.getBytes())).trim();
    }

	/**
	 * Decodes a string which was encoded using Base64 encoding.
	 *
	 * @param str The string to decode
	 * @return Decoded string
	 */
	public static String decodeString(String str) {
        Base64 decoder = new Base64();
        return new String(decoder.decode(str.getBytes()));
    }

	/**
	 * Encodes a string for inserting to database table using SQL.
	 *
	 * @param inputString input string
	 * @return encoded string
	 */
	public static String sqlEscape(String inputString) {
        if (inputString == null)
            return null;

		StringBuffer buf = new StringBuffer((int) (inputString.length() * 1.1));
		int stringLength = inputString.length();

		for ( int i = 0; i < stringLength; ++i ) {
			char c = inputString.charAt(i);

			switch ( c ) {
				case 0: /* Must be escaped for 'mysql' */
					buf.append('\\');
					buf.append('0');

					break;

				case '\n': /* Must be escaped for logs */
					buf.append('\\');
					buf.append('n');

					break;

				case '\r':
					buf.append('\\');
					buf.append('r');

					break;

				case '\\':
					buf.append('\\');
					buf.append('\\');

					break;

				case '\'':
					buf.append('\''); // Doubling, not escaping with slash!
					buf.append('\'');

					break;

				case '"': /* Better safe than sorry */
					buf.append('\\');
					buf.append('"');

					break;

				case '\032': /* This gives problems on Win32 */
					buf.append('\\');
					buf.append('Z');

					break;

				default:
					buf.append(c);
			}
		}
		return buf.toString();
	}

	/**
	 * Encodes a string for inserting to javascript (it's assumed to be enclosed
     * with double-quotes).
	 *
	 * @param inputString input string
	 * @return encoded string
	 */
	public static String javascriptEscape(String inputString) {
        if (inputString == null)
            return null;

		StringBuffer buf = new StringBuffer((int) (inputString.length() * 1.1));
		int stringLength = inputString.length();

		for ( int i = 0; i < stringLength; ++i ) {
			char c = inputString.charAt(i);

			switch ( c ) {
				case '\n':
					buf.append('\\');
					buf.append('n');

					break;

				case '\r':
					buf.append('\\');
					buf.append('r');

					break;

				case '\\':
					buf.append('\\');
					buf.append('\\');

					break;

				case '\'':
					buf.append('\\');
					buf.append('\'');

					break;

				case '"':
					buf.append('\\');
					buf.append('"');

					break;

				default:
					buf.append(c);
			}
		}
		return buf.toString();
	}

    /**
	 * Replaces all HTML-sensitive characters with their entity equivalents
	 * @param html String to encode
	 * @return string with all HTML-sensitive characters replaced with their entity equivalents
	 */
	public static String htmlEncode(String html) {
		if ( html == null ) {
			return null;
		}
		StringBuffer buf = new StringBuffer((int) (html.length() * 1.1));
		int stringLength = html.length();

		for ( int i = 0; i < stringLength; ++i ) {
			char c = html.charAt(i);

			switch ( c ) {
				case '\'':
					buf.append("&#39;");

					break;

				case '"':
					buf.append("&quot;");

					break;

				case '<':
					buf.append("&lt;");

					break;

				case '>':
					buf.append("&gt;");

					break;

				case '&':
					buf.append("&amp;");

					break;
				default:
					buf.append(c);
			}
		}
		return buf.toString();
	}

	/**
	 * Replaces all HTML-sensitive characters with their entity equivalents in
     * all string values in specified model.
	 * @param model Map of pairs <code>variable -&gt; value</code>.
	 * @return Map with encoded string values
	 */
    public static Map htmlEncodeModel(Map model) {
        return htmlEncodeModelWithExceptions(model, new HashSet());
    }

	/**
	 * Replaces all HTML-sensitive characters with their entity equivalents in
     * all string values in specified model. Some model values are skipped
     * untouched (their names are contained in exceptions set).
	 * @param model Map of pairs <code>variable -&gt; value</code>.
	 * @return Map with encoded string values
	 */
	public static Map htmlEncodeModelWithExceptions(Map model, Set exceptions) {
		Map result = new HashMap(model.size());
		for ( Iterator i = model.entrySet().iterator(); i.hasNext(); ) {
			Map.Entry entry = (Map.Entry) i.next();
			String key = (String) entry.getKey();
			Object value = (Object) entry.getValue();
            if (exceptions.contains(key)) {
                result.put(key, value);
            } else {
                if ( value instanceof String ) {
                    result.put(key, htmlEncode((String) value));
                } else {
                    result.put(key, value);
                }
            }
		}
		return result;
	}

    /**
     * Replaces all EL (end of line) characters with HTML BR tag
     *
     * @param html text in which to replace
     * @return string with EL replaced with BR
     */
    public static String elToBr(String html) {
        if ( html == null ) {
            return null;
        }
        StringBuffer buf = new StringBuffer((int) (html.length() * 1.1));
        int stringLength = html.length();

        for ( int i = 0; i < stringLength; ++i ) {
            char c = html.charAt(i);

            switch ( c ) {
                case '\n':
                    buf.append("<br />");

                    break;
                default:
                    buf.append(c);
            }
        }
        return buf.toString();
    }

	/**
	 * Capitalizes string
	 * @param s String to capitalize
	 * @return Capitalized string
	 */
	public static String capitalize(String s) {
		if ( s == null || s.length() == 0 ) {
			return s;
		}
		char[] chars = s.toCharArray();
		chars[0] = Character.toUpperCase(chars[0]);
		return String.valueOf(chars);
	}

	/**
	 * Splits template name to get identifier, field name and locale
	 * @param templateName String in form <code>identifier_field_locale</code>
	 * @return Array containing identifier, field and locale or empty array if string format is incorrect
	 */
	public static String[] splitTemplateName(String templateName) {
		ArrayList result = new ArrayList();
		int k = templateName.lastIndexOf("_");
		if ( k != -1 ) {
		String locale = templateName.substring(k + 1);
		templateName = templateName.substring(0, k);
		k = templateName.lastIndexOf("_");
			if ( k != -1 ) {
				String field = templateName.substring(k + 1);
				templateName = templateName.substring(0, k);
				result.add(templateName);
				result.add(field);
				result.add(locale);
			}
		}
		return (String[]) result.toArray(new String[0]);
	}

	/**
	 * Concatenates arguments into template name
	 * @param identifier Identifier of mail template
	 * @param field Field (from, subject or body)
	 * @param locale Locale identifier
	 * @return Template name to use in resource loader
	 */
	public static String createTemplateName(String identifier, String field, String locale) {
		StringBuffer result = new StringBuffer(identifier);
		result.append("_");
		result.append(field);
		result.append("_");
		result.append(locale);
		return result.toString();
	}

	/**
	 * Shortens string to be no more than number of symbols specified
	 * @param s String to shorten
	 * @param requiredLength Length to shorten string to
	 * @return Shortened string
	 */
	public static String shortenString(String s, int requiredLength) {
		if ( s != null && s.length() > requiredLength ) {
			s = s.substring(0, requiredLength + 1);
			int space = s.lastIndexOf(" ");
			int lineFeed = s.lastIndexOf("\n");
			int tab = s.lastIndexOf("\t");
			if ( space > 0 || lineFeed > 0 || tab > 0 ) {
				int cut = space > lineFeed ? ( space > tab ? space : tab ) : ( lineFeed > tab ? lineFeed : tab );
				s = s.substring(0, cut);
			}
			s += "...";
		}
		return s;
	}

	/**
	 * Completes number to be no less than specified number of signs
	 *
	 * @param number Number to complete
	 * @param signs  Length of number to reach
	 * @return Adds zero-by-zero from to the left of number until its length will be greater or equal than specified number of signs.
	 *         For example if number = 67 and signs = 3 are specified, result string will be "067" and so on
	 */
	public static String completeNumber(int number, int signs) {

		// if number of signs is equal to zero, return empty string
		if ( signs == 0 ) {
			return "";
		}

		// calculate radix: it is equal to the (signs - 1) power of ten
		int radix = (int) Math.pow(10, signs - 1);

		StringBuffer result = new StringBuffer();
		while ( (number / radix == 0) && (number % radix > 0) ) {
			result.append("0");
			radix = radix / 10;
		}

		// create result string
		result.append(number);

		return result.toString();
	}

    /**
     * Returns a string that consists of a random consequence of digits and
     * latin letters in upper case.
     *
     * @param len length of returned token
     * @return random token
     */
    public static String getRandomToken(int len) {
        StringBuffer result = new StringBuffer(len);
        for (int i = 0; i < len; i++) {
            result.append(Character.forDigit(RandomUtils.nextInt(36), 36));
        }
        return result.toString().toUpperCase();
    }

    /**
     * Escapes double-quote symbols with backslash.
     *
     * @param str string to escape
     * @return string where all double-quotes are escaped with a backslash
     */
    public static String escapeDoubleQuotesForJs(String str) {
        return str == null ? "" : str.replaceAll("\\\"", "\\\\\"");
    }

    /**
     * Replaces all carriage returns and new lines with spaces.
     *
     * @param str string to process
     * @return string without carriage returns
     */
    public static String asSingleLine(String str) {
        return str == null ? "" : str.replaceAll("\\r|\\n", " ");
    }
}
