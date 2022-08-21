package com.aktog.yusuf.veterinary.dto.request.create

import java.time.LocalDate
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Past

data class CreatePetRequest (
    @field:NotBlank
    val type: String,

    @field:NotBlank
    val genus: String,

    @field:NotBlank
    val name: String,

    @field:Past
    val birthDate: LocalDate,

    @field:NotBlank
    val description: String
) {
    constructor() : this("","","",LocalDate.now(),"") {

    }
}