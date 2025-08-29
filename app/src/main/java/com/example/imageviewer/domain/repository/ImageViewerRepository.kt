package com.example.imageviewer.domain.repository

import com.example.imageviewer.domain.model.ImageViewerDomain

interface ImageViewerRepository {
  suspend fun getImages(): List<ImageViewerDomain>
}