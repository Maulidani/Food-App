package com.skripsi.traditionalfood.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.skripsi.traditionalfood.R
import com.skripsi.traditionalfood.adapter.AdapterFood
import com.skripsi.traditionalfood.model.ResponseFoodModel
import com.skripsi.traditionalfood.network.ApiClient
import com.skripsi.traditionalfood.ui.admin.InputEditFoodActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListFoodFragment(_type: String, _category: String) : Fragment(), AdapterFood.IUserRecycler {
    private val type = _type
    private val category = _category
    private val pbLoading: ProgressBar by lazy { requireActivity().findViewById(R.id.pbLoading) }
    private val fabAdd: FloatingActionButton by lazy { requireView().findViewById(R.id.fabAdd) }
    private val rv: RecyclerView by lazy { requireActivity().findViewById(R.id.rvFood) }
    private val search: EditText by lazy { requireView().findViewById(R.id.etSearch) }
    private val notFound: TextView by lazy { requireActivity().findViewById(R.id.tvNotFound) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_food, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (type == "admin") {
            fabAdd.visibility = View.VISIBLE
            fabAdd.setOnClickListener {
                startActivity(
                    Intent(requireActivity(), InputEditFoodActivity::class.java).putExtra(
                        "type",
                        "add"
                    )
                )
            }
        } else {
            fabAdd.setVisibility(View.INVISIBLE)
        }

        search.addTextChangedListener {
            food(search.text.toString(), category)
        }

    }

    private fun food(searchString: String, category: String) {
        pbLoading.visibility = View.VISIBLE

        ApiClient.instances.showFoodCategory(searchString, category)
            .enqueue(object : Callback<ResponseFoodModel> {
                override fun onResponse(
                    call: Call<ResponseFoodModel>,
                    response: Response<ResponseFoodModel>
                ) {
                    val message = response.body()?.message
                    val error = response.body()?.errors
                    val data = response.body()?.data

                    if (response.isSuccessful) {

                        if (error == false) {

                            val adapter =
                                data?.let { AdapterFood(it, type, this@ListFoodFragment) }
                            rv.layoutManager = LinearLayoutManager(requireContext())
                            rv.adapter = adapter

                            if ("${data?.size}" == "0") {
                                notFound.visibility = View.VISIBLE
                            } else {
                                notFound.visibility = View.INVISIBLE
                            }

                        } else {
                            Toast.makeText(requireContext(), "gagal", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {

                        Toast.makeText(requireContext(), "gagal", Toast.LENGTH_SHORT)
                            .show()

                    }
                    pbLoading.visibility = View.INVISIBLE;

                }

                override fun onFailure(call: Call<ResponseFoodModel>, t: Throwable) {

                    Toast.makeText(
                        requireContext(),
                        t.message.toString(),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    pbLoading.visibility = View.INVISIBLE;

                }

            })
    }

    override fun refreshView(onUpdate: Boolean) {
        food("", category)
    }

    override fun onResume() {
        super.onResume()
        food("", category)
    }

}