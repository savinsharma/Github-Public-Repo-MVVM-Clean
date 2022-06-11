package com.githubrepo.project.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.githubrep.project.databinding.LayoutSingleItemViewClosePrBinding
import com.githubrepo.project.model.ClosePullRequestResponse

class ClosedPRListAdapter(private val closedPRList : List<ClosePullRequestResponse>) : RecyclerView.Adapter<ClosedPRListAdapter.ClosedPRListViewHolder>() {

    class ClosedPRListViewHolder(view: LayoutSingleItemViewClosePrBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClosedPRListViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        return ClosedPRListViewHolder(LayoutSingleItemViewClosePrBinding.inflate(inflater))
    }

    override fun onBindViewHolder(holder: ClosedPRListViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return closedPRList.size
    }
}