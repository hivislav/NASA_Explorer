package ru.hivislav.nasaexplorer.model.entities

import com.google.gson.annotations.SerializedName

data class MarsPhotoDTO(
    val id: Int,
    @SerializedName("img_src")
    val imgSrc: String,
    @SerializedName("earth_date")
    val earthDate: String
)
