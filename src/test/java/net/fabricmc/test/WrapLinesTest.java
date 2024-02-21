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
	 * Trigger branches 1, 2, 5, 7, 8, 9, 12.
	 */
	@Test
	void testWrapLines_longStringPerformsWrapping() {
		String str = "This is a long string that needs to be wrapped";
		String expected = "This is a\nlong\nstring\nthat needs\nto be\nwrapped";
		int limit = 10;
		String wrappedString = StringUtil.wrapLines(str, limit);
		assertEquals(wrappedString, expected);
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
		assertEquals(notWrappedString, expected);
	}
}
