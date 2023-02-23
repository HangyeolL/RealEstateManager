package com.openclassrooms.realestatemanager.design_system.photo_carousel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.databinding.RealEstatePhotoListItemAddBinding
import com.openclassrooms.realestatemanager.databinding.RealEstatePhotoListItemBinding
import com.openclassrooms.realestatemanager.design_system.photo_carousel.RealEstatePhotoItemViewState.*
import com.openclassrooms.realestatemanager.design_system.photo_carousel.RealEstatePhotoItemViewState.PhotoCarouselType.ADD_PHOTO
import com.openclassrooms.realestatemanager.design_system.photo_carousel.RealEstatePhotoItemViewState.PhotoCarouselType.CONTENT

//TODO Didnt understand perfectly how it works
class RealEstatePhotoListAdapter :
    ListAdapter<RealEstatePhotoItemViewState, ViewHolder>(PhotoCarouselDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        when (PhotoCarouselType.values()[viewType]) {
            CONTENT -> RealEstatePhotoViewHolder(
                RealEstatePhotoListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            ADD_PHOTO -> RealEstateAddViewHolder(
                RealEstatePhotoListItemAddBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is Content -> (holder as RealEstatePhotoViewHolder).bind(item)
            is AddRealEstatePhoto -> (holder as RealEstateAddViewHolder).bind(item)
        }
    }

    override fun getItemViewType(position: Int): Int = getItem(position).type.ordinal

    class RealEstatePhotoViewHolder(private val itemBinding: RealEstatePhotoListItemBinding) :
        ViewHolder(itemBinding.root) {

        fun bind(viewState: Content) {
            Glide.with(itemView.context)
                .load(viewState.photoUrl)
                .centerCrop()
                .into(itemBinding.realEstatePhotoListItemImageView)
            itemBinding.realEstatePhotoListItemTextView.text = viewState.photoDescription
        }
    }

    class RealEstateAddViewHolder(private val itemBinding: RealEstatePhotoListItemAddBinding) :
        ViewHolder(itemBinding.root) {

        fun bind(viewState: AddRealEstatePhoto) {
            itemBinding.ImageButton.setOnClickListener {
                viewState.onClick()
            }
        }
    }

    object PhotoCarouselDiffCallback : DiffUtil.ItemCallback<RealEstatePhotoItemViewState>() {
        override fun areItemsTheSame(
            oldItem: RealEstatePhotoItemViewState,
            newItem: RealEstatePhotoItemViewState
        ): Boolean =
            oldItem is Content && newItem is Content && oldItem.photoId == newItem.photoId
                    || oldItem is AddRealEstatePhoto && newItem is AddRealEstatePhoto

        override fun areContentsTheSame(
            oldItem: RealEstatePhotoItemViewState,
            newItem: RealEstatePhotoItemViewState
        ): Boolean = oldItem == newItem
    }
}
