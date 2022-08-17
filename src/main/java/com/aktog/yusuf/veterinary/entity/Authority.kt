package com.aktog.yusuf.veterinary.entity

import javax.persistence.*

@Entity
data class Authority @JvmOverloads constructor(
    @Id
    @Column(name = "authority_id")
    val id:Int? = 0,
    val name:String,

    @ManyToMany(mappedBy = "authorities")
    val owners:Set<PetOwner>? = HashSet()
)