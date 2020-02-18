package com.hallel.presentation.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hallel.domain.sponsor.SponsorVO
import com.hallel.presentation.R
import com.hallel.presentation.extensions.gone
import com.hallel.presentation.extensions.visible
import kotlinx.android.synthetic.main.item_sponsor.view.*

class HomeSponsorAdapter(
    private val sponsorList: List<SponsorVO>,
    private val listener: HomeSponsorAdapterListener
) :
    RecyclerView.Adapter<HomeSponsorAdapter.CustomHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHolder {
        context = parent.context
        return CustomHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_sponsor, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return sponsorList.size
    }

    override fun onBindViewHolder(holder: CustomHolder, position: Int) {
        val sponsor = sponsorList[position]
        if (sponsor.logo != null) {
            Glide.with(context)
                .load(sponsor.logo)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.hallel_logo)
                .into(holder.sponsorImage)
            holder.sponsorImage.visible()
            holder.sponsorFallbackName.gone()
        } else {
            holder.sponsorFallbackName.apply {
                visible()
                text = sponsor.name
            }
            holder.sponsorImage.gone()
            holder.sponsorFallbackName.gone()
        }
        holder.bind(sponsor.link, listener)
    }

    class CustomHolder(view: View) : RecyclerView.ViewHolder(view) {

        val sponsorImage: ImageView = view.itemSponsorImgPhoto
        val sponsorFallbackName: TextView = view.itemSponsorTxtNameFallback

        fun bind(link: String?, listener: HomeSponsorAdapterListener) {
            itemView.setOnClickListener {
                listener.onItemClicked(link)
            }
        }
    }

    interface HomeSponsorAdapterListener {

        fun onItemClicked(sponsorLink: String?)
    }
}