package com.optiva.media.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.android.parcel.Parcelize

@Parcelize
@Xml
data class Movie (

	@Attribute val id : Long,
	@Attribute val name : String?,
	@Attribute val description : String?,
	@Attribute val contentProvider : String?,
	@Attribute val imageFile : String?,
	@Attribute val imageFileSmall : String?,
	@Attribute val duration : Long?,
	@Attribute val categoryId : Long?,
	@Attribute val prLevel : Long?,
	@Attribute val prName : String?,
	@Attribute val advisories : String?,
	@Attribute val windowStart : Long?,
	@Attribute val windowEnd : Long?,
	@Attribute val assetId : Long?,
	@Attribute val year : Long?,
	@Attribute val reviewerRating : String?,
	@Attribute val template : String?,
	@Attribute val adsInfo : String?,
	@Attribute val flags : Long?,
	@Attribute val shortName : String?,
	@Attribute val status : Long?,
	@Attribute val externalChannelId : String?,
	@Attribute val broadcastTime : Long?,
	@Attribute val removalDate : Long?,
	@Attribute val seriesName : String?,
	@Attribute val seriesSeason : String?,
	@Attribute val discountId : String?,
	@Attribute val externalId : String?,
	@Attribute val assetExternalId : String?,
	@Element val pricingMatrix : PricingMatrix?,
	@Element val metaData : MetaData?,
	@Element val extraFields : ExtraFields?,
	@Element val attachments : Attachments?,
	@Element val formats : Formats?,
	@Attribute var isFavorite : Boolean = false
) : Parcelable

@Entity(tableName = "favoritesTable")
data class FavoritesEntity(
	@PrimaryKey
	val movieId: Long,
)

fun Movie.asFavoriteEntity(): FavoritesEntity =
	FavoritesEntity(this.id)

fun List<FavoritesEntity>.asMovieIdList(): List<Long> = this.map {
	it.movieId
}

