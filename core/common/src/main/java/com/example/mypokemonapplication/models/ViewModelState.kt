package com.example.mypokemonapplication.models

data class ViewModelState<T>(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val data: T? = null,
    val errorMessage: String? = null
)