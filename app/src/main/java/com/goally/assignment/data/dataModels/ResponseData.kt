package com.goally.assignment.data.dataModels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ResponseData {
    @SerializedName("response_code")
    @Expose
    var responseCode: Int? = null

    @SerializedName("results")
    @Expose
    var results: List<QuestionClass>? = null
}