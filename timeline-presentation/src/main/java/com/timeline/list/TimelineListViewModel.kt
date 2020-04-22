package com.timeline.list

import androidx.lifecycle.*
import com.common.BaseViewModel
import com.core.timeline.TimelineInteractor
import kotlinx.coroutines.CoroutineDispatcher

/**
 * @author mawenqing.
 */
class TimelineListViewModel(
    private val interactor: TimelineInteractor,
    mainDispatcher: CoroutineDispatcher
) : BaseViewModel(mainDispatcher) {
    private val mutableTimeline = MutableLiveData<List<TimelineItemModel>>()

    val timeline: LiveData<List<TimelineItemModel>> = mutableTimeline

    fun getTimeline(category: String) {
        // prevent loading data at configuration changes
        if (timeline.value.isNullOrEmpty()) {
            uiJob {
                mutableTimeline.value = interactor.loadTimeline(category, ::handleError)
                    .map {
                        TimelineItemModel(
                            it.id,
                            it.name,
                            it.status,
                            it.likeCount,
                            it.commentCount,
                            it.price,
                            it.photo
                        )
                    }
            }
        }
    }

    class Factory(
        private val interactor: TimelineInteractor,
        private val mainDispatcher: CoroutineDispatcher
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return TimelineListViewModel(interactor, mainDispatcher) as T
        }
    }
}