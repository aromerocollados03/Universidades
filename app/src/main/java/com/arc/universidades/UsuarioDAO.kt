package com.arc.universidades

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UsuarioDAO {
    @Insert
    suspend fun insertarUsuario(usuario: Usuario)

    @Query("SELECT * FROM usuarios WHERE nombre = :nombre LIMIT 1")
    suspend fun buscarPorNombre(nombre: String): Usuario?

    @Query("SELECT * FROM usuarios WHERE nombre = :nombre AND password = :password LIMIT 1")
    suspend fun buscarUsuario(nombre: String, password: String): Usuario?
}