package com.estel;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class CompanyAnalyzerTest
    extends TestCase
{
    public CompanyAnalyzerTest(String testName)
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( CompanyAnalyzerTest.class );
    }

    public void testApp()
    {
        assertTrue( true );
    }
}
