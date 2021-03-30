package com.optiva.media.domain.model

import android.os.Parcelable
import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.android.parcel.Parcelize


@Parcelize
@Xml
data class VideoRecommendation (

		@Attribute val id : Int?,
		@Attribute val externalContentId : String?,
		@Attribute val name : String?,
		@Attribute val rating : Double?,
		@Attribute val ratersCount : Int?,
		@Attribute val prLevel : Int?,
		@Attribute val prName : String?,
		@Attribute val contentType : String?,
		@Attribute val type : String?,
		@Element val genres : Genres?,
		@Element val images : Images?,
		@Element val availabilities : Availabilities?

): Parcelable