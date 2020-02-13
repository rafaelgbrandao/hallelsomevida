package com.hallel.domain.guest

import com.hallel.data.guest.GuestType

data class GuestVO(
    val id: Int,
    val name: String,
    val type: List<GuestType>,
    val image: String?
)