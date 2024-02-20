package net.fabricmc.test;

import java.io.Console;

public class TestCoverage {
	
	public static boolean[] ModPrioSorter_sort = new boolean[25];
	public static boolean[] ModSolver_deriveVersion = new boolean[25];
	public static boolean[] ResultAnalyzer_formatVersionRequirements = new boolean[25];
	public static boolean[] ResultAnalyzer_addErrorToList = new boolean[25];
	public static boolean[] SemanticVersionImpl_SemanticVersionImpl = new boolean[25];

	private static int testCounter = 0;
	private final static int numOfTestFiles = 4;
	public static void AfterAllTests()
	{
		testCounter++;
		if(testCounter >= numOfTestFiles)
		{
			System.out.println("ModPrioSorter_sort: " + getCoverage(ModPrioSorter_sort));
			System.out.println("ModSolver_deriveVersion: " + getCoverage(ModSolver_deriveVersion));
			System.out.println("ResultAnalyzer_formatVersionRequirements: " + getCoverage(ResultAnalyzer_formatVersionRequirements));
			System.out.println("ResultAnalyzer_addErrorToList: " + getCoverage(ResultAnalyzer_addErrorToList));
			System.out.println("SemanticVersionImpl_SemanticVersionImpl: " + getCoverage(SemanticVersionImpl_SemanticVersionImpl));
		}
	}

	private static String getCoverage(boolean[] coverage)
	{
		int covered = 0;
		for(int i = 0; i < coverage.length; i++)
		{
			if(coverage[i])
			{
				covered++;
			}
		}

		return covered + "/" + coverage.length;
	}
}
