package q4.test_coverage.mytests.view.description

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import q4.test_coverage.mytests.R
import q4.test_coverage.mytests.databinding.FragmentDescriptionBinding
import q4.test_coverage.mytests.model.DataModel
import q4.test_coverage.mytests.utils.networkstatus.convertMeaningsToString
import q4.test_coverage.mytests.utils.networkstatus.convertNoteToString

class DescriptionFragment : Fragment() {

    companion object {
        private const val BUNDLE_EXTRA = "MY_Word_Description"
        fun newInstance(data: DataModel) = DescriptionFragment().apply {
            arguments = bundleOf(BUNDLE_EXTRA to data)
        }
    }

    private val data: DataModel by lazy {
        arguments?.getParcelable<DataModel>("MY_Word_Description") as DataModel
    }

    private var _binding: FragmentDescriptionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentDescriptionBinding.inflate(inflater, container, false).also {
        _binding = it

    }.root

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setData() {

        binding.descriptionHeader.text = data.text
        binding.descriptionTextview.text = data.meanings?.let {
            convertMeaningsToString(it)
        }
        binding.transcriptionTextview.text = data.meanings?.get(0)?.transcription
        binding.noteTextview.text = data.meanings?.let {
            convertNoteToString(it)
        }
        val imageLink = data.meanings?.get(0)?.imageUrl.toString()

        usePicassoToLoadPhoto(binding.descriptionImageview, imageLink)
    }

    private fun usePicassoToLoadPhoto(imageView: ImageView, imageLink: String) {
        Picasso.get().load("https:$imageLink")
            .placeholder(R.drawable.ic_no_photo_vector).fit().centerCrop()
            .into(imageView, object : Callback {
                override fun onSuccess() {
                }

                override fun onError(e: Exception?) {
                    imageView.setImageResource(R.drawable.ic_load_error_vector)
                }
            })
    }

}















