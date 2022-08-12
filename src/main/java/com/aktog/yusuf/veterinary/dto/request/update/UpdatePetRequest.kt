package com.aktog.yusuf.veterinary.dto.request.update


data class UpdatePetRequest(
    val name: String?,
    val description: String?,
    val ownerId: String?
)
