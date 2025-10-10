package com.example.mypokemonapplication.models

data class UISkeletonData(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isDataEmpty: Boolean = false
)