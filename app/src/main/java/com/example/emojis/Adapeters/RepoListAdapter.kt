package com.example.emojis.Adapeters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.emojis.R
import com.example.emojis.network.models.Repo
import kotlinx.android.synthetic.main.list_repo.view.*

/**
 * Created By Fakher_Husayn on 30-Aug-20
 **/
class RepoListAdapter(public val context: Context) :
    PagedListAdapter<Repo, RecyclerView.ViewHolder>(
        DiffUtilCallBack()
    ) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_repo, parent, false)
        return ReopItemViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as ReopItemViewHolder).bind(getItem(position), context)

    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }


    class DiffUtilCallBack : DiffUtil.ItemCallback<Repo>() {
        override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
            return oldItem == newItem

        }

    }

    class ReopItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(repo: Repo?, context: Context) {
            itemView.repoName.text = repo?.full_name
        }

    }


}

