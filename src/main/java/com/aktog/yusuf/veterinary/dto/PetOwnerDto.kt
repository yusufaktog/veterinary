package com.aktog.yusuf.veterinary.dto

data class PetOwnerDto @JvmOverloads constructor(

    val id:String? = "",
    val name:String,
    val surname:String,
    val phoneNumber:String,
    val email:String,

    val petIds:Set<String>? = HashSet(),
    val addressIds:Set<String>? = HashSet(),
    val authorityIds:Set<Int>? = HashSet()
)
