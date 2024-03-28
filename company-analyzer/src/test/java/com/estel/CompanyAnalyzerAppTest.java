package com.estel;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class CompanyAnalyzerAppTest
    extends TestCase
{
    public CompanyAnalyzerAppTest(String testName)
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( CompanyAnalyzerAppTest.class );
    }

    public void testApp()
    {
        assertTrue( true );
    }
}
