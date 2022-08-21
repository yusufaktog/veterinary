package com.aktog.yusuf.veterinary.dto.request.update

import com.aktog.yusuf.veterinary.entity.Address
import com.aktog.yusuf.veterinary.entity.Authority
import com.aktog.yusuf.veterinary.entity.Pet
import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class UpdatePetOwnerRequest(
    @field:NotBlank
    val name: String,

    @field:NotBlank
    val surname: String,

    @field:Length(min = 10, max = 11)
    val phoneNumber: String,

    @field:Email
    val email: String,

    val pets:Set<Pet>?,
    val addresses:Set<Address>?,
    val authorities:Set<Authority>?

)