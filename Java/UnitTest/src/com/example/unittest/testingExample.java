package com.example.unittest;

import org.junit.Test;

import junit.framework.TestCase;

public class testingExample extends TestCase {

	@Test
	public void test(){
		MyTestExample exampleForTest = new MyTestExample();
		assertEquals(exampleForTest.exampleMethod(),1);
	}
}
