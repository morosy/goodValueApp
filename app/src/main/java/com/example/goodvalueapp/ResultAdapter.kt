package com.example.goodvalueapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ResultAdapter(private val productList: List<ProductData>) : RecyclerView.Adapter<ResultAdapter.ResultViewHolder>() {
    
    class ResultViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rankText: TextView = view.findViewById(R.id.rankText)
        val productName: TextView = view.findViewById(R.id.productName)
        val priceText: TextView = view.findViewById(R.id.priceText)
        val weightText: TextView = view.findViewById(R.id.weightText)
        val pricePerGramText: TextView = view.findViewById(R.id.pricePerGramText)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_result, parent, false)
        return ResultViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val product = productList[position]
        
        holder.rankText.text = "${position + 1}位"
        holder.productName.text = product.name
        holder.priceText.text = "${product.price.toInt()}円"
        holder.weightText.text = "${product.weight.toInt()}g"
        holder.pricePerGramText.text = String.format("%.2f円/g", product.pricePerGram)
        
        // 1位は背景色を変更
        if (position == 0) {
            holder.itemView.setBackgroundResource(R.drawable.winner_background)
        } else {
            holder.itemView.setBackgroundResource(R.drawable.input_box_background)
        }
    }
    
    override fun getItemCount(): Int = productList.size
}