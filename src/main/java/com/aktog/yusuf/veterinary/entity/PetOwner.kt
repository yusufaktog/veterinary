package com.aktog.yusuf.veterinary.entity

import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

@Entity
data class PetOwner @JvmOverloads constructor(
    @Id
    @Column(name = "owner_id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: String? = "",
    val name:String,
    val surname:String,
    val phoneNumber:String,

    @Column(unique = true)
    val email:String,
    val password:String,

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    val pets:Set<Pet>? = HashSet(),

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "owner_addresses",
        joinColumns = [JoinColumn(name = "owner_id", referencedColumnName = "owner_id")],
        inverseJoinColumns = [JoinColumn(name = "address_id", referencedColumnName = "address_id")]
    )
    val addresses:Set<Address>? = HashSet(),


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "owner_authorities",
        joinColumns = [JoinColumn(name = "owner_id", referencedColumnName = "owner_id")],
        inverseJoinColumns = [JoinColumn(name = "authority_id", referencedColumnName = "authority_id")]
    )
    val authorities:Set<Authority>? = HashSet()

)
