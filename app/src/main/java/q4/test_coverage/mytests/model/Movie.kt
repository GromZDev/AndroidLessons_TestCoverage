package q4.test_coverage.mytests.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    @SerializedName("id") @Expose val id: Long? = null,
    @SerializedName("title") @Expose val title: String? = null,
    @SerializedName("overview") @Expose val overview: String? = null,
    @SerializedName("poster_path") @Expose val posterPath: String? = null,
    @SerializedName("backdrop_path") @Expose val backdropPath: String? = null,
    @SerializedName("vote_average") @Expose val rating: Float? = null,
    @SerializedName("release_date") @Expose val releaseDate: String? = null,
    @SerializedName("popularity") @Expose val popularity: Float? = null
): Parcelable