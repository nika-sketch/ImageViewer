package com.example.imageviewer.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imageviewer.di.DispatcherProvider
import com.example.imageviewer.domain.repository.ImageViewerRepository
import com.example.imageviewer.presentation.main.model.ImageViewerState
import com.example.imageviewer.presentation.main.model.ImageViewerState.Error
import com.example.imageviewer.presentation.main.model.ImageViewerState.Loading
import com.example.imageviewer.presentation.main.model.ImageViewerState.Success
import com.example.imageviewer.presentation.main.model.ImageViewerState.Success.ImageViewerUi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ImagesViewerViewModel(
  private val repository: ImageViewerRepository,
  private val dispatchers: DispatcherProvider
) : ViewModel() {

  private val mutableImagesState = MutableStateFlow<ImageViewerState>(Loading)
  val imagesState: StateFlow<ImageViewerState> = mutableImagesState.asStateFlow()

  private val triggerRefreshFlow = MutableSharedFlow<Unit>(replay = 1)

  init {
    triggerRefreshFlow.tryEmit(Unit)
    viewModelScope.launch {
      triggerRefreshFlow.collectLatest {
        val currentState = imagesState.value
        if (currentState !is Success) mutableImagesState.value = Loading
        else mutableImagesState.value = currentState.copy(isRefreshing = true)

        val imagesDomain = repository.getImages()
        withContext(dispatchers.mainImmediate()) {
          val imagesUi = imagesDomain.map { imageDomain ->
            ImageViewerUi(id = imageDomain.id, title = imageDomain.title, url = imageDomain.image)
          }
          mutableImagesState.value = if (imagesUi.isEmpty()) {
            Error(message = "No images found")
          } else {
            Success(images = imagesUi, isRefreshing = false)
          }
        }
      }
    }
  }

  fun refresh() = triggerRefreshFlow.tryEmit(Unit)
}
