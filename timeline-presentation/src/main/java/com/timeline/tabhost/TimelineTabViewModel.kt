package com.timeline.tabhost

import androidx.lifecycle.*
import com.common.BaseViewModel
import com.core.timeline.TimelineInteractor
import kotlinx.coroutines.CoroutineDispatcher

/**
 * @author mawenqing.
 */
class TimelineTabViewModel(
    private val interactor: TimelineInteractor,
    mainDispatcher: CoroutineDispatcher
) : BaseViewModel(mainDispatcher) {
    private val mutableTabs = MutableLiveData<List<String>>()

    val tabs: LiveData<List<String>> = mutableTabs

    init {
        uiJob {
            mutableTabs.value = interactor.loadCategories(::handleError)
                .toMutableList()
                .apply {
                    if (count() >= 3) {
                        // change the tab order
                        val tabAll = removeAt(0)
                        add(1, tabAll)
                    }
                }
                .map { it.name }
        }
    }

    class Factory(
        private val interactor: TimelineInteractor,
        private val mainDispatcher: CoroutineDispatcher
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return TimelineTabViewModel(interactor, mainDispatcher) as T
        }
    }
}
