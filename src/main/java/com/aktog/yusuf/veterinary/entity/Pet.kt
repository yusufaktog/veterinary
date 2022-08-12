package com.aktog.yusuf.veterinary.entity

import org.hibernate.annotations.GenericGenerator
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date
import javax.persistence.*

@Entity
data class Pet @JvmOverloads constructor(
    @Id
    @Column(name = "pet_id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: String? = "",
    val name:String,
    val birthDate: LocalDate,
    val type:String,
    val genus:String,
    val description:String,

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    val owner:PetOwner,
)

