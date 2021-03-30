package com.optiva.media.domain.model

import android.os.Parcelable
import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.android.parcel.Parcelize

@Parcelize
@Xml
data class PricingOption (

	@Attribute val id : Long?,
	@Attribute val name : String?,
	@Attribute val rentalPeriod : Long?,
	@Attribute val price : Double?,
	@Attribute val rentalPeriodUnit : Integer?

) : Parcelable