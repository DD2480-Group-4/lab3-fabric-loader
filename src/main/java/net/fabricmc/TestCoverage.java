package net.fabricmc;

public class TestCoverage {
	
	public static boolean[] StringUtils_wrapLines = new boolean[18];
	public static boolean[] ModSolver_deriveVersion = new boolean[22];
	public static boolean[] ResultAnalyzer_formatVersionRequirements = new boolean[16];
	public static boolean[] ResultAnalyzer_addErrorToList = new boolean[11];
	public static boolean[] SemanticVersionImpl_SemanticVersionImpl = new boolean[19];

	private static int testCounter = 0;
	private final static int numOfTestFiles = 4;
	public static void AfterAllTests()
	{
		testCounter++;
		if(testCounter >= numOfTestFiles)
		{
			System.out.println("StringUtils_wrapLines: " + getCoverage(StringUtils_wrapLines));
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
