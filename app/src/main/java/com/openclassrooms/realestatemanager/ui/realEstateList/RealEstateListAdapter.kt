package com.openclassrooms.realestatemanager.ui.realEstateList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.databinding.RealEstateListItemBinding

class RealEstateListAdapter : ListAdapter<RealEstateListViewState, RealEstateListAdapter.ViewHolder>(RealEstateListDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
        RealEstateListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val itemBinding : RealEstateListItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(viewState: RealEstateListViewState) {
            itemBinding.realEstateListItemTextViewType.text = viewState.type
            itemBinding.realEstateListItemTextViewCity.text = viewState.city
            itemBinding.realEstateListTiemTextViewPrice.text = viewState.price.toString()
        }
    }

    object RealEstateListDiffCallback : DiffUtil.ItemCallback<RealEstateListViewState>() {
        override fun areItemsTheSame(
            oldItem: RealEstateListViewState,
            newItem: RealEstateListViewState
        ): Boolean =
            oldItem.id == newItem.id


        override fun areContentsTheSame(
            oldItem: RealEstateListViewState,
            newItem: RealEstateListViewState
        ): Boolean =
            oldItem == newItem

    }

}