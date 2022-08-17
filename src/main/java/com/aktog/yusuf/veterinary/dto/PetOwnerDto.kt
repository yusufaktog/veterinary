package com.aktog.yusuf.veterinary.dto

data class PetOwnerDto @JvmOverloads constructor(

    val id: String? = "",
    val name: String,
    val surname: String,
    val phoneNumber: String,
    val email: String,

    val petIds: Set<String>? = HashSet(),
    val addresses: Set<String>? = HashSet(),
    val authorities: Set<String>? = HashSet()
) {
    override fun toString(): String {
        return "$name  $surname  $phoneNumber  $email"
    }
}
