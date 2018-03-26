package com.technology.olympian.cibus.Data

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.get
import com.squareup.picasso.PicassoProvider
import com.technology.olympian.cibus.R

/**
 * Created by Yash on 14-03-2018.
 */
class ImageAdapter(private val list:ArrayList<Upload>,private val context: Context):RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.collage_row,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        var upload = list.get(position)


//        Picasso.with(context).load(imageUri).into(ivBasicImage);

        Picasso.get().load(upload.getUrl()).placeholder(R.mipmap.ic_launcher_round).centerCrop().into(holder!!.image_1)

    }


    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

        var image_1 = itemView.findViewById<ImageView>(R.id.image1)


//        fun bindViews(upload: Upload){
//            image_text.setText(upload.getName().toString()
//        }
    }
}