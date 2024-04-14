package com.getir.lite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.NavHostFragment
import com.getir.core.SharedViewModel
import com.getir.lite.databinding.ActivityMainBinding

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
        setUpNavigation()
        setUpObservers()
    }

    private fun setUpObservers() {
        sharedViewModel.toolBarType.observe(this) {
            binding.customToolBar.setType(it)
        }
        sharedViewModel.cartAmount.observe(this) {
            binding.customToolBar.setAmount(it)
        }
    }
    private fun setUpNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun setListeners() {
        binding.customToolBar.apply {
            setCartButtonClickListener {
                val request = NavDeepLinkRequest.Builder
                    .fromUri("android-app://example.google.app/fragment_basket".toUri())
                    .build()
                navController.navigate(request)

            }
            setCloseButtonClickListener {
                navController.popBackStack()
            }
        }

    }
}