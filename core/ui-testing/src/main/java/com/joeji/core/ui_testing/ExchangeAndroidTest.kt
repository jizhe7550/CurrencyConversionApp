package com.joeji.core.ui_testing

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.joeji.core.database.ExchangeDatabase
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

abstract class ExchangeAndroidTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var db: ExchangeDatabase

    protected lateinit var context: Context

    @Before
    open fun setup() {
        context = ApplicationProvider.getApplicationContext()
        hiltRule.inject()
        db.clearAllTables()
    }

    @After
    open fun tearDown() {
        db.close()
    }
}