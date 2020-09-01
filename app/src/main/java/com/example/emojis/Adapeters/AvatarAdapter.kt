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
import com.example.emojis.EmojiViewModel
import com.example.emojis.R
import com.example.emojis.network.models.Avatar

/**
 * Created By Fakher_Husayn on 31-Aug-20
 **/
class AvatarAdapter(val emojiViewModel: EmojiViewModel) :
    RecyclerView.Adapter<AvatarAdapter.ViewHolder>() {

    var data = ArrayList<Avatar>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvatarAdapter.ViewHolder {
        return AvatarAdapter.ViewHolder.from(
            parent
        )
    }

    override fun onBindViewHolder(holder: AvatarAdapter.ViewHolder, position: Int) {

        val item = data[position]

        holder.bind(item)
        holder.itemView.setOnClickListener {
            Toast.makeText(
                holder.Image.context,
                "Deleteig item " + holder.adapterPosition,
                Toast.LENGTH_LONG
            ).show()
            deleteItems(holder.adapterPosition)
        }

    }

    fun deleteItems(position: Int) {
        emojiViewModel.deleteAvatar(data.get(position))
        data.removeAt(position)
        notifyItemRemoved(position)

    }


    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val Image: ImageView = itemView.findViewById(R.id.AvatarImage)

        fun bind(item: Avatar) {
            val imageUrl = item.avatar_url

            val imgUri = imageUrl.toUri().buildUpon().scheme("https").build()
            Glide.with(Image.context)
                .load(imgUri).apply(RequestOptions())
                .into(Image)
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.avatar_item, parent, false)
                return ViewHolder(view)
            }
        }
    }

}