package com.example.demo

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Visitors : Table() {
    val name = varchar("name", 50)

    fun exists(newname:String): Boolean =
        transaction {
            Visitors.selectAll().map { it[name] }.contains(newname)
        }

    fun insertName(newName:String) = transaction {
        Visitors.insert { it[name] = newName }
    }
}