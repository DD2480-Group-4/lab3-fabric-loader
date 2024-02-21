package net.fabricmc.test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import net.fabricmc.TestCoverage;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;
import net.fabricmc.loader.api.metadata.ContactInformation;
import net.fabricmc.loader.api.metadata.ModDependency;
import net.fabricmc.loader.api.metadata.Person;
import net.fabricmc.loader.api.metadata.ModDependency.Kind;
import net.fabricmc.loader.api.metadata.version.VersionInterval;
import net.fabricmc.loader.impl.discovery.ModCandidate;
import net.fabricmc.loader.impl.discovery.ResultAnalyzer;
import net.fabricmc.loader.impl.metadata.ContactInformationImpl;
import net.fabricmc.loader.impl.metadata.LoaderModMetadata;
import net.fabricmc.loader.impl.metadata.ModDependencyImpl;
import net.fabricmc.loader.impl.metadata.V0ModMetadata;

public class ResultAnalyzerAddErrorToListTest {

	@AfterAll
	public static void AfterAll()
	{
		TestCoverage.AfterAllTests();
	}

	@Test
	public void addErrorToListTest_MissingMatches() throws VersionParsingException {
		ArrayList<Path> modePathList = new ArrayList<Path>();
		modePathList.add(Paths.get("foo"));
		modePathList.add(Paths.get("bar"));
		LoaderModMetadata metadata = new V0ModMetadata("foo", Version.parse("1.0.1-"),
				new ArrayList<ModDependency>(), null, null, "",
				new ArrayList<String>(), "", "", new ArrayList<Person>(),
				new ArrayList<Person>(), new ContactInformationImpl(new HashMap<String, String>()), "");

		ModCandidate mod = ModCandidate.createPlain(modePathList, metadata, false, new ArrayList<ModCandidate>());
		ModDependency dep = new ModDependencyImpl(Kind.DEPENDS, "foo", new ArrayList<String>());

		OutputStream stream = new ByteArrayOutputStream();
		PrintWriter pr = new PrintWriter(stream);

		ResultAnalyzer.addErrorToList(mod, dep, new ArrayList<ModCandidate>(), false, false, "Foo!", pr);
		pr.flush();

		Assertions.assertEquals(
				"Foo! - Mod 'foo' (foo) 1.0.1- requires an unsatisfiable version range of foo, which is missing!",
				stream.toString().trim());
	}

	@Test
	public void addErrorToListTest_PresentForOtherEnvAndIsPositive() throws VersionParsingException {
		ArrayList<Path> modePathList = new ArrayList<Path>();
		modePathList.add(Paths.get("foo"));
		modePathList.add(Paths.get("bar"));
		LoaderModMetadata metadata = new V0ModMetadata("foo", Version.parse("1.0.1-"),
				new ArrayList<ModDependency>(), null, null, "",
				new ArrayList<String>(), "", "", new ArrayList<Person>(),
				new ArrayList<Person>(), new ContactInformationImpl(new HashMap<String, String>()), "");

		ModCandidate mod = ModCandidate.createPlain(modePathList, metadata, false, new ArrayList<ModCandidate>());
		ModDependency dep = new ModDependencyImpl(Kind.DEPENDS, "foo", new ArrayList<String>());

		OutputStream stream = new ByteArrayOutputStream();
		PrintWriter pr = new PrintWriter(stream);

		ResultAnalyzer.addErrorToList(mod, dep, new ArrayList<ModCandidate>(), true, false, "Foo!", pr);
		pr.flush();
		
		Assertions.assertEquals(
			"Foo! - Mod 'foo' (foo) 1.0.1- requires an unsatisfiable version range of foo, which is disabled for this environment (client/server only)!",
				stream.toString().trim());
	}
}
