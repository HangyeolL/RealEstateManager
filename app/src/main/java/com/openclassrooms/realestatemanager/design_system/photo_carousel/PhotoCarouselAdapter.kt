package com.openclassrooms.realestatemanager.design_system.photo_carousel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.databinding.RealEstatePhotoListItemAddBinding
import com.openclassrooms.realestatemanager.databinding.RealEstatePhotoListItemBinding
import com.openclassrooms.realestatemanager.design_system.photo_carousel.PhotoCarouselViewState.*
import com.openclassrooms.realestatemanager.design_system.photo_carousel.PhotoCarouselViewState.PhotoCarouselType.ADD_PHOTO
import com.openclassrooms.realestatemanager.design_system.photo_carousel.PhotoCarouselViewState.PhotoCarouselType.CONTENT

class PhotoCarouselAdapter : ListAdapter<PhotoCarouselViewState, ViewHolder>(PhotoCarouselDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = when (PhotoCarouselType.values()[viewType]) {
        CONTENT -> PhotoCarouselViewHolder(
            RealEstatePhotoListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
        ADD_PHOTO -> PhotoCarouselAddViewHolder(
            RealEstatePhotoListItemAddBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is Content -> (holder as PhotoCarouselViewHolder).bind(item)
            is AddPhoto -> (holder as PhotoCarouselAddViewHolder).bind(item)
        }
    }

    override fun getItemViewType(position: Int): Int = getItem(position).type.ordinal

    class PhotoCarouselViewHolder(private val itemBinding: RealEstatePhotoListItemBinding) : ViewHolder(itemBinding.root) {

        fun bind(viewState: Content) {
            Glide.with(itemView.context)
                .load(viewState.photoUrl)
                .into(itemBinding.realEstatePhotoListItemImageView)
            itemBinding.realEstatePhotoListItemTextView.text = viewState.photoDescription
        }
    }

    class PhotoCarouselAddViewHolder(private val itemBinding: RealEstatePhotoListItemAddBinding) : ViewHolder(itemBinding.root) {

        fun bind(viewState: AddPhoto) {
            itemBinding.ImageButton.setOnClickListener {
                viewState.onClick()
            }
        }
    }

    object PhotoCarouselDiffCallback : DiffUtil.ItemCallback<PhotoCarouselViewState>() {
        override fun areItemsTheSame(oldItem: PhotoCarouselViewState, newItem: PhotoCarouselViewState): Boolean =
            oldItem is Content && newItem is Content && oldItem.photoId == newItem.photoId
                || oldItem is AddPhoto && newItem is AddPhoto

        override fun areContentsTheSame(oldItem: PhotoCarouselViewState, newItem: PhotoCarouselViewState): Boolean = oldItem == newItem
    }
}
