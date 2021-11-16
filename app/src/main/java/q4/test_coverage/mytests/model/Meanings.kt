package q4.test_coverage.mytests.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class Meanings(
    @field:SerializedName("translation") val translation: Translation?,
    @field:SerializedName("previewUrl") val previewUrl: String?,
    @field:SerializedName("imageUrl") val imageUrl: String?,
    @field:SerializedName("transcription") val transcription: String?
) : Parcelable