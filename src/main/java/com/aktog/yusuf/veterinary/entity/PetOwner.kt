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
    val name: String,
    val surname: String,

    @Column(unique = true)
    val phoneNumber: String,

    @Column(unique = true)
    val email: String,

    val password: String,

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinTable(
        name = "owner_authorities",
        joinColumns = [JoinColumn(name = "owner_id", referencedColumnName = "owner_id")],
        inverseJoinColumns = [JoinColumn(name = "authority_id", referencedColumnName = "authority_id")]
    )
    val authorities: Set<Authority>,

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    val pets: Set<Pet>? = HashSet(),

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "owner_addresses",
        joinColumns = [JoinColumn(name = "owner_id", referencedColumnName = "owner_id")],
        inverseJoinColumns = [JoinColumn(name = "address_id", referencedColumnName = "address_id")]
    )
    val addresses: Set<Address>? = HashSet(),


    ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PetOwner

        if (id != other.id) return false
        if (name != other.name) return false
        if (surname != other.surname) return false
        if (phoneNumber != other.phoneNumber) return false
        if (email != other.email) return false
        if (password != other.password) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        result = 31 * result + surname.hashCode()
        result = 31 * result + phoneNumber.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + password.hashCode()
        return result
    }

    override fun toString(): String {
        return "PetOwner(id=$id, name='$name', surname='$surname', phoneNumber='$phoneNumber', email='$email', password='$password')"
    }

}
