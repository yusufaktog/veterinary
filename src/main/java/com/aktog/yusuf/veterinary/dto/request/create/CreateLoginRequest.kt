package com.aktog.yusuf.veterinary.dto.request.create

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Email

data class CreateLoginRequest(
    @field:Email
    val email: String,

    @field:Length(min = 6, max = 30)
    val password: String
) {
    constructor() : this("","") {

    }
}