package com.optiva.media.domain.model.response

import android.os.Parcelable
import com.optiva.media.domain.model.Movie
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.android.parcel.Parcelize


@Parcelize
@Xml
data class MoviesResponse (
	@Element(name = "VODService") val movies : List<Movie>
) : Parcelable