package com.hallel.domain.sponsor

data class SponsorVO(
    val id: Int,
    val name: String,
    val logo: String? = null,
    val link: String? = null
)