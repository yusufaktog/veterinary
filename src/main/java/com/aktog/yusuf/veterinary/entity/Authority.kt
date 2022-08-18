package com.aktog.yusuf.veterinary.entity

import javax.persistence.*

@Entity
data class Authority @JvmOverloads constructor(
    @Id
    @Column(name = "authority_id")
    val name:String,

    @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
    val owners:Set<PetOwner>? = HashSet()
)