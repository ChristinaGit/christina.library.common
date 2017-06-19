package moe.christina.common.android.extension

import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity

object ActivityExtension {
    fun AppCompatActivity.setSubtitle(@StringRes subtitleId: Int) {
        actionBar?.setSubtitle(subtitleId)
        supportActionBar?.setSubtitle(subtitleId)
    }

    fun AppCompatActivity.setSubtitle(subtitle: String?) {
        actionBar?.subtitle = subtitle
        supportActionBar?.subtitle = subtitle
    }
}
