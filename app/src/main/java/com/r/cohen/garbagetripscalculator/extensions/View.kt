package com.r.cohen.garbagetripscalculator.extensions

import android.view.View
import androidx.databinding.BindingAdapter

// a generic binding adapter for toggling the visibility of a view
@BindingAdapter("visibleGone")
fun View.setVisibleGone(visible: Boolean) {
    this.visibility =
        if (visible) View.VISIBLE
        else View.GONE
}