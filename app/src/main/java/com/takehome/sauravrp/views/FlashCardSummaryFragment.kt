package com.takehome.sauravrp.views

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.takehome.sauravrp.DirectoryComponentProvider
import com.takehome.sauravrp.R
import com.takehome.sauravrp.databinding.FlashSummaryViewBinding
import com.takehome.sauravrp.di.components.DaggerFragmentComponent
import com.takehome.sauravrp.viewmodels.*
import com.takehome.sauravrp.viewmodels.factory.FlashCardViewModelFactory
import com.takehome.sauravrp.views.adapter.FlashListAdapter
import timber.log.Timber
import javax.inject.Inject

class FlashCardSummaryFragment : Fragment(), FlashListAdapter.FlashCardSelectionListener {

    @Inject
    lateinit var flashCardViewModelFactory: FlashCardViewModelFactory

    private var binding: FlashSummaryViewBinding? = null

    private lateinit var model: FlashCardViewModel

    private val listAdapter = FlashListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerFragmentComponent
            .factory()
            .create((requireActivity().applicationContext as DirectoryComponentProvider).directoryComponent())
            .inject(this)

        model = ViewModelProvider(requireActivity(), flashCardViewModelFactory).get(
            FlashCardViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val _binding = FlashSummaryViewBinding.inflate(inflater, container, false)
        binding = _binding
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.listView?.apply {
            adapter = listAdapter
        }

        binding?.retryButton?.setOnClickListener {
            model.fetchFlashCards()
        }

        model.viewState.observe(viewLifecycleOwner, {
            when (it) {
                is FlashCardViewModel.ViewState.Error -> showError(it.error)
                FlashCardViewModel.ViewState.Loading -> showLoading()
                is FlashCardViewModel.ViewState.FlashCards ->  {
                    if(it.data.isEmpty()) {
                        showEmpty()
                    } else {
                        showFlashCards(it.data)
                    }
                }
            }
        })
    }


    override fun onStart() {
        super.onStart()
        model.fetchFlashCards()
    }

    private fun showEmpty() {
        Timber.d("Showing empty state")
        binding?.apply {
            listView.isVisible = false
            progress.isVisible = false

            noResultsContainer.isVisible = true
            noResultsMessage.text = getString(R.string.flash_card_empty_message)
            retryButton.isVisible = false

        }
    }

    private fun showError(error: Throwable) {
        Timber.d("Showing error state")
        binding?.apply {
            listView.isVisible = false
            progress.isVisible = false

            noResultsContainer.isVisible = true
            noResultsMessage.text = getString(R.string.error_message)

            retryButton.isVisible = true

            Timber.e(error)
        }
    }

    private fun showLoading() {
        Timber.d("Showing loading state")
        binding?.apply {
            listView.isVisible = false
            progress.isVisible = true
            noResultsContainer.isVisible = false
        }
    }

    private fun showFlashCards(data: List<FlashCard>) {
        Timber.d("Showing flash cards")
        binding?.apply {
            listView.isVisible = true
            progress.isVisible = false
            noResultsContainer.isVisible = false
        }
        listAdapter.submitList(data)
    }

    override fun cardItemSelected(card: FlashCard) {
       model.setSelection(card)
    }
}