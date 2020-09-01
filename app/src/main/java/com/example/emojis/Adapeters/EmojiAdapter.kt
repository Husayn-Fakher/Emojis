package com.example.emojis.Adapeters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.emojis.R
import com.example.emojis.network.models.Emoji

/**
 * Created By Fakher_Husayn on 17-Aug-20
 **/


class EmojiAdapter() : RecyclerView.Adapter<EmojiAdapter.ViewHolder>() {

    var data = ArrayList<Emoji>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(
            parent
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = data[position]

        holder.bind(item)
        holder.itemView.setOnClickListener {
            Toast.makeText(
                holder.Image.context,
                "Deleting item ",
                Toast.LENGTH_LONG
            ).show()

            deleteItems(holder.adapterPosition)
        }
    }

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val Image: ImageView = itemView.findViewById(R.id.Emoji)


        fun bind(item: Emoji) {
            val imageUrl = item.url

            val imgUri = imageUrl.toUri().buildUpon().scheme("https").build()
            Glide.with(Image.context)
                .load(imgUri).apply(RequestOptions())
                .into(Image)
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.list_emoji, parent, false)

                return ViewHolder(view)
            }
        }
    }

    fun swapItems(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition..toPosition - 1) {
                data.set(i, data.set(i + 1, data.get(i)));
            }
        } else {
            for (i in fromPosition..toPosition + 1) {
                data.set(i, data.set(i - 1, data.get(i)));
            }
        }

        notifyItemMoved(fromPosition, toPosition)
    }

    fun deleteItems(position: Int) {

        data.removeAt(position)
        notifyItemRemoved(position)
    }

}