package com.openclassrooms.realestatemanager.design_system.real_estate_type

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import androidx.annotation.NonNull
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.databinding.AddOrModifyRealEstateSpinnerItemBinding

class RealEstateTypeSpinnerAdapter(
    context: Context,
    resource: Int
) : ArrayAdapter<RealEstateTypeSpinnerItemViewState>(context, resource) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, parent)
    }

    override fun getFilter() = object : Filter() {
        override fun performFiltering(constraint: CharSequence?) = FilterResults()
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

        }
        override fun convertResultToString(resultValue: Any): CharSequence {
            return (resultValue as RealEstateTypeSpinnerItemViewState).type
        }
    }

    private fun getCustomView(position: Int, @NonNull parent: ViewGroup): View {
        val itemBinding = AddOrModifyRealEstateSpinnerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        val itemViewState = getItem(position)

        Glide.with(context).load(itemViewState?.icon)
            .into(itemBinding.addOrModifyRealEstateSpinnerItemImageView)

        itemBinding.addOrModifyRealEstateSpinnerItemTextView.text = itemViewState?.type

        return itemBinding.root
    }
}