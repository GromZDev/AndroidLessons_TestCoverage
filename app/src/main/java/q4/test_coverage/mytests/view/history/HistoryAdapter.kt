package q4.test_coverage.mytests.view.history

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import q4.test_coverage.mytests.databinding.ItemHistoryFragmentRvBinding
import q4.test_coverage.mytests.model.DataModel

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.RecyclerItemViewHolder>() {

    private var data: List<DataModel> = arrayListOf()

    fun setData(data: List<DataModel>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RecyclerItemViewHolder(
            ItemHistoryFragmentRvBinding.inflate(
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

    inner class RecyclerItemViewHolder(private val vb: ItemHistoryFragmentRvBinding) :
        RecyclerView.ViewHolder(vb.root) {

        fun bind(data: DataModel) = with(vb) {
            if (layoutPosition != RecyclerView.NO_POSITION) {

                headerHistoryItem.text = data.text
//                transcriptionHistoryItem.text = data.meanings?.get(0)?.translation?.translation
//                descriptionHistoryItem.text = data.meanings?.get(0)?.translation?.translation
                itemView.setOnClickListener {
                    Toast.makeText(
                        itemView.context, "Когда-нибудь перейдём в описание: ${data.text}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}