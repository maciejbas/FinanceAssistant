package com.example.financeassistant.incomeRecyclerView

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.financeassistant.R
import com.example.financeassistant.room.Operation
import com.example.financeassistant.room.OperationType

class OperationAdapter(private val context: Context) :
    RecyclerView.Adapter<OperationAdapter.ViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    private var items = listOf<Operation>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        val image = when(item.operationType){
            OperationType.EXPENSE -> R.drawable.icons8_minus_50
            OperationType.INCOME -> R.drawable.icons8_plus_50
        }

        Glide
            .with(context)
            .load(image)
            .placeholder(R.drawable.icons8_frame_100)
            .override(40, 40)
            .into(holder.imageViewHolder)

        holder.categoryTextView.text = item.category
        holder.amountTextView.text = String.format("%.2f",item.amount)
        holder.dateTextView.text = item.date
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItemList(itemList: List<Operation>) {
        items = itemList
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageViewHolder: ImageView = itemView.findViewById(R.id.type_cardview_image)
        val categoryTextView: TextView = itemView.findViewById(R.id.category_cardview_input)
        val amountTextView: TextView = itemView.findViewById(R.id.amount_cardview_input)
        val dateTextView: TextView = itemView.findViewById(R.id.date_cardview_input)
    }
}