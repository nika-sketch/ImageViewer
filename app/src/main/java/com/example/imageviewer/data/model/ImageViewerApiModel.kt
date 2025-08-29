package com.example.imageviewer.data.model

import androidx.annotation.Keep
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class ImageViewerApiModel(
  val id: Int,
  val title: String,
  val url: String
)