<<<<<<<< HEAD:DynService-Client-Example/src/test/java/com/dynsers/remoteservice/SpringRemoteFramework/Caller/AppTest.java
package com.dynsers.remoteservice.SpringRemoteFramework.Caller;
========
package com.dynsers.core.SpringRemoteFramework.Caller;
>>>>>>>> master:DynService-Client-Example/src/test/java/com/dynsers/core/SpringRemoteFramework/Caller/AppTest.java

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
}
