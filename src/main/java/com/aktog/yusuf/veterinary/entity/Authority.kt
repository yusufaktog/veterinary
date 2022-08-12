package com.aktog.yusuf.veterinary.entity

import javax.persistence.*

@Entity
data class Authority(
    @Id
    @Column(name = "authority_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id:Int? = 0,
    val name:String
)