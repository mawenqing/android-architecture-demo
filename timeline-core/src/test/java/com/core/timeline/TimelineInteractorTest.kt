package com.core.timeline

import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

/**
 * @author mawenqing.
 */
class TimelineInteractorTest {
    @Mock
    lateinit var repository: TimelineRepository

    private lateinit var interactor: TimelineInteractorImpl

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        interactor = TimelineInteractorImpl(repository)
    }

    @Test
    fun loadCategories() {
        runBlocking {
            interactor.loadCategories()

            verify(repository).loadCategories()
        }
    }

    @Test
    fun loadTimeline() {
        runBlocking {
            interactor.loadTimeline("dummy")

            verify(repository).loadTimeline("dummy")
        }
    }
}
