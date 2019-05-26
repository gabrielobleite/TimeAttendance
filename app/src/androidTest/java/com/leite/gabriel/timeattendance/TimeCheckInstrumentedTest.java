
package com.leite.gabriel.timeattendance;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class TimeCheckInstrumentedTest {
    String sDate = "25/05/2019";
    String sTime = "14:57";

    String sTimeUpdate = "17:54";

    @Test
    public void TimeCheck_Create_Success() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        TimeCheck timeCheck = new TimeCheck();
        timeCheck.setDate(sDate);
        timeCheck.setTime(sTime);
        long id = timeCheck.Save(appContext);

        assertTrue(id > 0);
    }

    public void Create_Recover_TimeCheck_Success() {
        Context appContext = InstrumentationRegistry.getTargetContext();

        TimeCheck timeCheck = new TimeCheck();

        timeCheck.setDate(sDate);
        timeCheck.setTime(sTime);
        long id = timeCheck.Save(appContext);

        List lst = timeCheck.RecoverTimeCheck(appContext, sDate);

        assertTrue(lst.contains(timeCheck));
    }

    public void Delete_TimeCheck_Success() {
        Context appContext = InstrumentationRegistry.getTargetContext();

        TimeCheck timeCheck = new TimeCheck();

        timeCheck.setDate(sDate);
        timeCheck.setTime(sTime);
        long id = timeCheck.Save(appContext);

        timeCheck.Delete(appContext);

        assertTrue(true);
    }

    public void Load_TimeCheck_Success() {
        Context appContext = InstrumentationRegistry.getTargetContext();

        TimeCheck timeCheck = new TimeCheck();

        timeCheck.setDate(sDate);
        timeCheck.setTime(sTime);
        long id = timeCheck.Save(appContext);

        TimeCheck loadTimeCheck = new TimeCheck();
        loadTimeCheck.Load(appContext, id);

        assertTrue(loadTimeCheck.getId() == id);
    }

    public void Update_TimeCheck_Success() {
        Context appContext = InstrumentationRegistry.getTargetContext();

        TimeCheck timeCheck = new TimeCheck();

        timeCheck.setDate(sDate);
        timeCheck.setTime(sTime);
        long id = timeCheck.Save(appContext);

        timeCheck.Save(appContext);

        timeCheck.setTime(sTimeUpdate);
        timeCheck.Save(appContext);

        TimeCheck loadTimeCheck = new TimeCheck();
        loadTimeCheck.Load(appContext, id);

        assertTrue(loadTimeCheck.getTime() == sTimeUpdate);
    }
}
