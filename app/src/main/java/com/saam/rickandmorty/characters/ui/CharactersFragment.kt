package com.saam.rickandmorty.characters.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.saam.rickandmorty.R
import com.saam.rickandmorty.api.models.Character
import com.saam.rickandmorty.characters.adapter.CharactersAdapter
import com.saam.rickandmorty.characters.presentation.CharactersViewModel
import com.saam.rickandmorty.core.module.viewmodel.ViewModelFactory
import com.saam.rickandmorty.infrastructure.adapter.NetworkState
import com.saam.rickandmorty.infrastructure.adapter.NetworkStatus
import com.saam.rickandmorty.util.views.CustomDividerDecoration
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_characters.*
import javax.inject.Inject

class CharactersFragment: Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: CharactersViewModel
    private lateinit var adapter: CharactersAdapter

    companion object {
        const val TAG: String = "CharactersFragment"

        fun newInstance(): Fragment {
            return CharactersFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CharactersViewModel::class.java)
        setupAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_characters, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupAdapter() {
        adapter = CharactersAdapter { viewModel.retry() }
    }

    private fun setupViews() {
        with(characters) {
            adapter = this@CharactersFragment.adapter
            layoutManager = LinearLayoutManager(this.context)
            addItemDecoration(CustomDividerDecoration(this.context, resources.getDimensionPixelSize(R.dimen.start_divider_margin), 0))
            itemAnimator = DefaultItemAnimator()
        }

        viewModel.getInitialNetworkState().observe(this, Observer<NetworkState> { state ->
            setViewVisibility(state)
            adapter.setNetworkState(state)
        })

        viewModel.getNetworkState().observe(this, Observer<NetworkState> { state ->
            adapter.setNetworkState(state)
        })

        viewModel.getCharacterList().observe(this, Observer<PagedList<Character>> { state ->
            adapter.submitList(state)
        })
    }

    private fun setViewVisibility(state: NetworkState) {
        when(state.status) {
            NetworkStatus.RUNNING -> loading_bar.visibility = View.VISIBLE
            NetworkStatus.SUCCESS -> loading_bar.visibility = View.GONE
            NetworkStatus.FAILED -> loading_bar.visibility = View.GONE
        }
    }
}
