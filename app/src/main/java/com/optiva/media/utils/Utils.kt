package com.optiva.media.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.optiva.media.common.Constants
import com.optiva.media.domain.model.Attachment
import com.optiva.media.domain.model.Image

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

inline fun <T : View> T.showIf(condition: (T) -> Boolean) {
    if (condition(this)) {
        show()
    } else {
        hide()
    }
}

inline fun <T : View> T.hideIf(condition: (T) -> Boolean) {
    if (condition(this)) {
        hide()
    } else {
        show()
    }
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}


object ImageUtils {
    fun imageUrl(url : String): String { return Constants.BASE_URL_IMAGES + url }

    fun pickCoverImage(attachments : List<Attachment>?): String? {
        var image = attachments!![0].value

        if (attachments.any { it.name == Constants.IMAGE_COVER_NAME }){
            image = attachments?.find { it.name == Constants.IMAGE_COVER_NAME }?.value
        }
        return image
    }

    fun pickDetailsImage(attachments : List<Attachment>?): String? {
        var image = attachments!![0].value

        if (attachments.any { it.name == Constants.IMAGE_DETAIL_NAME }){
            image = attachments?.find { it.name == Constants.IMAGE_DETAIL_NAME }?.value
        }
        return image
    }

    fun pickRecomImage(images: List<Image>?): String? {
        var image = images!![0].value

        if (images.any { it.name == Constants.IMAGE_COVER_NAME }){
            image = images?.find { it.name == Constants.IMAGE_COVER_NAME }?.value
        }
        return image
    }
}