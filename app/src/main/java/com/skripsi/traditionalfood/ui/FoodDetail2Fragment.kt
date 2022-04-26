package com.skripsi.traditionalfood.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.skripsi.traditionalfood.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FoodDetail2Fragment : Fragment() {

    private val tvInfo: TextView by lazy { requireActivity().findViewById(R.id.tvInfoRecipe) }
    private val tvDetail: TextView by lazy { requireActivity().findViewById(R.id.tvDetailRecipe) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_detail2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        val detail = bundle?.getString("detail")

        tvInfo.text = "Resep"
        tvDetail.text = detail


    }
}