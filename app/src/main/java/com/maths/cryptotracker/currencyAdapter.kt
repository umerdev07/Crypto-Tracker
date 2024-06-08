package com.maths.cryptotracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.DecimalFormat

class currencyAdapter (
    private var currencyModals: ArrayList<CurrencyModel>,
    private val context: Context
):RecyclerView.Adapter<currencyAdapter.CurrencyViewHolder>(){

    var df2 = DecimalFormat("#.##")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.items_set , parent ,false)
        return CurrencyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return currencyModals.size
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val modal = currencyModals[position]
        holder.nametv.text = modal.name
        holder.pricetv.text = "$ ${df2.format(modal.price)}"
        holder.symboltv.text = modal.symbol
    }

    inner class CurrencyViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
          val nametv:TextView = itemView.findViewById(R.id.name)
          val pricetv:TextView = itemView.findViewById(R.id.price)
        val symboltv:TextView = itemView.findViewById(R.id.symbol)

    }
    fun filterList(filterList:ArrayList<CurrencyModel>){
        currencyModals = filterList
        notifyDataSetChanged()
    }
}