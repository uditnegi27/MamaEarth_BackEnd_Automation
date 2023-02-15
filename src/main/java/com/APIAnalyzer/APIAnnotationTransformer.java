package com.APIAnalyzer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

public class APIAnnotationTransformer implements IAnnotationTransformer{
	
	public void transform(ITestAnnotation testAnnotation, Class testClass, Constructor constuctor, Method method) {
		testAnnotation.setRetryAnalyzer(ResultAnalyzer.class);
	}
	
}

