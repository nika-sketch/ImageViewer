package com.example.imageviewer

import android.app.Application
import com.example.imageviewer.data.repository.ImageViewerRepositoryImpl
import com.example.imageviewer.di.DispatcherProvider
import com.example.imageviewer.di.NetworkModule
import com.example.imageviewer.domain.repository.ImageViewerRepository

class ImageViewerApp : Application() {

  companion object {
    lateinit var imageViewerRepository: ImageViewerRepository
  }

  override fun onCreate() {
    super.onCreate()
    imageViewerRepository = ImageViewerRepositoryImpl(
      network = NetworkModule.NetworkModuleImpl().provideFormNestService(),
      dispatchers = DispatcherProvider.Default(),
    )
  }
}