package com.example.qnopytestapp.APIResponse


import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("formData")
    var formData: List<FormData>
)