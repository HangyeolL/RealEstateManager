package com.openclassrooms.realestatemanager.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.databinding.RealEstatePhotoListItemBinding

class DetailListAdapter :
    ListAdapter<DetailListItemViewState, DetailListAdapter.ViewHolder>(DetailListDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            RealEstatePhotoListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val itemBinding: RealEstatePhotoListItemBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(viewState: DetailListItemViewState) {
            Glide.with(itemView.context).load(viewState.photoUrl)
                .into(itemBinding.realEstatePhotoListItemImageView)
            itemBinding.realEstatePhotoListItemTextView.text = viewState.photoDescription
        }

    }

    object DetailListDiffCallback : DiffUtil.ItemCallback<DetailListItemViewState>() {
        override fun areItemsTheSame(
            oldItem: DetailListItemViewState,
            newItem: DetailListItemViewState
        ): Boolean = oldItem.photoId == newItem.photoId

        override fun areContentsTheSame(
            oldItem: DetailListItemViewState,
            newItem: DetailListItemViewState
        ): Boolean = oldItem == newItem
    }

}