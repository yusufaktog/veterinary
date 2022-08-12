package com.aktog.yusuf.veterinary.entity

import org.hibernate.annotations.GenericGenerator
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Address @JvmOverloads constructor(
    @Id
    @Column(name = "address_id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: String? = "",
    val country:String,
    val city:String,
    val street:String,
    val buildingNumber:Int,
    val apartmentNumber:Int,
    val zipCode:Int,
)
