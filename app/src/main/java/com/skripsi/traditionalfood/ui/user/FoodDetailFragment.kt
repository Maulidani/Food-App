package com.skripsi.traditionalfood.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.skripsi.traditionalfood.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FoodDetailFragment : Fragment() {

    private val tvInfo: TextView by lazy { requireActivity().findViewById(R.id.tvInfoDesc) }
    private val tvDetail: TextView by lazy { requireActivity().findViewById(R.id.tvDetailDesc) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        val detail = bundle?.getString("detail")

        tvInfo.text = "Deskripsi"
        tvDetail.text = detail

    }

}