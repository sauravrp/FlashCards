package com.takehome.sauravrp.views


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.takehome.sauravrp.DirectoryComponentProvider
import com.takehome.sauravrp.databinding.FlashDetailViewBinding
import com.takehome.sauravrp.di.components.DaggerFragmentComponent
import com.takehome.sauravrp.viewmodels.FlashCardViewModel
import com.takehome.sauravrp.viewmodels.FlashContentWithLocale
import com.takehome.sauravrp.viewmodels.factory.FlashCardViewModelFactory
import com.takehome.sauravrp.views.adapter.FlashContentAdapter
import javax.inject.Inject

class FlashCardDetailFragment : Fragment() {

    @Inject
    lateinit var flashCardViewModelFactory: FlashCardViewModelFactory

    private var binding: FlashDetailViewBinding? = null

    private lateinit var model: FlashCardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerFragmentComponent
            .factory()
            .create((requireActivity().applicationContext as DirectoryComponentProvider).directoryComponent())
            .inject(this)

        model = ViewModelProvider(requireActivity(), flashCardViewModelFactory).get(
            FlashCardViewModel::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val _binding = FlashDetailViewBinding.inflate(inflater, container, false)
        binding = _binding
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.detailContentList?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = FlashContentAdapter()
        }

        model.viewEvent.observe(viewLifecycleOwner, { event ->
            when (event) {
                is FlashCardViewModel.ViewEvent.ShowDetails -> model.fetchFlashCardContents(event.data)
            }
        })

        model.viewState.observe(viewLifecycleOwner, { state ->
            when (state) {
                is FlashCardViewModel.ViewState.FlashContents -> showDetail(state.data)
                else -> {
                }
            }
        })
    }

    private fun showDetail(cardContents: List<FlashContentWithLocale>) {
        binding?.detailContentList?.apply {
            (adapter as FlashContentAdapter).submitList(cardContents)
        }
    }
}