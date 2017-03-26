package com.androidhuman.example.simplecontacts.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class Person : RealmObject() {

    @PrimaryKey
    var id: Long = 0

    @Required
    lateinit var name: String

    var address: String? = null

    companion object {

        val PRIMARY_KEY = "id"
    }
}
