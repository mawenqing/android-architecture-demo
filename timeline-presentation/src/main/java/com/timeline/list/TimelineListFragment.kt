package com.timeline.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.common.showToast
import com.timeline.R
import com.timeline.inject.TimelineComponent
import kotlinx.android.synthetic.main.fragment_main.view.*
import javax.inject.Inject

/**
 * The fragment displaying different categories of timeline items.
 *
 * @author mawenqing.
 */
const val ARGUMENT_KEY_CATEGORY = "category"

class TimelineListFragment : Fragment() {
    @Inject
    lateinit var adapter: TimelineListAdapter

    @Inject
    lateinit var viewModelFactory: TimelineListViewModel.Factory

    private lateinit var viewModel: TimelineListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as TimelineComponent.ComponentProvider).timelineComponent
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_main, container, false)
        .apply {
            recyclerView.layoutManager = GridLayoutManager(context, 2)
            recyclerView.adapter = adapter
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeTimeline()
    }

    private fun observeTimeline() {
        val category = arguments?.getString(ARGUMENT_KEY_CATEGORY) ?: ""
        viewModel = ViewModelProviders.of(this, viewModelFactory)[TimelineListViewModel::class.java]
            .apply {
                getTimeline(category)
                timeline.observe(viewLifecycleOwner, Observer {
                    adapter.updateData(it)
                })
                error.observe(viewLifecycleOwner, Observer {
                    showToast(it)
                })
            }
    }
}
