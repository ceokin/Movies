package com.optiva.media.common

/**
 * Created by Cesar Conde
 */

object Constants {

    //API
    const val BASE_URL = "https://smarttv.orangetv.orange.es/stv/api/"

    const val GET_ALL_MOVIES_URL = "rtv/v1/GetUnifiedList?&statuses=published&definitions=SD;HD;4K&external_category_id=SED_3880&filter_empty_categories=true"

    const val GET_MOVIE_DETAIL_URL = "rtv/v1/GetVideo"

    const val GET_MOVIE_RECOMMENDATIONS_URL = "reco/v1/GetVideoRecommendationList"

    const val BASE_URL_IMAGES = BASE_URL + "rtv/v1/images"

    const val IMAGE_COVER_NAME = "COVER4_1"

    const val IMAGE_DETAIL_NAME = "APP_SLSHOW_3"

    const val DETAIL_ID_FROM_RECOM = "_PAGE_HD"

    //ROOM
    const val DATABASE_NAME = "movies_table"
}