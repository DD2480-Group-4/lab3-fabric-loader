package net.fabricmc.test;


import net.fabricmc.TestCoverage;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;
import net.fabricmc.loader.api.metadata.version.VersionInterval;

import net.fabricmc.loader.impl.util.version.VersionIntervalImpl;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DeriveVersionsTest {

	@AfterAll
	public static void AfterAll()
	{
		TestCoverage.AfterAllTests();
	}

	/**
	 * Calls deriveVersion using reflection, so we don't need to make the class and method public.
	 * @param interval The interval to check.
	 * @return A version in that interval.
	 */
	private static Version deriveVersion(VersionInterval interval) {
		try {
			Class<?> modSolver = Class.forName("net.fabricmc.loader.impl.discovery.ModSolver");
			Method method = modSolver.getDeclaredMethod("deriveVersion", VersionInterval.class);
			if (!method.isAccessible()) {
				method.setAccessible(true);
			}
			return (Version) method.invoke(null, interval);
		} catch (
				ClassNotFoundException | NoSuchMethodException | IllegalArgumentException |
				IllegalAccessException | InvocationTargetException ignored
		) {}
		return null;
	}
	@Test
	void minExclusiveReturnsNextVersion() throws VersionParsingException {
		Version version = deriveVersion(new VersionIntervalImpl(
				Version.parse("1.0.0"), false, Version.parse("2.0.0"), true
		));
		assert version.equals(Version.parse("1.0.1-")); //I wonder if the empty pre-release is intentional.
	}

	@Test
	void maxExclusiveReturnsLargePreRelease() throws VersionParsingException {
		Version version = deriveVersion(new VersionIntervalImpl(
				null, false, Version.parse("2.0.0"), false
		));
		assert version.equals(Version.parse("2.0.0-zzzzzzzz"));
	}

	@Test
	void maxExclusivePreReleaseSmallADecrementsToCapitalZ() throws VersionParsingException {
		Version version = deriveVersion(new VersionIntervalImpl(
				null, false, Version.parse("2.0.0-a"), false
		));
		assert version.equals(Version.parse("2.0.0-Z"));
	}

	@Test
	void maxExclusiveLastPreReleaseDecrementsToPreviousVersion() throws VersionParsingException {
		Version version = deriveVersion(new VersionIntervalImpl(
				null, false, Version.parse("2.0-"), false
		));
		assert version.equals(Version.parse("1.9999.9999"));
	}

	@Test
	void unboundedReturns1() throws VersionParsingException {
		Version version = deriveVersion(new VersionIntervalImpl(
				null, false, null, false
		));
		assert version.equals(Version.parse("1"));
	}

}
