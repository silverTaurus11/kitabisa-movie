package com.project.myapplication.utils

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.appbar.AppBarLayout
import com.project.myapplication.R
import java.text.SimpleDateFormat
import java.util.*

object MyHelpers {
    fun getFullMovieImage(image: String?, resources: Resources): String{
        return if(image.isNullOrEmpty()){
            resources.getString(R.string.empty_movie_image)
        } else {
            resources.getString(R.string.image_base_url) + image
        }
    }

    fun View.toVisible(isVisible: Boolean){
        if(isVisible){
            this.visibility = View.VISIBLE
        } else{
            this.visibility = View.GONE
        }
    }

    fun ImageView.setImageUrl(url: String?){
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(this)
    }

    fun printReviewDate(dateString: String?): String{
        if(dateString == null) return ""
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
        val date = inputFormat.parse(dateString)
        if(date != null){
            return outputFormat.format(date)
        }
        return ""
    }

    fun AppBarLayout.toScrollEnabled(isEnabled: Boolean){
        val params = layoutParams as CoordinatorLayout.LayoutParams
        if (params.behavior == null)
            params.behavior = AppBarLayout.Behavior()
        val behaviour = params.behavior as AppBarLayout.Behavior
        behaviour.setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
            override fun canDrag(appBarLayout: AppBarLayout): Boolean {
                return isEnabled
            }
        })
    }

    @SuppressLint("ClickableViewAccessibility")
    fun RecyclerView.toScrollEnabled(isEnabled: Boolean){
        setOnTouchListener { _, _ ->
            !isEnabled
        }
    }

    fun TextView.setTextHtml(content: String?){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            this.text = Html.fromHtml(
                (content
                    ?: "").replace("\\n", "<br>"), Html.FROM_HTML_MODE_LEGACY
            ).toString()
        } else {
            this.text = Html.fromHtml((content ?: "").replace("\\n", "<br>")).toString()
        }
    }
}