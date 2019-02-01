package com.saam.rickandmorty.locations.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saam.rickandmorty.R
import com.saam.rickandmorty.api.models.Location
import com.saam.rickandmorty.locations.presentation.LocationsViewModel
import com.saam.rickandmorty.core.module.viewmodel.ViewModelFactory
import com.saam.rickandmorty.infrastructure.adapter.NetworkState
import com.saam.rickandmorty.locations.adapter.LocationsAdapter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_locations.*
import javax.inject.Inject

class LocationsFragment: Fragment() {
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
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LocationsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_locations, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
    }

    private fun setupAdapter() {
        val linearLayoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        adapter = LocationsAdapter {
            viewModel.retry()
        }
        locations.layoutManager = linearLayoutManager
        locations.adapter = adapter
        viewModel.getLocationList().observe(
                this,
                Observer<PagedList<Location>> { adapter.submitList(it) }
        )
        viewModel.getNetworkState().observe(
                this,
                Observer<NetworkState> { adapter.setNetworkState(it) }
        )
    }


}
