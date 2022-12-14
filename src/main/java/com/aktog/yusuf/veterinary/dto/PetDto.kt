package com.aktog.yusuf.veterinary.dto

import com.fasterxml.jackson.annotation.JsonInclude

data class PetDto @JvmOverloads constructor(

    val id: String? = "",
    val name: String,
    val age: Int,
    val type: String,
    val genus: String,
    val description: String,
    val ownerName: String? = "None",

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    val ownerId: String? = ""
) {
    override fun toString(): String {
        return "$name  $age  $type  $genus  $description $ownerName"
    }
}