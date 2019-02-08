package com.saam.rickandmorty.episodeslist.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saam.rickandmorty.R
import com.saam.rickandmorty.api.models.Episode
import com.saam.rickandmorty.episodeslist.presentation.EpisodesViewModel
import com.saam.rickandmorty.core.module.viewmodel.ViewModelFactory
import com.saam.rickandmorty.episodeslist.adapter.EpisodesAdapter
import com.saam.rickandmorty.infrastructure.adapter.NetworkState
import com.saam.rickandmorty.infrastructure.adapter.NetworkStatus
import com.saam.rickandmorty.nav.ui.NavActivity
import com.saam.rickandmorty.util.views.CustomDividerDecoration
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_episodes.*
import timber.log.Timber
import javax.inject.Inject

class EpisodesFragment: Fragment(), View.OnClickListener {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: EpisodesViewModel
    private lateinit var adapter: EpisodesAdapter

    companion object {
        const val TAG: String = "EpisodesFragment"

        fun newInstance(): Fragment {
            return EpisodesFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(EpisodesViewModel::class.java)
        setupAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_episodes, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle? ) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    override fun onClick(view: View) {
        viewModel.retry()
    }

    private fun setupAdapter() {
        adapter = EpisodesAdapter { viewModel.retry() }
    }

    private fun setupViews() {
        (activity as NavActivity).setTitle(getString(R.string.title_episodes))

        error_message.setOnClickListener(this)

        with(episodes) {
            adapter = this@EpisodesFragment.adapter
            layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
            addItemDecoration(CustomDividerDecoration(context, 0, 0))
            itemAnimator = DefaultItemAnimator()
        }

        viewModel.getInitialNetworkState().observe(this, Observer<NetworkState> { state ->
            setViewVisibility(state)
            adapter.setNetworkState(state)
        })

        viewModel.getNetworkState().observe(this, Observer<NetworkState> { state ->
            adapter.setNetworkState(state)
        })

        viewModel.getDataList().observe(this, Observer<PagedList<Episode>> { data ->
            adapter.submitList(data)
        })
    }

    private fun setViewVisibility(state: NetworkState) {
        when(state.status) {
            NetworkStatus.RUNNING -> {
                loading_bar.visibility = View.VISIBLE
                error_message.visibility = View.GONE
            }
            NetworkStatus.SUCCESS -> loading_bar.visibility = View.GONE
            NetworkStatus.FAILED -> {
                loading_bar.visibility = View.GONE
                error_message.visibility = View.VISIBLE

                Timber.e(state.message)
            }
        }
    }
}