package com.example.demo

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@RestController
class DemoApplication {
    @GetMapping("/hello")
    fun hello(@RequestParam(value = "name", defaultValue = "World") name: String): String {
        return if (Visitors.exists(name))
            "Welcome back, $name"
        else {
            Visitors.insertName(name)
            String.format("Hello %s!", name)
        }
    }
}

fun main(args: Array<String>) {
    initDB()
    runApplication<DemoApplication>(*args)
}

fun initDB(){
    val dbUrl = "jdbc:postgresql://localhost:5432/springsample"
    val dbUser = "postgres"
    val dbPass = ""

    Database.connect(dbUrl, driver = "org.postgresql.Driver", user = dbUser, password = dbPass)
    transaction {
        addLogger(StdOutSqlLogger)
    }
}