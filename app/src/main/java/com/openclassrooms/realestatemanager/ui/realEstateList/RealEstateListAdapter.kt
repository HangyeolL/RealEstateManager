package com.openclassrooms.realestatemanager.ui.realEstateList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.RealEstateListItemBinding
import com.openclassrooms.realestatemanager.ui.detail.DetailFragment
import com.openclassrooms.realestatemanager.ui.main.MainActivity

class RealEstateListAdapter : ListAdapter<RealEstateListViewStateItem, RealEstateListAdapter.ViewHolder>(RealEstateListDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
        RealEstateListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val itemBinding : RealEstateListItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(viewState: RealEstateListViewStateItem) {
            itemBinding.realEstateListItemTextViewType.text = viewState.type
            itemBinding.realEstateListItemTextViewCity.text = viewState.city
            itemBinding.realEstateListTiemTextViewPrice.text = viewState.price.toString()

            // TODO how to do transaction to switch Fragment ?

        }
    }

    object RealEstateListDiffCallback : DiffUtil.ItemCallback<RealEstateListViewStateItem>() {
        override fun areItemsTheSame(
            oldItem: RealEstateListViewStateItem,
            newItem: RealEstateListViewStateItem
        ): Boolean =
            oldItem.id == newItem.id


        override fun areContentsTheSame(
            oldItem: RealEstateListViewStateItem,
            newItem: RealEstateListViewStateItem
        ): Boolean =
            oldItem == newItem

    }

}