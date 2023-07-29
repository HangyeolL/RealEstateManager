package com.openclassrooms.realestatemanager

import com.openclassrooms.realestatemanager.domain.CoroutineDispatcherProvider
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import org.junit.rules.TestRule
import org.junit.runners.model.Statement
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.runner.Description


class TestCoroutineRule : TestRule {

    val testCoroutineDispatcher = StandardTestDispatcher()
    val testCoroutineScope = TestScope(testCoroutineDispatcher)

    override fun apply(base: Statement, description: Description): Statement =
        object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                Dispatchers.setMain(testCoroutineDispatcher)

                base.evaluate()

                Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
            }
        }

    fun runTest(block: suspend TestScope.() -> Unit) = testCoroutineScope.runTest {
        block()
    }

    fun getTestCoroutineDispatcherProvider() = mockk<CoroutineDispatcherProvider> {
        every { main } returns testCoroutineDispatcher
        every { io } returns testCoroutineDispatcher
    }
}