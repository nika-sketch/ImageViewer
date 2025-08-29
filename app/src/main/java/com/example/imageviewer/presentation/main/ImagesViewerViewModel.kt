package com.example.imageviewer.presentation.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imageviewer.di.DispatcherProvider
import com.example.imageviewer.domain.repository.ImageViewerRepository
import com.example.imageviewer.presentation.main.model.ImageViewerState
import com.example.imageviewer.presentation.main.model.ImageViewerState.Success
import com.example.imageviewer.presentation.main.model.ImageViewerState.Initial
import com.example.imageviewer.presentation.main.model.ImageViewerState.Loading
import com.example.imageviewer.presentation.main.model.ImageViewerState.Error
import com.example.imageviewer.presentation.main.model.ImageViewerState.Success.ImageViewerUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ImagesViewerViewModel(
  private val repository: ImageViewerRepository,
  private val dispatchers: DispatcherProvider
) : ViewModel() {

  private val mutableImagesState = MutableStateFlow<ImageViewerState>(Initial)
  val imagesState: StateFlow<ImageViewerState> = mutableImagesState.asStateFlow()

  init {
    viewModelScope.launch {
      mutableImagesState.value = Loading
      val images = repository.getImages()
      withContext(dispatchers.main()) {
        val images = images.map { imageDomain ->
          ImageViewerUi(id = imageDomain.id, title = imageDomain.title, url = imageDomain.image)
        }
        mutableImagesState.value = if (images.isEmpty()) {
          Error(message = "No images found")
        } else {
          Success(images = images)
        }
      }
    }
  }
}
