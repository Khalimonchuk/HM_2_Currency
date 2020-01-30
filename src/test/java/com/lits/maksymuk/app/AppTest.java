package com.lits.maksymuk.app;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest
{

    @BeforeClass
    public void beforeClass() {
        System.out.println("*** Tests Class AppTest starter");
    }

    @BeforeMethod
    public void beforeEachMethod() {
        System.out.println("*** Before each Tests Method");
    }


    /**
     * Rigourous Test :-)
     */
    @Test
    public void testApp()
    {
        assertTrue( true );
    }

    @Test(
            description = "Verify that 10 is equal to 4",
            enabled = true)
    public void firstTest()
    {
        assertEquals(10, 4, "10 is not equal to 4" );
    }
}
