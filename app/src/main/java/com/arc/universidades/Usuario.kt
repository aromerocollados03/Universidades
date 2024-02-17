package com.arc.universidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("usuarios")
data class Usuario(
    @PrimaryKey var nombre: String,
    @ColumnInfo(name = "correo") val correo: String,
    @ColumnInfo(name = "password")val passwd: String
)
