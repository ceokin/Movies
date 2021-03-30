package com.optiva.media.domain.model

import android.os.Parcelable
import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.android.parcel.Parcelize


@Parcelize
@Xml
data class Category (

		@Attribute val categoryId : String?,
		@Attribute val categoryName : String?
): Parcelable