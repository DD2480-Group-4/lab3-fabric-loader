package net.fabricmc.test;

import net.fabricmc.TestCoverage;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;
import net.fabricmc.loader.api.metadata.version.VersionInterval;
import net.fabricmc.loader.impl.discovery.ResultAnalyzer;
import net.fabricmc.loader.impl.util.version.VersionIntervalImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;


public class ResultAnalyzerFormatVersionRequirements {

	//System.out.println(Arrays.toString(TestCoverage.ResultAnalyzer_formatVersionRequirements));
	//System.out.println(ret);

	/*
	Tests that the function returns that the range is unsatisfiable when provided with an empty list.
	Covers branch [15]
	 */
	@Test
	public void testEmptyCollection() {
		Collection<VersionInterval> emptyInterval = Collections.emptyList();
		String ret = ResultAnalyzer.formatVersionRequirements(emptyInterval);
		Assertions.assertEquals("an unsatisfiable version range", ret);
	}

	/*
	Tests that providing the function with an unspecified intervals returns that any version is required.
	Covers branches [0], [2] and [3]
	 */
	@Test
	public void testUndefinedVersionInterval() {
		Collection<VersionInterval> nullInterval = Arrays.asList((new VersionIntervalImpl(null, false, null, false)));
		String ret = ResultAnalyzer.formatVersionRequirements(nullInterval);
		Assertions.assertEquals("any version", ret);
	}

	/*
	Tests that providing the function with a min (inclusive) required version and no max required version
	returns that any version from and including the min version is required.
	Covers branches [0], [6], [7] and [16]
	 */
	@Test
	public void testLowerRequiredVersion() throws VersionParsingException {
		Collection<VersionInterval> nullIntervals = Arrays.asList((new VersionIntervalImpl(Version.parse("1.0.0"), true, null, false)));
		String ret = ResultAnalyzer.formatVersionRequirements(nullIntervals);
		Assertions.assertEquals("version 1.0.0 or later", ret);
	}

	/*
	Tests that providing the function with a min (inclusive) required version and a max (exclusive) required version
	returns that any version from and including the min version to and excluding the max version is required.
	Also tests that the function skips empty intervals
	Covers branches [0], [1], [14] and [16]
	 */
	@Test
	public void testInclusiveExclusiveVersionIntervalWithEmptyListItems() throws VersionParsingException {
		Collection<VersionInterval> nullIntervals = Arrays.asList(
				null,
				new VersionIntervalImpl(Version.parse("1.0.0"), true, Version.parse("1.2.3"), false),
				null,
				null
		);
		String ret = ResultAnalyzer.formatVersionRequirements(nullIntervals);
		Assertions.assertEquals("any version between 1.0.0 (inclusive) and 1.2.3 (exclusive)", ret);
	}

	/*
	Tests that providing the function with required versions min=max returns a specific required version
	if the interval is min- and max-inclusive. If the interval is not both min- and max-inclusive
	it skips the interval as it is considered empty/invalid.
	Covers branches [0], [9], [10], [11] and [16]
	 */
	@Test
	public void testSpecificVersionOnInclusiveAndExclusive() throws VersionParsingException {
		Collection<VersionInterval> nullIntervals = Arrays.asList(
				new VersionIntervalImpl(Version.parse("1.1.1"), true, Version.parse("1.1.1"), false),
				new VersionIntervalImpl(Version.parse("1.1.1"), true, Version.parse("1.1.1"), true)
		);
		String ret = ResultAnalyzer.formatVersionRequirements(nullIntervals);
		Assertions.assertEquals("version 1.1.1", ret);
	}
}
