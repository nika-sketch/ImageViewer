package com.example.imageviewer.presentation.main.model

sealed class ImageViewerState {
  object Loading : ImageViewerState()

  data class Success(
    val images: List<ImageViewerUi>
  ) : ImageViewerState() {
    data class ImageViewerUi(
      val id: Int,
      val title: String,
      val url: String,
    )
  }

  data class Error(val message: String) : ImageViewerState()
}
