package com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView

class AddOrModifyRealEstateAutocompleteAdapter : BaseAdapter(), Filterable {

    private var items = emptyList<AddOrModifyRealEstateAutocompleteItemViewState>()

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): AddOrModifyRealEstateAutocompleteItemViewState? = items.getOrNull(position)

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val textView: TextView = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false) as TextView

        getItem(position)?.let {
            textView.text = it.text
        }

        return textView
    }

    override fun getFilter() = object : Filter() {
        override fun performFiltering(constraint: CharSequence?) = FilterResults()
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {}
        override fun convertResultToString(resultValue: Any): CharSequence {
            return (resultValue as AddOrModifyRealEstateAutocompleteItemViewState).text
        }
    }

    fun setData(items: List<AddOrModifyRealEstateAutocompleteItemViewState>) {
        this.items = items
        notifyDataSetChanged()
    }
}