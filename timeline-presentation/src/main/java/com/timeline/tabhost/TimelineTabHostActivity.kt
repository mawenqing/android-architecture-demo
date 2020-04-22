package com.timeline.tabhost

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.common.showToast
import com.google.android.material.snackbar.Snackbar
import com.timeline.R
import com.timeline.inject.DaggerTimelineComponent
import com.timeline.inject.TimelineComponent
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class TimelineTabHostActivity : AppCompatActivity(),
    TimelineComponent.ComponentProvider {
    @Inject
    lateinit var pagerAdapter: SectionsPagerAdapter

    @Inject
    lateinit var viewModelFactory: TimelineTabViewModel.Factory

    override val timelineComponent: TimelineComponent
        get() = DaggerTimelineComponent
            .builder()
            .dependency((application as TimelineComponent.HasDependecy).getDependency())
            .activity(this)
            .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        timelineComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupFloatingButton()
        setupTabs()
    }

    private fun setupViewPager() {
        viewPager.adapter = pagerAdapter
        tabs.setupWithViewPager(viewPager)
        viewPager.currentItem = 1
    }

    private fun setupFloatingButton() {
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    private fun setupTabs() {
        ViewModelProviders
            .of(this, viewModelFactory)[TimelineTabViewModel::class.java]
            .apply {
                tabs.observe(this@TimelineTabHostActivity,
                    Observer {
                        pagerAdapter.updateTabs(it)
                        setupViewPager()
                    })
                error.observe(this@TimelineTabHostActivity,
                    Observer {
                        showToast(it)
                    })
            }
    }
}
