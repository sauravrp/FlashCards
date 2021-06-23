package com.takehome.sauravrp.views

import android.graphics.drawable.ClipDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.takehome.sauravrp.DirectoryComponentProvider
import com.takehome.sauravrp.R
import com.takehome.sauravrp.databinding.LocaleActivityBinding
import com.takehome.sauravrp.di.components.DaggerActivityComponent
import com.takehome.sauravrp.viewmodels.LocaleViewModel
import com.takehome.sauravrp.viewmodels.factory.LocaleViewModelFactory
import com.takehome.sauravrp.views.adapter.LocaleAdapter
import timber.log.Timber
import javax.inject.Inject

class LocaleActivity : AppCompatActivity() {
    private lateinit var binding: LocaleActivityBinding

    private lateinit var model: LocaleViewModel

    @Inject
    internal lateinit var modelFactory: LocaleViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LocaleActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTitle(R.string.add_locale)

        binding.apply {
            localeListView.apply {
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(DividerItemDecoration(context, ClipDrawable.HORIZONTAL))
                adapter = LocaleAdapter()
            }

            inputLocale.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    addLocale.isEnabled = s.toString().isNotBlank()
                }

            })

            addLocale.setOnClickListener {
                model.addLocale(inputLocale.editableText.toString())
            }
        }

        DaggerActivityComponent
                .factory()
                .create((applicationContext as DirectoryComponentProvider).directoryComponent())
                .inject(this)

        model = ViewModelProvider(this, modelFactory).get(LocaleViewModel::class.java)

        model.viewState.observe(this, { state ->
            when (state) {
                is LocaleViewModel.ViewState.Error -> showError(state)
                LocaleViewModel.ViewState.Loading -> showLoading()
                is LocaleViewModel.ViewState.Success -> showSuccess(state)
            }
        })
    }

    private fun showSuccess(state: LocaleViewModel.ViewState.Success) {

        binding.apply {
            localeHeader.isVisible = true
            localeListView.isVisible = true
            (localeListView.adapter as LocaleAdapter).submitList(state.locales)
            inputLocale.editableText.clear()
        }
    }

    private fun showLoading() {
        binding.apply {
            localeHeader.isVisible = false
            localeListView.isVisible = false
        }
    }

    private fun showError(state: LocaleViewModel.ViewState.Error) {
        Timber.e(state.error.toString())
    }
}