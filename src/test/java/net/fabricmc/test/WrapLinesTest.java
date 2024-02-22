package net.fabricmc.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import net.fabricmc.TestCoverage;
import net.fabricmc.loader.impl.util.StringUtil;

public class WrapLinesTest {

	@AfterAll
    public static void AfterAll()
    {
        TestCoverage.AfterAllTests();
    }

	/**
	 * Test that verifies that long string is wrapped correctly.
	 * Trigger branches 1, 4, 5, 6, 5, 8, 11.
	 */
	@Test
	void testWrapLines_longStringPerformsWrapping() {
		String str = "This is a long string that needs to be wrapped";
		String expected = "This is a\nlong\nstring\nthat needs\nto be\nwrapped";
		int limit = 10;
		String wrappedString = StringUtil.wrapLines(str, limit);
		assertEquals(expected, wrappedString);
	}

	/**
	 * Test that verifies that short string is not wrapped.
	 * Triggers branch 0.
	 */
	@Test
	void testWrapLines_shortStringNoWrapping() {
		String str = "";
		String expected = "";
		int limit = 10;
		String notWrappedString = StringUtil.wrapLines(str, limit);
		assertEquals(expected, notWrappedString);
	}

	/**
	 * Test that verifies that string with escape sequences is wrapped correctly.
	 * Triggers branches 2 and 3.
	 */
	@Test
	void testWrapLines_escapeSequences() {
		String str = "Escape\rSequences\n";
		String expected = "EscapeSequences\n";
		int limit = 10;
		String wrappedString = StringUtil.wrapLines(str, limit);
		assertEquals(expected, wrappedString);
	}

	/**
	 * Test that verifies that string with quotations is wrapped correctly.
	 * Triggers branches 9, and 10.
	 */
	@Test
	void testWrapLines_withQuotations() {
		String str = "With \'Quotations\' and \"stuff";
		String expected = "With\n'Quotations'\nand \"stuff";
		int limit = 10;
		String wrappedString = StringUtil.wrapLines(str, limit);
		assertEquals(expected, wrappedString);
	}

}
