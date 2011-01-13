package com.barchart.udt.lib;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.barchart.udt.HelperTestUtilities;

public class TestLibraryUDT_3 {

	@Before
	public void setUp() throws Exception {
		HelperTestUtilities.logOsArch();
		HelperTestUtilities.logClassPath();
		HelperTestUtilities.logLibraryPath();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLoadString() throws Exception {

		String targetFolder = "./target/test-lib-3";

		LibraryUDT_3.load(targetFolder);

		assertTrue(true);

	}

}