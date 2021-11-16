package q4.test_coverage.mytests.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class Translation(
    @field:SerializedName("text") val translation: String?,
    @field:SerializedName("note") val note: String?,
) : Parcelable
