package com.skripsi.traditionalfood.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.skripsi.traditionalfood.R
import com.skripsi.traditionalfood.ui.admin.HomeAdminActivity
import com.skripsi.traditionalfood.ui.user.HomeUserActivity
import com.skripsi.traditionalfood.utils.Constant
import com.skripsi.traditionalfood.utils.PreferencesHelper

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : Fragment() {
    private lateinit var sharedPref: PreferencesHelper
    private lateinit var btnGetStarted: MaterialButton
    private lateinit var tvAdmin: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = PreferencesHelper(requireActivity())
        btnGetStarted = requireActivity().findViewById(R.id.btnGetStarted)
        tvAdmin = requireActivity().findViewById(R.id.tvAdmin)

        btnGetStarted.setOnClickListener {
//
            if (sharedPref.getBoolean(Constant.PREF_IS_LOGIN)) {
                startActivity(Intent(requireContext(), HomeAdminActivity::class.java))

//                val type = sharedPref.getString(Constant.PREF_TYPE)
//
//                if (type == "admin") {
//                    startActivity(Intent(requireContext(), HomeAdminActivity::class.java))
//                } else {
//                    sharedPref.logout()
//                    Toast.makeText(requireContext(), "Keluar", Toast.LENGTH_SHORT).show()
//                    startActivity(
//                        Intent(requireContext(), LoginActivity::class.java)
//                    )
//                }
//
//            } else {
//                startActivity(Intent(requireContext(), LoginAdminActivity::class.java))
            } else {
                startActivity(Intent(requireContext(), HomeUserActivity::class.java))
            }
        }

        if (sharedPref.getBoolean(Constant.PREF_IS_LOGIN)) {
            tvAdmin.visibility = View.GONE
        } else {
            tvAdmin.visibility = View.VISIBLE
        }

        tvAdmin.setOnClickListener {
            startActivity(Intent(requireContext(), LoginAdminActivity::class.java))
        }

    }
}

