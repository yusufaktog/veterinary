package com.aktog.yusuf.veterinary.dto

data class AddressDto @JvmOverloads constructor(
    val id: String? = "",
    val country: String,
    val city: String,
    val street: String,
    val buildingNumber: Int,
    val apartmentNumber: Int,
    val zipCode: Int,
) {
    override fun toString(): String {
        return "$country $city $street $buildingNumber $apartmentNumber $zipCode"
    }
}
