package com.example.booksearchapp.ui.view.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.booksearchapp.R
import com.example.booksearchapp.base.BaseActivity
import com.example.booksearchapp.databinding.ActivityMainBinding
import com.example.booksearchapp.ui.viewmodel.BookViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, BookViewModel>() {
    override val layoutResID: Int = R.layout.activity_main
    override val viewModel: BookViewModel  by lazy {
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(BookViewModel::class.java)
    }

    private val TIME_INTERVAL = 2000
    private var backKeyPressedTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        val navHostFragment: NavHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        viewDataBinding.bnMenu.setupWithNavController(navHostFragment.navController)

        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.listFragment || destination.id == R.id.searchFragment)
                viewDataBinding.bnMenu.visibility = View.VISIBLE
            else
                viewDataBinding.bnMenu.visibility = View.GONE
        }
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + TIME_INTERVAL) {
            backKeyPressedTime = System.currentTimeMillis()
        } else {
            finish()
        }
    }
}