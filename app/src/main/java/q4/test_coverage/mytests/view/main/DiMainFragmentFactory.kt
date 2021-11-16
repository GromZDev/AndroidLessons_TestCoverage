package q4.test_coverage.mytests.view.main

import dagger.assisted.AssistedFactory
import q4.test_coverage.mytests.model.DataModel

@AssistedFactory
interface DiMainFragmentFactory {

    fun create(
        data: List<DataModel>?,
        onListItemClickListener: MainAdapter.OnListItemClickListener
    ): MainAdapter
}