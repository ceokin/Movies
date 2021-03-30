package com.optiva.media.ui.movieDetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.optiva.media.R
import com.optiva.media.common.Constants.DETAIL_ID_FROM_RECOM
import com.optiva.media.core.Resource
import com.optiva.media.databinding.MovieDetailsFragmentBinding
import com.optiva.media.domain.model.Movie
import com.optiva.media.domain.model.VideoRecommendation
import com.optiva.media.ui.presentation.MainViewModel
import com.optiva.media.utils.ImageUtils
import com.optiva.media.utils.WrapContentLinearLayoutManager
import com.optiva.media.utils.hide
import com.optiva.media.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.movie_details_fragment.*
import kotlinx.coroutines.launch
import java.util.*

/**
 * Created by Cesar Conde
 */

@AndroidEntryPoint
class MovieDetailsFragment : Fragment(R.layout.movie_details_fragment), RecomAdapter.OnClickListener  {
    private val mViewModel by activityViewModels<MainViewModel>()

    private lateinit var mMovie:       Movie
    private var          mIsFavorited: Boolean? = null
    private lateinit var mRecomAdapter: RecomAdapter
    private lateinit var mBinding: MovieDetailsFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireArguments().let {
            MovieDetailsFragmentArgs.fromBundle(it).also { args ->
                mMovie = args.movie
            }
        }

        mRecomAdapter = RecomAdapter(requireContext(), this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding = MovieDetailsFragmentBinding.bind(view)

        mBinding.recomMoviesRv.layoutManager = WrapContentLinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        mBinding.recomMoviesRv.adapter = mRecomAdapter

        mBinding.favoriteFb.setOnClickListener {
            val isFavorited = mIsFavorited ?: return@setOnClickListener

            mViewModel.saveOrDeleteFavoriteMovie(mMovie)
            this.mIsFavorited = !isFavorited
            updateButtonIcon()
        }

        checkIsFavorite()

        mMovie.externalId?.let { mViewModel.fetchMovieDetail(it) }
        mViewModel.movieDetail.observe(viewLifecycleOwner, {
            updateUI(mBinding, it)
        })

        mMovie.assetExternalId?.let { mViewModel.fetchRecommendationList(it) }
        mViewModel.recommendationList.observe(viewLifecycleOwner, {
            updateRecom(mBinding, it)
        })
    }

    private fun checkIsFavorite(){
        viewLifecycleOwner.lifecycleScope.launch {
            mIsFavorited = mViewModel.isMovieFavorite(mMovie)
            updateButtonIcon()
        }
    }

    private fun updateButtonIcon() {
        val isFavorited = mIsFavorited ?: return

        mBinding.favoriteFb.setImageResource(
                when {
                    isFavorited -> R.drawable.ic_delete_white
                    else -> R.drawable.ic_favorite_white
                }
        )
    }

    private fun updateUI(binding: MovieDetailsFragmentBinding, resource: Resource<Movie?>){

        when (resource) {
            is Resource.Success -> {
                mMovie = resource.data!!
                checkIsFavorite()
                val image = ImageUtils.pickDetailsImage(resource.data.attachments?.attachment)
                photo_iv.load(image?.let { ImageUtils.imageUrl(it) }) {
                    crossfade(true)
                }

                binding.titleDetailTv.text = resource.data.name
                binding.descriptionDetailTv.text = resource.data.description
                binding.contentCl.show()
            }
            is Resource.Failure -> {
                binding.contentCl.hide()
                binding.errorContainer.root.show()
            }
        }
    }

    private fun updateRecom(binding: MovieDetailsFragmentBinding, resource: Resource<List<VideoRecommendation>?>){

        when (resource) {
            is Resource.Success -> {
                if (resource.data.isNullOrEmpty()) {
                    binding.headerRecomTv.hide()
                } else {
                    mRecomAdapter.setRecomList(resource.data)
                    binding.headerRecomTv.show()
                }
                binding.errorRecomContainer.root.hide()
            }
            is Resource.Failure -> {
                binding.errorRecomContainer.root.show()
            }
        }
    }

    override fun onRecomClick(recom: VideoRecommendation) {
        mViewModel.fetchMovieDetail(recom.externalContentId + DETAIL_ID_FROM_RECOM)
        recom.externalContentId?.let { mViewModel.fetchRecommendationList(it) }
    }
}