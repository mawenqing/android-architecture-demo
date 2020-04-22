package com.data.timeline

import com.data.timeline.entity.TimelineCategoryRaw
import com.data.timeline.entity.TimelineItemRaw
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

/**
 * @author mawenqing.
 */
class TimelineRepositoryTest {
    @Mock
    lateinit var service: TimelineService
    lateinit var repository: TimelineRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        repository = TimelineRepositoryImpl(service)
    }

    @Test
    fun loadCategories_getMatchedResult() {
        runBlocking {
            // Given
            `when`(service.getTimelineCategories())
                .thenReturn(
                    listOf(
                        TimelineCategoryRaw("nameA", "dataA"),
                        TimelineCategoryRaw("nameB", "dataB"),
                        TimelineCategoryRaw("nameC", "dataC")
                    )
                )

            // When
            val categories = repository.loadCategories()

            // Then
            assertThat(categories)
                .hasSize(3)
                .extracting("name")
                .containsExactly("nameA", "nameB", "nameC")
        }
    }

    @Test
    fun loadCategories_filterInvalid() {
        runBlocking {
            // Given
            `when`(service.getTimelineCategories())
                .thenReturn(
                    listOf(
                        TimelineCategoryRaw("nameA", "dataA"),
                        TimelineCategoryRaw("nameB", "dataB"),
                        TimelineCategoryRaw(null, null),
                        TimelineCategoryRaw("", "data"),
                        TimelineCategoryRaw("name", null),
                        null,
                        TimelineCategoryRaw("nameC", "dataC")
                    )
                )

            // When
            val categories = repository.loadCategories()

            // Then
            assertThat(categories)
                .hasSize(3)
                .extracting("name")
                .containsExactly("nameA", "nameB", "nameC")
        }
    }

    @Test
    fun loadCategories_forwardException() {
        runBlocking {
            // Given
            val exception = Exception()
            `when`(service.getTimelineCategories()).thenThrow(exception)

            // When
            repository.loadCategories {

                // Then
                assertThat(it.cause).isSameAs(exception)
            }
        }
    }

    @Test
    fun loadTimeline_getMatchedResult() {
        runBlocking {
            // Given
            `when`(service.getTimelineCategories())
                .thenReturn(listOf(TimelineCategoryRaw("all", "http://localhost")))
            `when`(service.getTimelineByCategory("http://localhost"))
                .thenReturn(
                    listOf(
                        TimelineItemRaw(
                            "id0", "name", "status", 0,
                            0, 0, ""
                        ),
                        TimelineItemRaw(
                            "id1", "name", "status", 0,
                            0, 0, ""
                        )
                    )
                )

            // When
            repository.loadCategories()
            val items = repository.loadTimeline("all")

            // Then
            assertThat(items)
                .hasSize(2)
                .extracting("id")
                .containsExactly("id0", "id1")
        }
    }

    @Test
    fun loadTimeline_filterInvalid() {
        runBlocking {
            // Given
            `when`(service.getTimelineCategories())
                .thenReturn(listOf(TimelineCategoryRaw("all", "http://localhost")))
            `when`(service.getTimelineByCategory("http://localhost"))
                .thenReturn(
                    listOf(
                        TimelineItemRaw(
                            "id0", "name", "status", 0,
                            0, 0, ""
                        ),
                        null,
                        null,
                        TimelineItemRaw(
                            "id1", "name", "status", 0,
                            0, 0, ""
                        )
                    )
                )

            // When
            repository.loadCategories()
            val items = repository.loadTimeline("all")

            // Then
            assertThat(items)
                .hasSize(2)
                .extracting("id")
                .containsExactly("id0", "id1")
        }
    }

    @Test
    fun loadTimeline_forwardException() {
        runBlocking {
            // Given
            `when`(service.getTimelineCategories())
                .thenReturn(listOf(TimelineCategoryRaw("all", "http://localhost")))
            val exception = Exception()
            `when`(service.getTimelineByCategory("http://localhost")).thenThrow(exception)

            // When
            repository.loadCategories()
            repository.loadTimeline("all") {

                // Then
                assertThat(it.cause).isSameAs(exception)
            }
        }
    }

    @Test
    fun loadTimeline_illegalAddress() {
        runBlocking {
            // When
            repository.loadTimeline("invalid category") {

                // Then
                assertThat(it.cause)
                    .isInstanceOf(IllegalArgumentException::class.java)
            }
        }
    }
}
