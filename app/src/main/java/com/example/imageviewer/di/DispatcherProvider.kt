package com.example.imageviewer.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatcherProvider {

  fun io(): CoroutineDispatcher
  fun main(): CoroutineDispatcher
  fun default(): CoroutineDispatcher

  class Default : DispatcherProvider {
    override fun io(): CoroutineDispatcher = Dispatchers.IO
    override fun main(): CoroutineDispatcher = Dispatchers.Main
    override fun default(): CoroutineDispatcher = Dispatchers.Default
  }
}