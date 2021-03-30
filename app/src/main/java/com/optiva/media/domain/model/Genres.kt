package com.optiva.media.domain.model

import android.os.Parcelable
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.android.parcel.Parcelize


@Parcelize
@Xml
data class Genres (

	@Element(name = "Genre") val genre : List<Genre>?

): Parcelable