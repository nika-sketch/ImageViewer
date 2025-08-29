package com.example.imageviewer.data.repository

import com.example.imageviewer.data.network.ImageViewerService
import com.example.imageviewer.di.DispatcherProvider
import com.example.imageviewer.domain.model.ImageViewerDomain
import com.example.imageviewer.domain.repository.ImageViewerRepository
import kotlinx.coroutines.withContext

class ImageViewerRepositoryImpl(
  private val network: ImageViewerService,
  private val dispatchers: DispatcherProvider
) : ImageViewerRepository {

  override suspend fun getImages(): List<ImageViewerDomain> {
    return withContext(dispatchers.io()) {
      val response = network.fetchImages()
      val body = response.body()
      if (response.isSuccessful && body != null) {
        body.map { apiModel ->
          ImageViewerDomain(id = apiModel.id, title = apiModel.title, url = apiModel.url)
        }
      } else emptyList()
    }
  }
}