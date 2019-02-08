package com.saam.rickandmorty.locationslist.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.saam.rickandmorty.R
import com.saam.rickandmorty.api.models.Location
import com.saam.rickandmorty.locationslist.presentation.LocationsViewModel
import com.saam.rickandmorty.core.module.viewmodel.ViewModelFactory
import com.saam.rickandmorty.infrastructure.adapter.NetworkState
import com.saam.rickandmorty.infrastructure.adapter.NetworkStatus
import com.saam.rickandmorty.locationslist.adapter.LocationsAdapter
import com.saam.rickandmorty.nav.ui.NavActivity
import com.saam.rickandmorty.util.views.CustomDividerDecoration
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_locations.*
import timber.log.Timber
import javax.inject.Inject

class LocationsFragment: Fragment(), View.OnClickListener {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: LocationsViewModel
    private lateinit var adapter: LocationsAdapter

    companion object {
        const val TAG: String = "LocationsFragment"

        fun newInstance(): Fragment {
            return LocationsFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LocationsViewModel::class.java)
        setupAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_locations, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    override fun onClick(view: View) {
        viewModel.retry()
    }

    private fun setupAdapter() {
        adapter = LocationsAdapter { viewModel.retry() }
    }

    private fun setupViews() {
        (activity as NavActivity).setTitle(getString(R.string.title_locations))

        error_message.setOnClickListener(this)

        with(locations) {
            adapter = this@LocationsFragment.adapter
            layoutManager = LinearLayoutManager(this.context)
            addItemDecoration(CustomDividerDecoration(this.context, 0, 0))
            itemAnimator = DefaultItemAnimator()
        }

        viewModel.getInitialNetworkState().observe(this, Observer<NetworkState> { state ->
            setViewVisibility(state)
            adapter.setNetworkState(state)
        })
        viewModel.getNetworkState().observe(this, Observer<NetworkState> { state ->
            adapter.setNetworkState(state)
        })
        viewModel.getDataList().observe(this, Observer<PagedList<Location>> { data ->
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
