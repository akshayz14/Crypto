package com.akshay.upstoxassignment

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.akshay.upstoxassignment.base.Utils
import com.akshay.upstoxassignment.base.toStringOrEmpty
import com.bumptech.glide.util.Util

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.akshay.upstoxassignment", appContext.packageName)
    }


    @Test
    fun UtilTest() {

        val utils = Utils()
        val str = null
        val test = str.toStringOrEmpty()
        assertEquals("", test)

    }
}