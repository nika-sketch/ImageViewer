package com.example.imageviewer.data.network

import com.example.imageviewer.BuildConfig
import com.example.imageviewer.data.model.ImageViewerApiModel
import retrofit2.Response
import retrofit2.http.GET

interface ImageViewerService {

  @GET(BuildConfig.FORM_NEST_ENDPOING)
  suspend fun fetchImages(): Response<List<ImageViewerApiModel>>
}