package advdevelopment.com.model.data.dto

import advdevelopment.com.model.data.dto.MeaningsDto
import com.google.gson.annotations.SerializedName

class SearchResultDto(
    @field:SerializedName("text") val text: String?,
    @field:SerializedName("meanings") val meanings: List<MeaningsDto?>?
)
