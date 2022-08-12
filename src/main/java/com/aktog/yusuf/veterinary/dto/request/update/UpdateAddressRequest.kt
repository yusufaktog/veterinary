package com.aktog.yusuf.veterinary.dto.request.update

import javax.validation.constraints.Positive

data class UpdateAddressRequest(

    val country: String?,

    val city: String?,

    val street: String?,

    @field:Positive
    val buildingNumber: Int?,

    @field:Positive
    val apartmentNumber: Int?,

    @field:Positive
    val zipCode: Int?
)