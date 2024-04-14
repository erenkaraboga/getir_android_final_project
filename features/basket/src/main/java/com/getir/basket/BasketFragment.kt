package com.getir.basket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.getir.basket.databinding.FragmentBasketBinding
import com.getir.core.SharedViewModel
import com.getir.core.common.constants.ToolBarType
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BasketFragment : Fragment() {
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private lateinit var binding: FragmentBasketBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBasketBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sharedViewModel.setTopBar(ToolBarType.BASKET)
        super.onViewCreated(view, savedInstanceState)
    }

}