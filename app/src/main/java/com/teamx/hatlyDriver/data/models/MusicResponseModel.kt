package com.teamx.hatlyDriver.data.models

data class MusicResponseModel(
    val resultCount: Int,
    val results: List<MusicModel>?,
)