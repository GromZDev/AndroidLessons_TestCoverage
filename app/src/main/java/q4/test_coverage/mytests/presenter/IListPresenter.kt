package q4.test_coverage.mytests.presenter

import q4.test_coverage.mytests.view.RVItemView

interface IListPresenter<V : RVItemView> {
    var itemClickListener: ((V) -> Unit)?
    fun bindView(view: V)
    fun getCount(): Int
}