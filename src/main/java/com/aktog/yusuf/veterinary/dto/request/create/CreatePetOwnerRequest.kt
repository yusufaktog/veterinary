package com.aktog.yusuf.veterinary.dto.request.create

import com.aktog.yusuf.veterinary.entity.Authority
import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class CreatePetOwnerRequest @JvmOverloads constructor(
    @field:NotBlank
    val name: String,

    @field:NotBlank
    val surname: String,

    @field:Length(min = 10, max = 11)
    val phoneNumber: String,

    @field:Email
    val email: String,

    @field:Length(min = 6, max = 30)
    val password: String,

    val authorities:Set<Authority>? = HashSet()

) {
    constructor() : this("", "", "", "", "") {

    }
}