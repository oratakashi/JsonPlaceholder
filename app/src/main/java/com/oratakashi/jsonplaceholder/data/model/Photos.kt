package com.oratakashi.jsonplaceholder.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Photos(
    @SerializedName("albumId") val albumId : String,
    @SerializedName("id") @PrimaryKey(autoGenerate = false) val id : Int,
    @SerializedName("title") val title : String,
    @SerializedName("url") val url : String,
    @SerializedName("thumbnailUrl") val thumbnailUrl : String
)
