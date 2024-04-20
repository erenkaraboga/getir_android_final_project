package com.getir.lite

import android.content.DialogInterface
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.getir.core.SharedViewModel
import com.getir.core.common.constants.NavigationRoute
import com.getir.core.common.utils.AlertDialogListener
import com.getir.core.common.utils.DialogHelper
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
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun setUpObservers() {
        sharedViewModel.toolBarType.observe(this) {
            binding.customToolBar.setType(it)
        }
        sharedViewModel.cartAmount.observe(this) {
            binding.customToolBar.setAmount(it)
        }

        sharedViewModel.isOrdered.observe(this) { isOrdered ->
            if (isOrdered) {
                restartActivity()
            }
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
                navigateToBasketFragment()
            }

            setCloseButtonClickListener {
                navController.popBackStack()
            }

            setDeleteButtonClickListener {
                if (sharedViewModel.isDeleteButtonActive()) {
                    showDialog()
                }
            }

        }

    }

    private fun navigateToBasketFragment() {
        if (sharedViewModel.canNavigateBasket()) {
            val navOptions = NavOptions.Builder()
                .setEnterAnim(com.getir.core.R.anim.slide_in)
                .setPopEnterAnim(com.getir.core.R.anim.slide_in)
                .build()
            val request = NavDeepLinkRequest.Builder
                .fromUri(NavigationRoute.BASKET.toUri())
                .build()
            navController.navigate(request, navOptions)
        }

    }

    private fun showDialog() {
        DialogHelper.showDialog(
            context = this,
            object : AlertDialogListener {
                override fun onPositiveClicked(dialog: DialogInterface) {
                    restartActivity()
                }

                override fun onNegativeClicked(dialog: DialogInterface) {
                    dialog.dismiss()
                    dialog.cancel()
                }
            },
            getString(com.getir.core.R.string.are_you_sure_clear_basket),
        )
    }

    private fun restartActivity() {
        val intent = intent
        finish()
        startActivity(intent)
    }
}