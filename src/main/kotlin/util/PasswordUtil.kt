package com.util

import java.security.SecureRandom

fun checkHasForPassword(password: String, hashWithSalt: String): Boolean {
    val hashAndSalt = hash
}

fun getHashWithSalt(stringToHash: String, val saltLength: Int = 2): String {
    val salt = SecureRandom.getInstance("SHA1PRNG")
    generateSeed
}