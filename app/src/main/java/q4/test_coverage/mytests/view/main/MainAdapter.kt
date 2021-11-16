package q4.test_coverage.mytests.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import q4.test_coverage.mytests.databinding.ItemMainFragmentRvBinding
import q4.test_coverage.mytests.model.DataModel
import q4.test_coverage.mytests.utils.ImageLoader
import q4.test_coverage.mytests.utils.networkstatus.convertMeaningsToString

class MainAdapter @AssistedInject constructor(
    @Assisted private var onListItemClickListener: OnListItemClickListener,
    @Assisted private var data: List<DataModel>,
    val imageLoader: ImageLoader<ShapeableImageView>
) :
    RecyclerView.Adapter<MainAdapter.RecyclerItemViewHolder>() {


    fun setData(data: List<DataModel>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RecyclerItemViewHolder(
            ItemMainFragmentRvBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(private val vb: ItemMainFragmentRvBinding) :
        RecyclerView.ViewHolder(vb.root) {


        fun bind(data: DataModel) = with(vb) {
            if (layoutPosition != RecyclerView.NO_POSITION) {

                headerTextviewRecyclerItem.text = data.text

                descriptionTextviewRecyclerItem.text =
                    data.meanings?.get(0)?.translation?.translation

                imageLoader.loadInto(
                    "https:" + data.meanings?.get(0)?.previewUrl.toString(), itemWordImage
                )

                transcriptionTextviewRecyclerItem.text = data.meanings?.let {
                    convertMeaningsToString(
                        it
                    )
                }

                itemView.setOnClickListener { openInNewWindow(data) }
            }
        }
    }

    private fun openInNewWindow(listItemData: DataModel) {
        onListItemClickListener.onItemClick(listItemData)
    }

    interface OnListItemClickListener {
        fun onItemClick(data: DataModel)
    }

}