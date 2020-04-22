package com.timeline.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.core.timeline.TimelineInteractor
import com.core.timeline.TimelineItem
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
class TimelineListViewModelTest {
    @Mock
    lateinit var interactor: TimelineInteractor

    @Mock
    lateinit var dataObserver: Observer<List<TimelineItemModel>>

    @Mock
    lateinit var errorObserver: Observer<String>

    @Captor
    lateinit var captor: ArgumentCaptor<List<TimelineItemModel>>

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: TimelineListViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = TimelineListViewModel(interactor, Dispatchers.Unconfined)
        viewModel.timeline.observeForever(dataObserver)
        viewModel.error.observeForever(errorObserver)
    }

    @Test
    fun getTimeline_observeMatchedResult() {
        runBlocking {
            // Given
            val list = listOf(
                TimelineItem("id", "name", "status", 0, 0, 0, "")
            )
            `when`(interactor.loadTimeline(ArgumentMatchers.anyString(), ArgumentMatchers.any()))
                .thenReturn(list)

            // When
            viewModel.getTimeline("")

            // Then
            verify(dataObserver).onChanged(captor.capture())
            assertThat(captor.value)
                .hasSize(1)
                .extracting("id")
                .containsExactly("id")
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
