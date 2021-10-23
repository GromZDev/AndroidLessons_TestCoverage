package q4.test_coverage.mytests.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetMoviesResponse(
    @SerializedName("page") @Expose val page: Int,
    @SerializedName("results") @Expose val movies: List<Movie>,
    @SerializedName("total_pages") @Expose val pages: Int
): Parcelable
