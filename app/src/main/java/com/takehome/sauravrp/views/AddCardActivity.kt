package com.takehome.sauravrp.views

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.takehome.sauravrp.DirectoryComponentProvider
import com.takehome.sauravrp.R
import com.takehome.sauravrp.databinding.CardContentAddViewBinding
import com.takehome.sauravrp.databinding.CardContentItemViewBinding
import com.takehome.sauravrp.di.components.DaggerActivityComponent
import com.takehome.sauravrp.viewmodels.AddCardViewModel
import com.takehome.sauravrp.viewmodels.factory.AddCardViewModelFactory
import timber.log.Timber
import javax.inject.Inject

class AddCardActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE = 1
    }

    private lateinit var binding: CardContentAddViewBinding

    private lateinit var model: AddCardViewModel

    @Inject
    internal lateinit var modelFactory: AddCardViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CardContentAddViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTitle(R.string.add_flash_card)

        DaggerActivityComponent
                .factory()
                .create((applicationContext as DirectoryComponentProvider).directoryComponent())
                .inject(this)

        model = ViewModelProvider(this, modelFactory).get(AddCardViewModel::class.java)

        model.viewState.observe(this, { state ->
            when (state) {
                is AddCardViewModel.ViewState.Error -> showError(state)
                AddCardViewModel.ViewState.Loading -> showLoading()
                is AddCardViewModel.ViewState.Success -> showSuccess(state)
            }
        })

        model.viewEvent.observe(this, { event ->
            when (event) {
                AddCardViewModel.ViewEvent.SaveCompleted -> saveComplete()
                is AddCardViewModel.ViewEvent.SaveEnabled -> enableSave(event.enabled)
                is AddCardViewModel.ViewEvent.SaveFailed -> showSaveError(event.error)
            }
        })
    }

    private fun saveComplete() {
        setResult(RESULT_OK)
        finish()
    }

    private fun showSaveError(error: Throwable) {
        Timber.e(error.toString())
    }

    private fun enableSave(enabled: Boolean) {
        binding.saveButton.isEnabled = enabled
    }

    private fun showSuccess(state: AddCardViewModel.ViewState.Success) {
        binding.apply {

            saveButton.setOnClickListener {
                model.save()
            }

            flashCardName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    model.setCardName(s.toString())
                }

            })

           state.locales.map { locale ->
               val addBinding = CardContentItemViewBinding.inflate(layoutInflater)
               binding.inputContainer.addView(
                   addBinding.root
               )
               addBinding.apply {
                   localeText.text = locale.localeName
                   titleText.addTextChangedListener(object : TextWatcher {
                       override fun beforeTextChanged(
                           s: CharSequence?,
                           start: Int,
                           count: Int,
                           after: Int
                       ) {
                       }

                       override fun onTextChanged(
                           s: CharSequence?,
                           start: Int,
                           before: Int,
                           count: Int
                       ) {
                       }

                       override fun afterTextChanged(s: Editable?) {
                           model.addTitle(s.toString(), locale)
                       }
                   })

                   bodyText.addTextChangedListener(object : TextWatcher {
                       override fun beforeTextChanged(
                           s: CharSequence?,
                           start: Int,
                           count: Int,
                           after: Int
                       ) {
                       }

                       override fun onTextChanged(
                           s: CharSequence?,
                           start: Int,
                           before: Int,
                           count: Int
                       ) {
                       }

                       override fun afterTextChanged(s: Editable?) {
                           model.addBody(s.toString(), locale)
                       }
                   })
               }

           }
        }
    }

    private fun showLoading() {
       // TBD
    }

    private fun showError(state: AddCardViewModel.ViewState.Error) {
        Timber.e(state.error.toString())
    }
}