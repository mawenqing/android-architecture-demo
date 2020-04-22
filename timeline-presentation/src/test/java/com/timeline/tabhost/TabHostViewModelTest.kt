package com.timeline.tabhost

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.core.timeline.TimelineCategory
import com.core.timeline.TimelineInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.*
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

/**
 * @author mawenqing.
 */
class TabHostViewModelTest {
    @Mock
    lateinit var interactor: TimelineInteractor

    @Mock
    lateinit var dataObserver: Observer<List<String>>

    @Mock
    lateinit var errorObserver: Observer<String>

    @Captor
    lateinit var captor: ArgumentCaptor<List<String>>

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: TimelineTabViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = TimelineTabViewModel(interactor, Dispatchers.Unconfined)
        viewModel.tabs.observeForever(dataObserver)
        viewModel.error.observeForever(errorObserver)
    }

    @Test
    fun getTabs_observeMatchedResult() {
        runBlocking {
            // Given
            val list = listOf(
                TimelineCategory("A"),
                TimelineCategory("B"),
                TimelineCategory("C")
            )
            `when`(interactor.loadCategories(ArgumentMatchers.any()))
                .thenReturn(list)

            // Then
            verify(dataObserver).onChanged(captor.capture())
            assertThat(captor.value)
                .hasSize(3)
                .extracting("name")
                .containsExactly("A", "B", "C")
        }
    }

    @Test
    fun errorOccurs_observeError() {
        runBlocking {
            // When
            viewModel.handleError(Exception("message"))

            // Then
            verify(errorObserver).onChanged("message")
        }
    }
}
