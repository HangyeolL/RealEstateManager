package com.openclassrooms.realestatemanager.ui.realEstateList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.databinding.RealEstateListItemBinding

class RealEstateListAdapter(
    private val itemIdListener: (id : Int) -> Unit
) : ListAdapter<RealEstateListItemViewState, RealEstateListAdapter.ViewHolder>(
    RealEstateListDiffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            RealEstateListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), itemIdListener)
    }

    class ViewHolder(private val itemBinding: RealEstateListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(viewState: RealEstateListItemViewState, itemIdListener: (id: Int) -> Unit) {
            itemBinding.realEstateListItemTextViewType.text = viewState.type
            itemBinding.realEstateListItemTextViewCity.text = viewState.city
            itemBinding.realEstateListTiemTextViewPrice.text = viewState.price.toString()

            itemBinding.realEstateListItemCardView.setOnClickListener {
                itemIdListener.invoke(viewState.id)
            }

        }
    }

    object RealEstateListDiffCallback : DiffUtil.ItemCallback<RealEstateListItemViewState>() {
        override fun areItemsTheSame(
            oldItem: RealEstateListItemViewState,
            newItem: RealEstateListItemViewState
        ): Boolean =
            oldItem.id == newItem.id


        override fun areContentsTheSame(
            oldItem: RealEstateListItemViewState,
            newItem: RealEstateListItemViewState
        ): Boolean =
            oldItem == newItem

    }

}