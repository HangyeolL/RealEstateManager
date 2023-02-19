package com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.RealEstatePhotoListItemAddBinding
import com.openclassrooms.realestatemanager.databinding.RealEstatePhotoListItemBinding

class AddOrModifyRealEstatePhotoListAdapter :
    ListAdapter<AddOrModifyRealEstatePhotoListItemViewState, RecyclerView.ViewHolder>(DetailListDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when(viewType) {
            R.layout.real_estate_photo_list_item ->
                ItemViewHolder(
                    RealEstatePhotoListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            R.layout.real_estate_photo_list_item_add ->
                ItemAddViewHolder(
                    RealEstatePhotoListItemAddBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            else -> {
                throw IllegalArgumentException("unknown view type $viewType")
            }
        }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)) {
            R.layout.real_estate_photo_list_item ->
                (holder as ItemViewHolder).bind(getItem(position))
            R.layout.real_estate_photo_list_item_add ->
                (holder as ItemAddViewHolder).bind()
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (position == currentList.size) {
            R.layout.real_estate_photo_list_item_add
        } else {
            R.layout.real_estate_photo_list_item
        }

    override fun getItemCount(): Int {
        return currentList.size + 1
    }

    class ItemViewHolder(
        private val itemBinding: RealEstatePhotoListItemBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(viewState: AddOrModifyRealEstatePhotoListItemViewState) {
            Glide.with(itemView.context).load(viewState.photoUrl)
                .into(itemBinding.realEstatePhotoListItemImageView)
            itemBinding.realEstatePhotoListItemTextView.text = viewState.photoDescription
        }

    }

    class ItemAddViewHolder(
        private val itemBinding: RealEstatePhotoListItemAddBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind() {
            itemBinding.ImageButton.setOnClickListener {
                //TODO OnClick of last item of the list
            }
        }
    }

    object DetailListDiffCallback : DiffUtil.ItemCallback<AddOrModifyRealEstatePhotoListItemViewState>() {
        override fun areItemsTheSame(
            oldItem: AddOrModifyRealEstatePhotoListItemViewState,
            newItem: AddOrModifyRealEstatePhotoListItemViewState
        ): Boolean = oldItem.photoId == newItem.photoId

        override fun areContentsTheSame(
            oldItem: AddOrModifyRealEstatePhotoListItemViewState,
            newItem: AddOrModifyRealEstatePhotoListItemViewState
        ): Boolean = oldItem == newItem
    }

}