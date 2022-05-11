package com.skripsi.traditionalfood.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.skripsi.traditionalfood.R
import com.skripsi.traditionalfood.model.DataModel
import com.skripsi.traditionalfood.model.ResponseFoodModel
import com.skripsi.traditionalfood.network.ApiClient
import com.skripsi.traditionalfood.ui.admin.InputEditFoodActivity
import com.skripsi.traditionalfood.ui.user.FoodDetailActivity
import com.skripsi.traditionalfood.utils.Constant
import okhttp3.internal.notifyAll
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdapterFood(
    private val list: List<DataModel>,
    type: String,
    private val mListener: AdapterFood.IUserRecycler
) :
    RecyclerView.Adapter<AdapterFood.ListViewHolder>() {

    val _type = type

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView by lazy { itemView.findViewById(R.id.tvFoodName) }
        val icMore: ImageView by lazy { itemView.findViewById(R.id.imgMore) }
        val item: CardView by lazy { itemView.findViewById(R.id.itemCardProduct) }

        fun bindData(result: DataModel) {

            if (_type == "admin") {

                icMore.visibility = View.VISIBLE
                icMore.setOnClickListener {
                    optionAlert(itemView, result)
                }

            } else {
                icMore.visibility = View.INVISIBLE
            }

            name.text = result.name

            item.setOnClickListener {
                itemView.context.startActivity(
                    Intent(itemView.context, FoodDetailActivity::class.java)
                        .putExtra("id", result.id.toString())
                        .putExtra("name", result.name)
                        .putExtra("category", result.category)
                        .putExtra("image", result.image)
                        .putExtra("description", result.description)
                        .putExtra("recipe", result.recipe)
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bindData(list[position])
    }

    override fun getItemCount(): Int = list.size

    private fun optionAlert(itemView: View, result: DataModel) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(itemView.context)
        builder.setTitle("Aksi")

        val options = arrayOf("Edit item", "Hapus item")
        builder.setItems(
            options
        ) { _, which ->
            when (which) {
                0 -> {
                    ContextCompat.startActivity(
                        itemView.context,
                        Intent(itemView.context, InputEditFoodActivity::class.java)
                            .putExtra("type", "edit")
                            .putExtra("id", result.id.toString())
                            .putExtra("name", result.name)
                            .putExtra("category", result.category)
                            .putExtra("image", result.image)
                            .putExtra("description", result.description)
                            .putExtra("recipe", result.recipe), null
                    )
                }
                1 -> deleteAlert(itemView, result.id)
            }
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun deleteAlert(itemView: View, id: Int) {
        val builder = AlertDialog.Builder(itemView.context)
        builder.setTitle("Hapus")
        builder.setMessage("Hapus makanan ini ?")

        builder.setPositiveButton("Ya") { _, _ ->
            delete(itemView, id)
        }

        builder.setNegativeButton("Tidak") { _, _ ->
            // cancel
        }
        builder.show()
    }

    private fun delete(itemView: View, id: Int) {
        ApiClient.instances.deletefood(id).enqueue(object :
            Callback<ResponseFoodModel> {
            override fun onResponse(
                call: Call<ResponseFoodModel>,
                response: Response<ResponseFoodModel>
            ) {
                if (response.isSuccessful) {

                    mListener.refreshView(true)
                    notifyDataSetChanged()
                } else {
                    Toast.makeText(itemView.context, "gagal", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseFoodModel>, t: Throwable) {
                Toast.makeText(itemView.context, t.message.toString(), Toast.LENGTH_SHORT).show()

            }
        })
    }

    interface IUserRecycler {
        fun refreshView(onUpdate: Boolean)
    }
}