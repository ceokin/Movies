package com.optiva.media.domain.model

import android.os.Parcelable
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.android.parcel.Parcelize

@Parcelize
@Xml
data class PricingMatrix (

	@Element(name = "PricingOption") val pricingOption : PricingOption?
) : Parcelable