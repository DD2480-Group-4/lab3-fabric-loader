/*
 * Copyright 2016 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.fabricmc.loader.impl.util;

import static net.fabricmc.TestCoverage.StringUtils_wrapLines;

public final class StringUtil {
	public static String capitalize(String s) {
		if (s.isEmpty()) return s;

		int pos;

		for (pos = 0; pos < s.length(); pos++) {
			if (Character.isLetterOrDigit(s.codePointAt(pos))) {
				break;
			}
		}

		if (pos == s.length()) return s;

		int cp = s.codePointAt(pos);
		int cpUpper = Character.toUpperCase(cp);
		if (cpUpper == cp) return s;

		StringBuilder ret = new StringBuilder(s.length());
		ret.append(s, 0, pos);
		ret.appendCodePoint(cpUpper);
		ret.append(s, pos + Character.charCount(cp), s.length());

		return ret.toString();
	}

	public static String[] splitNamespaced(String s, String defaultNamespace) {
		int i = s.indexOf(':');

		if (i >= 0) {
			return new String[] { s.substring(0, i), s.substring(i + 1) };
		} else {
			return new String[] { defaultNamespace, s };
		}
	}

	public static String wrapLines(String str, int limit) {
		if (str.length() <= limit) {
			// return original string if it's short enough
			StringUtils_wrapLines[0] = true;
			return str;
		}

		StringBuilder sb = new StringBuilder();
		int len = 0;
		int lastSpace = -1;
		for (int i = 0; i < str.length() - 1; i++) {
			StringUtils_wrapLines[1] = true;
			char c = str.charAt(i);
			if (c == '\r') {
				// ignore carriage returns
				StringUtils_wrapLines[2] = true;
				continue;
			}
			if (c == ' ') {
				// record position of space for potential split
				StringUtils_wrapLines[3] = true;
				lastSpace = sb.length();
			}
			if (len >= limit) {
				// limit has been exceeded
				StringUtils_wrapLines[4] = true;
				if (str.charAt(i + 1) == ' ') {
					// next character is a space, split there
					StringUtils_wrapLines[5] = true;
					sb.append(c);
					sb.append('\n');
					len = 0;
					i++;
					continue;
				}
				if (lastSpace != -1) {
					// split at last space, and reset len to this position
					StringUtils_wrapLines[6] = true;
					sb.setCharAt(lastSpace, '\n');
					len = sb.length() - lastSpace;
				}
			}
			sb.append(c);
			len++;
		}
		sb.append(str.charAt(str.length() - 1));

		return sb.toString();
	}
}
