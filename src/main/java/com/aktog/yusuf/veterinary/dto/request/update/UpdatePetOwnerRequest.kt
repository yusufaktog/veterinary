package com.aktog.yusuf.veterinary.dto.request.update

import com.aktog.yusuf.veterinary.entity.Address
import com.aktog.yusuf.veterinary.entity.Authority
import com.aktog.yusuf.veterinary.entity.Pet

data class UpdatePetOwnerRequest(
    val name:String,
    val surname:String,
    val phoneNumber: String,
    val email:String,
    val password:String?,
    val pets:Set<Pet>?,
    val addresses:Set<Address>?,
    val authorities:Set<Authority>?

)