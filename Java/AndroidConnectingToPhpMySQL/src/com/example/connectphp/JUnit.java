package com.example.connectphp;

import junit.framework.*;

public class JUnit extends TestCase {
	
	final int resultExpected = 1;
	final int resultAsCoded = 1;
	
	public void ExecutaTeste(){
		assertEquals(resultExpected,resultAsCoded);
	}
}
