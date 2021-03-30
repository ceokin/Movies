package com.optiva.media.domain.model

import android.os.Parcelable
import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.android.parcel.Parcelize

@Parcelize
@Xml
data class ServiceAvailability (

		@Attribute val videoId : String?,
		@Attribute val serviceId : String?,
		@Attribute val startTime : Int?,
		@Attribute val endTime : Int?,
		@Element val categories : Categories?,
		@Element val images : Images?
): Parcelable