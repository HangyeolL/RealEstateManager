package com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import androidx.annotation.NonNull
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.databinding.AddOrModifyRealEstateSpinnerItemBinding

class AddOrModifyRealEstateAgentSpinnerAdapter(
    context: Context,
    resource: Int
) : ArrayAdapter<AddOrModifyRealEstateAgentSpinnerItemViewState>(context, resource) {

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
            return (resultValue as AddOrModifyRealEstateAgentSpinnerItemViewState).agentNameInCharge
        }
    }

    private fun getCustomView(position: Int, @NonNull parent: ViewGroup): View {
        val itemBinding = AddOrModifyRealEstateSpinnerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        val itemViewState = getItem(position)

        Glide.with(context).load(itemViewState?.agentPhoto)
            .into(itemBinding.addOrModifyRealEstateSpinnerItemImageView)

        itemBinding.addOrModifyRealEstateSpinnerItemTextView.text = itemViewState?.agentNameInCharge

        return itemBinding.root
    }
}