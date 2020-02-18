package com.hallel.domain.guest

data class GuestVO(
    val id: Int,
    val name: String,
    val type: List<GuestType>,
    val image: String?
)