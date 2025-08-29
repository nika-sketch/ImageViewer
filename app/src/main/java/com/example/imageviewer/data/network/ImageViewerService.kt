package com.example.imageviewer.data.network

import com.example.imageviewer.data.model.ImageViewerApiModel
import retrofit2.Response

interface ImageViewerService {

  suspend fun fetchImages(): Response<List<ImageViewerApiModel>>
}