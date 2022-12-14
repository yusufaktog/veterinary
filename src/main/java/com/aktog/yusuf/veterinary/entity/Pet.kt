package com.aktog.yusuf.veterinary.entity

import org.hibernate.annotations.GenericGenerator
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
data class Pet @JvmOverloads constructor(
    @Id
    @Column(name = "pet_id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: String? = "",
    val name: String,
    val birthDate: LocalDate,
    val type: String,
    val genus: String,
    val description: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    val owner: PetOwner,
) {

    override fun toString(): String {
        return "$name $birthDate $type $genus $description"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Pet

        if (name != other.name) return false
        if (birthDate != other.birthDate) return false
        if (type != other.type) return false
        if (genus != other.genus) return false
        if (description != other.description) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + birthDate.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + genus.hashCode()
        result = 31 * result + description.hashCode()
        return result
    }


}

