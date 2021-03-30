package com.optiva.media.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.optiva.media.R
import com.optiva.media.core.Resource
import com.optiva.media.databinding.MainFragmentBinding
import com.optiva.media.domain.model.Movie
import com.optiva.media.ui.presentation.MainViewModel
import com.optiva.media.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.*


/**
 * Created by Cesar Conde
 */

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment),
    MainAdapter.OnClickListener{
    private          val mViewModel by activityViewModels<MainViewModel>()
    private lateinit var mMainAdapter: MainAdapter
    private lateinit var mFavAdapter: MainAdapter
    private lateinit var mBinding: MainFragmentBinding
    private          var mIsPortrait: Boolean = true
    private          var mIsFavoriteVisible: Boolean = false
    private lateinit var mMenu: Menu

    private var mColumnCount = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        mMainAdapter = MainAdapter(requireContext(), this)
        mFavAdapter = MainAdapter(requireContext(), this)

        mIsPortrait = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding = MainFragmentBinding.bind(view)

        mBinding.movieRv.layoutManager =  when {
            mColumnCount <= 1 -> LinearLayoutManager(context)
            else -> GridLayoutManager(context, mColumnCount)
        }

        mBinding.favoriteRv.layoutManager =  when {
            mColumnCount <= 1 -> LinearLayoutManager(context)
            else -> GridLayoutManager(context, mColumnCount)
        }

        mBinding.movieRv.adapter    = mMainAdapter
        mBinding.favoriteRv.adapter = mFavAdapter

        mBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                mViewModel.searchMovies(newText)
                return false
            }
        })

        val searchCloseButtonId: Int = mBinding.searchView.context.resources
                .getIdentifier("android:id/search_close_btn", null, null)
        val closeButton: ImageView = this.searchView.findViewById(searchCloseButtonId) as ImageView

        closeButton.setOnClickListener {
            mBinding.searchView.setQuery("", false)
            mBinding.searchView.hideKeyboard()
            mViewModel.searchMovies("")
        }

        if (mIsPortrait){
            mIsFavoriteVisible = false
            mBinding.favoriteRv.hide()
            mBinding.movieRv.show()
        }else{
            mBinding.favoriteRv.show()
            mBinding.favoriteRv.show()
        }

        mViewModel.fetchMovieList.observe(viewLifecycleOwner, Observer { result ->
            mBinding.progressBar.showIf { result is Resource.Loading }

            when (result) {
                is Resource.Loading -> {
                    mBinding.emptyContainer?.root?.hide()
                }
                is Resource.Success -> {
                    if (result.data.isEmpty()) {
                        mBinding.movieRv.hide()
                        mBinding.emptyContainer?.root?.show()
                        return@Observer
                    }
                    mBinding.movieRv.show()
                    mMainAdapter.setMovieList(result.data)
                    mFavAdapter.setMovieList(filterFavorites(result.data))
                    mBinding.emptyContainer?.root?.hide()
                }
                is Resource.Failure -> {
                    mBinding.movieRv.hide()
                    mBinding.favoriteRv.hide()
                    mBinding.errorContainer?.root?.show()
                }
            }
        })

        mViewModel.filteredMovies.observe(viewLifecycleOwner, {
            mMainAdapter.setMovieList(it)
            mFavAdapter.setMovieList(filterFavorites(it))
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
        menu.findItem(R.id.favorites).isVisible = mIsPortrait
        menu.findItem(R.id.all).isVisible = false
        mMenu = menu
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorites -> {
                if (!mIsFavoriteVisible) {
                    mBinding.favoriteRv.show()
                    mBinding.movieRv.hide()

                    mIsFavoriteVisible = true
                    item.isVisible = false
                    mMenu.findItem(R.id.all).isVisible = true
                }
                false
            }
            R.id.all -> {
                if (mIsFavoriteVisible) {
                    mBinding.favoriteRv.hide()
                    mBinding.movieRv.show()

                    mIsFavoriteVisible = false
                    item.isVisible = false
                    mMenu.findItem(R.id.favorites).isVisible = true
                }
                false
            }
            else -> false
        }
    }

    private fun filterFavorites(allMovies: List<Movie>) : List<Movie>{
        return allMovies.filter { it.isFavorite }
    }

    override fun onMovieClick(movie: Movie) {
        navigateToDetail(movie)
    }

    override fun onFavItemClick(movie: Movie) {
        mBinding.searchView.setQuery("", false)
        mBinding.searchView.hideKeyboard()
        mViewModel.searchMovies("")
        mViewModel.saveOrDeleteFavoriteMovie(movie)
    }

    private fun navigateToDetail(movie: Movie){
        findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToMovieDetailsFragment(
                        movie
                )
        )
    }
}