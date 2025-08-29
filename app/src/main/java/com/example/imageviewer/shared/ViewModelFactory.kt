package com.example.imageviewer.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <reified VM : ViewModel> viewModelFactory(
  crossinline creator: () -> VM
): ViewModelProvider.Factory {
  return object : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
      if (modelClass.isAssignableFrom(creator().javaClass)) {
        return creator() as T
      }
      error("Unknown ViewModel class")
    }
  }
}