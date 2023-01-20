package com.openclassrooms.realestatemanager.ui.realEstateList

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.databinding.RealEstateListItemBinding

class RealEstateListAdapter : ListAdapter<RealEstateListViewState, RealEstateListAdapter.ViewHolder>(RealEstateListDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    object RealEstateListDiffCallback : DiffUtil.ItemCallback<RealEstateListViewState>() {
        override fun areItemsTheSame(
            oldItem: RealEstateListViewState,
            newItem: RealEstateListViewState
        ): Boolean {
            TODO("Not yet implemented")
        }

        override fun areContentsTheSame(
            oldItem: RealEstateListViewState,
            newItem: RealEstateListViewState
        ): Boolean {
            TODO("Not yet implemented")
        }

    }

    class ViewHolder(private val itemBinding : RealEstateListItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(viewState: RealEstateListViewState) {

        }
    }

}