package com.kotlinspring.entity

import jakarta.persistence.*

@Entity
@Table(name = "roles")
data class Role (

    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "roles_seq")
    @SequenceGenerator(name = "roles_seq", sequenceName = "roles_sequence", allocationSize = 1)
    val id: Long= 0,
    val name: String
){

}