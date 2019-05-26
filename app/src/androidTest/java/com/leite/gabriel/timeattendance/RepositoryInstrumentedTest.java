package com.leite.gabriel.timeattendance;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RepositoryInstrumentedTest {
    String sDate = "";
    String sTime = "";

    @Test
    public void CreateTimeCheck_Success() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        long id = Repository.getInstance().CreateTimeCheck(appContext, sDate, sTime);

        assertTrue(id > 0);
    }

    public void Create_Recover_TimeCheck_Success() {
        Context appContext = InstrumentationRegistry.getTargetContext();

        long id = Repository.getInstance().CreateTimeCheck(appContext, sDate, sTime);

        List ids = Repository.getInstance().RecoverTimeCheck(appContext, sDate);

        assertTrue(ids.contains(id));
    }
}