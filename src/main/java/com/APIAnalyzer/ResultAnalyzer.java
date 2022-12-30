package com.APIAnalyzer;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class ResultAnalyzer implements IRetryAnalyzer{
	
	public int testCount = 0;
	public int retryLimit = 3;
	
	public boolean retry(ITestResult result) {
		if(testCount < retryLimit) {
			testCount++;
			return true;
		}
		return false;
	}
}