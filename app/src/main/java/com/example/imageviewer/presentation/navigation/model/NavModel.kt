package com.example.imageviewer.presentation.navigation.model

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object MainScreen : NavKey

@Serializable
data class ImageDetail(
  val title: String,
  val url: String
) : NavKey
