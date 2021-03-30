package com.optiva.media.domain.model.response

import android.os.Parcelable
import com.optiva.media.domain.model.VideoRecommendation
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.android.parcel.Parcelize


@Parcelize
@Xml
data class RecommendationsResponse (

		@Element(name = "VideoRecommendation") val videoRecommendation : List<VideoRecommendation>?
): Parcelable