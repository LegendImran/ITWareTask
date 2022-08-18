package com.developer.code.itwaremachinetest.adapter

import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.developer.code.itwaremachinetest.R
import com.developer.code.itwaremachinetest.response.Data
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter(var myContext: Activity,var movies : ArrayList<Data>,var itemClicked : (Data)->Unit): RecyclerView.Adapter<MovieAdapter.RviewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= RviewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false),myContext
    )
    override fun onBindViewHolder(holder: RviewHolder, position: Int) {
        holder.name.text = movies.get(position).movie_name
        if(movies.get(position).status == 1){
            holder.fav.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.ic_heart))
        }
        else{
            holder.fav.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.ic_heart_filled))
        }
        holder.root.setOnClickListener{
            itemClicked(movies.get(position))
        }

    }

    override fun getItemCount(): Int {
        return movies.size
    }

    class RviewHolder(itemView: View, myContext: Activity) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tvName)
        var fav = itemView.findViewById<ImageView>(R.id.ivFav)
        var root = itemView.findViewById<ConstraintLayout>(R.id.clRoot)

    }

}
