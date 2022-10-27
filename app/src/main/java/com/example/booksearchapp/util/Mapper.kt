package com.example.booksearchapp.util

interface Mapper<FROM, TO> {
    fun map(from: FROM): TO
}