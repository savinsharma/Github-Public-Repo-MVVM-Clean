package com.githubrepo.project.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.githubrep.project.R
import com.githubrep.project.databinding.LayoutSingleItemViewClosePrBinding
import com.githubrepo.project.model.ClosePullRequestResponse

class ClosedPRListAdapter(private val context: Context,
                          private val closedPRList: ArrayList<ClosePullRequestResponse>,
                          val onLastPositionReached : (Int)-> Unit) :
    RecyclerView.Adapter<ClosedPRListAdapter.ClosedPRListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClosedPRListViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        return ClosedPRListViewHolder(LayoutSingleItemViewClosePrBinding.inflate(inflater))
    }

    override fun onBindViewHolder(holder: ClosedPRListViewHolder, position: Int) {
        holder.bind(closedPRList[position])
        if (position == closedPRList.size - 1)
            onLastPositionReached(position)
    }

    override fun getItemCount(): Int {
        return closedPRList.size
    }

    inner class ClosedPRListViewHolder(view: ViewDataBinding) : RecyclerView.ViewHolder(view.root) {
        private var layoutSingleItemViewClosePrBinding: LayoutSingleItemViewClosePrBinding? = null

        init {
            layoutSingleItemViewClosePrBinding = view as LayoutSingleItemViewClosePrBinding
        }

        fun bind(closePullRequestResponse: ClosePullRequestResponse) {
            layoutSingleItemViewClosePrBinding?.apply {
                closePullRequestResponse.user.let {
                    tvUserName.text = context.getString(R.string.user)
                    tvUserName.append(closePullRequestResponse.user?.login)
                    val userImage = closePullRequestResponse.user?.avatarUrl
                    Glide.with(context)
                        .load(userImage)
                        .into(ivUserImage)
                }
                tvPRTitle.text = context.getString(R.string.pr_title)
                tvPRTitle.append(closePullRequestResponse.title)

                tvPRCreatedDate.text = context.getString(R.string.created_at)
                tvPRCreatedDate.append(closePullRequestResponse.createdAt)

                tvPRCloseDate.text = context.getString(R.string.closed_at)
                tvPRCloseDate.append(closePullRequestResponse.closedAt)
            }
        }
    }
}