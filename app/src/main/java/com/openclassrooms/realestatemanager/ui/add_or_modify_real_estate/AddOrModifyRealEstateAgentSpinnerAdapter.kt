package com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.NonNull
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.databinding.AddRealEstateSpinnerItemBinding

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

    private fun getCustomView(position: Int, @NonNull parent: ViewGroup): View {
        val itemBinding = AddRealEstateSpinnerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        val itemViewState = getItem(position)

        Glide.with(context).load(itemViewState?.agentPhoto)
            .into(itemBinding.addRealEstateSpinnerItemImageView)

        itemBinding.addRealEstateSpinnerItemTextView.text = itemViewState?.agentNameInCharge

        return itemBinding.root
    }
}