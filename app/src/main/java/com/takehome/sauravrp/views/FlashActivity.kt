package com.takehome.sauravrp.views

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.switchmaterial.SwitchMaterial
import com.takehome.sauravrp.DirectoryComponentProvider
import com.takehome.sauravrp.R
import com.takehome.sauravrp.databinding.FlashActivityViewBinding
import com.takehome.sauravrp.di.components.DaggerActivityComponent
import com.takehome.sauravrp.viewmodels.FlashCardViewModel
import com.takehome.sauravrp.viewmodels.factory.FlashCardViewModelFactory
import javax.inject.Inject

class FlashActivity : AppCompatActivity() {

    private lateinit var binding: FlashActivityViewBinding

    @Inject
    internal lateinit var modelFactory: FlashCardViewModelFactory

    private lateinit var model: FlashCardViewModel

    private var addMenu: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FlashActivityViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        DaggerActivityComponent
            .factory()
            .create((applicationContext as DirectoryComponentProvider).directoryComponent())
            .inject(this)

        model = ViewModelProvider(this, modelFactory).get(
            FlashCardViewModel::class.java)

        model.viewEvent.observe(this, { event ->
            when(event) {
                is FlashCardViewModel.ViewEvent.ShowDetails -> navigateToDetail()
            }

        })
    }

    private fun navigateToAddCard() {
//        /        supportFragmentManager
//            .beginTransaction()
//            .replace(binding.fragmentContainer.id, EmployeeDetailFragment())
//            .addToBackStack(null)
//            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.add_card) {
            startActivity(Intent(this@FlashActivity, AddCardActivity::class.java))
        }
        return true
    }


    fun navigateToDetail() {
//        supportFragmentManager
//            .beginTransaction()
//            .replace(binding.fragmentContainer.id, EmployeeDetailFragment())
//            .addToBackStack(null)
//            .commit()
    }
}