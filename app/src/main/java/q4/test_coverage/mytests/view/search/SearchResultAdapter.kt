package q4.test_coverage.mytests.view.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import q4.test_coverage.mytests.databinding.ListItemBinding
import q4.test_coverage.mytests.model.SearchResult
import q4.test_coverage.mytests.view.search.SearchResultAdapter.SearchResultViewHolder

internal class SearchResultAdapter : RecyclerView.Adapter<SearchResultViewHolder>() {

    private var results: List<SearchResult> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = SearchResultViewHolder(
            ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )


    override fun onBindViewHolder(
        holder: SearchResultViewHolder,
        position: Int
    ) {
        holder.bind(results[position])
    }

    override fun getItemCount(): Int {
        return results.size
    }

    fun updateResults(results: List<SearchResult>) {
        this.results = results
        notifyDataSetChanged()
    }

    internal class SearchResultViewHolder(private val vb: ListItemBinding) :
        RecyclerView.ViewHolder(vb.root) {

        fun bind(searchResult: SearchResult) = with(vb) {
            repositoryName.text = searchResult.fullName
        }
    }
}
