package com.hallel.presentation.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hallel.domain.guest.GuestVO
import com.hallel.presentation.R
import com.hallel.presentation.extensions.visible
import com.hallel.presentation.extensions.visibleViews
import kotlinx.android.synthetic.main.item_guest.view.*

class HomeGuestAdapter(
    private val guestList: List<GuestVO>,
    private val listener: HomeGeneralClickListener
) :
    RecyclerView.Adapter<HomeGuestAdapter.CustomHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHolder {
        context = parent.context
        return CustomHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_guest, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return guestList.size
    }

    override fun onBindViewHolder(holder: CustomHolder, position: Int) {
        val guest = guestList[position]
        when {
            guest.image.isNullOrEmpty() -> holder.guestFallbackName.apply {
                text = guest.name
                visible()
            }
            else -> {
                Glide.with(context)
                        .load(guest.image)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.hallel_logo)
                        .optionalFitCenter()
                        .into(holder.guestPhoto)
                holder.guestContainer.visible()
                holder.guestName.text = guest.name
            }
        }
        holder.bind(guest, listener)
    }

    class CustomHolder(view: View) : RecyclerView.ViewHolder(view) {

        val guestPhoto: ImageView = view.itemGuestImgPhoto
        val guestName: TextView = view.itemGuestTxtName
        val guestContainer: ConstraintLayout = view.itemGuestContainerGuest
        val guestFallbackName: TextView = view.itemGuestTxtNameFallback

        fun bind(guest: GuestVO, listener: HomeGeneralClickListener) {
            itemView.setOnClickListener {
                listener.onAdapterItemClicked(guest.id)
            }
        }
    }
}