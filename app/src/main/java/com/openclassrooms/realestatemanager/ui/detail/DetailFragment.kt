package com.openclassrooms.realestatemanager.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.PagerSnapHelper
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.DetailFragmentBinding
import com.openclassrooms.realestatemanager.design_system.real_estate_photo.RealEstatePhotoListAdapter
import com.openclassrooms.realestatemanager.design_system.real_estate_photo.RealEstatePhotoListPagingIndicationDecoration
import com.openclassrooms.realestatemanager.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.detail_fragment) {

    private val binding by viewBinding { DetailFragmentBinding.bind(it) }

    private val viewModel by viewModels<DetailViewModel>()

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("HL", "DetailFragment onCreateView called")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("HL", "DetailFragment onViewCreated called")

        setHasOptionsMenu(true)

        navController = Navigation.findNavController(
            requireActivity(),
            R.id.main_FragmentContainerView_navHost
        )

        val adapter = RealEstatePhotoListAdapter() {}
        binding.detailRecyclerViewImages.adapter = adapter

        // add pager behavior to RecyclerView
        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(binding.detailRecyclerViewImages)
        binding.detailRecyclerViewImages.addItemDecoration(RealEstatePhotoListPagingIndicationDecoration())

        viewModel.detailViewStateLiveData.observe(viewLifecycleOwner) { detailViewState ->

            binding.detailConstraintLayoutParent.isVisible = detailViewState.isViewVisible

            binding.detailTextViewDescriptionBody.text = detailViewState.descriptionBody
            binding.detailTextViewSquareMeter.text = detailViewState.squareMeter.toString()
            binding.detailTextViewRooms.text = detailViewState.numberOfRooms.toString()
            binding.detailTextViewBathrooms.text = detailViewState.numberOfBathrooms.toString()
            binding.detailTextViewBedrooms.text = detailViewState.numberOfBedrooms.toString()
            binding.detailTextViewAddress.text = detailViewState.address
            binding.detailTextViewAgentName.text = detailViewState.agentName

            Glide.with(this)
                .load(detailViewState.agentPhotoUrl)
                .centerCrop()
                .into(binding.detailShapeableImageViewAgent)

            adapter.submitList(detailViewState.realEstatePhotoItemViewStateList)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.toolbar_menu_modify -> {
                val realEstateId = viewModel.selectedRealEstateId
                Log.d("HL", "DetailFragment handling toolBar menu modify")
                Log.d("HL", "DetailToAddOrModify:currentRealEstateId=${realEstateId}")

                navController.navigate(
                    DetailFragmentDirections.actionToAddOrModifyRealEstateFragment(
                        realEstateId
                    )
                )



                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }



    override fun onResume() {
        super.onResume()
        Log.d("HL", "DetailFragment onPause called")
    }

    override fun onPause() {
        super.onPause()
        Log.d("HL", "DetailFragment onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d("HL", "DetailFragment onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("HL", "DetailFragment onDestroy called")
    }
}
