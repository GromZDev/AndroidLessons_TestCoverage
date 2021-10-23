package q4.test_coverage.mytests.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import q4.test_coverage.mytests.databinding.ItemPopularFilmsRvBinding
import q4.test_coverage.mytests.presenter.PopularMoviesListPresenter
import q4.test_coverage.mytests.retrofit.ImageLoader

class PopularFilmsRVAdapter(
    private val presenter: PopularMoviesListPresenter, val imageLoader:
    ImageLoader<ShapeableImageView>
) :
    RecyclerView.Adapter<PopularFilmsRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemPopularFilmsRvBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        ).apply {
            itemView.setOnClickListener {
                presenter.itemClickListener?.invoke(this)
            }
        }

    override fun getItemCount() = presenter.getCount()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        presenter.bindView(holder.apply { pos = position })

    inner class ViewHolder(private val vb: ItemPopularFilmsRvBinding) :
        RecyclerView.ViewHolder(vb.root), PopularFilmsItemView {
        override var pos = -1

        override fun setTitle(text: String) = with(vb) {
            itemFilmName.text = text
        }

        override fun loadImage(url: String) {
            imageLoader.loadInto(url, vb.itemFilmImage)
        }

        override fun setRating(rating: Float) = with(vb) {
            itemFilmRating.text = rating.toString()
        }

        override fun setDate(date: String) = with(vb) {
            itemFilmYear.text = date
        }

        override fun setPopularity(votes: Float) = with(vb) {
            itemFilmPopularity.text = votes.toString()
        }
    }

}

