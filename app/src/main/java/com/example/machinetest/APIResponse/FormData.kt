package com.example.qnopytestapp.APIResponse


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class FormData(
    @SerializedName("extField1")
    var extField1: String,

    @SerializedName("FieldInputType")
    var fieldInputType: String,

    @SerializedName("fieldParameterId")
    var fieldParameterId: Int,

    @SerializedName("fieldParameterLabel")
    var fieldParameterLabel: String,

    @SerializedName("mobileAppId")
    var mobileAppId: Int,

    @PrimaryKey
    @SerializedName("RowOrder")
    var rowOrder: Int,

    @SerializedName("siteId")
    var siteId: Int,

    @SerializedName("stringValue")
    var stringValue: String,

    @SerializedName("userId")
    var userId: Int
)