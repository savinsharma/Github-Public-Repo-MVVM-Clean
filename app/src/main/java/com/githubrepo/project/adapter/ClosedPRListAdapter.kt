package com.githubrepo.project.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.githubrep.project.databinding.LayoutSingleItemViewClosePrBinding
import com.githubrepo.project.model.ClosePullRequestResponse

class ClosedPRListAdapter(private val closedPRList : ArrayList<ClosePullRequestResponse>) : RecyclerView.Adapter<ClosedPRListAdapter.ClosedPRListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClosedPRListViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        return ClosedPRListViewHolder(LayoutSingleItemViewClosePrBinding.inflate(inflater))
    }

    override fun onBindViewHolder(holder: ClosedPRListViewHolder, position: Int) {
        holder.bind(closedPRList[position])
    }

    override fun getItemCount(): Int {
        return closedPRList.size
    }

    inner class ClosedPRListViewHolder(view: ViewDataBinding) : RecyclerView.ViewHolder(view.root) {
        var layoutSingleItemViewClosePrBinding: LayoutSingleItemViewClosePrBinding? = null

        init {
            layoutSingleItemViewClosePrBinding = view as LayoutSingleItemViewClosePrBinding
        }

        fun bind(closePullRequestResponse : ClosePullRequestResponse){

        }

    }
}