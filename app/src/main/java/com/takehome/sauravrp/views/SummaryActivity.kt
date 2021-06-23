package com.takehome.sauravrp.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.takehome.sauravrp.DirectoryComponentProvider
import com.takehome.sauravrp.R
import com.takehome.sauravrp.databinding.SummaryActivityBinding
import com.takehome.sauravrp.di.components.DaggerActivityComponent
import com.takehome.sauravrp.viewmodels.SummaryViewModel
import com.takehome.sauravrp.viewmodels.factory.SummaryViewModelFactory
import timber.log.Timber
import javax.inject.Inject

class SummaryActivity : AppCompatActivity() {

    private lateinit var binding : SummaryActivityBinding

    private lateinit var model: SummaryViewModel

    @Inject
    internal lateinit var modelFactory: SummaryViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SummaryActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        DaggerActivityComponent
                .factory()
                .create((applicationContext as DirectoryComponentProvider).directoryComponent())
                .inject(this)

        binding.apply {
            localeCount.setOnClickListener {
                startActivity(Intent(this@SummaryActivity, LocaleActivity::class.java))
            }

            flashCardCount.setOnClickListener {
                startActivity(Intent(this@SummaryActivity, FlashActivity::class.java))
            }
        }

        model = ViewModelProvider(this, modelFactory).get(SummaryViewModel::class.java)

        model.viewState.observe(this, { state ->
            when(state) {
                is SummaryViewModel.ViewState.Error -> showError(state)
                SummaryViewModel.ViewState.Loading -> showLoading()
                is SummaryViewModel.ViewState.Success -> showSuccess(state)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        model.fetchCount()
    }

    private fun showSuccess(state: SummaryViewModel.ViewState.Success) {
        binding.apply {
            successView.isVisible = true
            progress.isVisible = false

            flashCardCount.text = String.format(getString(R.string.flash_card_count), state.flashCardCount)
            localeCount.text = String.format(getString(R.string.summary_locale_count), state.localeCount)
        }
    }

    private fun showLoading() {
        binding.apply {
            successView.isVisible = false
            progress.isVisible = true
        }
    }

    private fun showError(state: SummaryViewModel.ViewState.Error) {
        Timber.e(state.error.toString())
    }

}