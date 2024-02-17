package com.arc.universidades

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Usuario::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDAO
}
