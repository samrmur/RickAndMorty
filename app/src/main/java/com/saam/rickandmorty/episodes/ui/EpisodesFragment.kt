package com.saam.rickandmorty.episodes.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saam.rickandmorty.R
import com.saam.rickandmorty.api.models.Episode
import com.saam.rickandmorty.episodes.presentation.EpisodesViewModel
import com.saam.rickandmorty.core.module.viewmodel.ViewModelFactory
import com.saam.rickandmorty.episodes.adapter.EpisodesAdapter
import com.saam.rickandmorty.infrastructure.adapter.NetworkState
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_episodes.*
import javax.inject.Inject

class EpisodesFragment: Fragment() {
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
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(EpisodesViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_episodes, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle? ) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
    }

    private fun setupAdapter() {
        val linearLayoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        adapter = EpisodesAdapter {
            viewModel.retry()
        }
        episodes.layoutManager = linearLayoutManager
        episodes.adapter = adapter
        viewModel.getEpisodeList().observe(
                this,
                Observer<PagedList<Episode>> { adapter.submitList(it) }
        )
        viewModel.getNetworkState().observe(
                this,
                Observer<NetworkState> { adapter.setNetworkState(it) }
        )
    }


}