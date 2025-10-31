package com.util

import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils
import java.security.SecureRandom

fun checkHasForPassword(password: String, hashWithSalt: String): Boolean {
    val hashAnsSalt = hashWithSalt.split(":")
    val salt = hashAnsSalt[0]
    val hash = hashAnsSalt[1]
    val passwordHash = DigestUtils.sha256Hex("$salt$password")
    return hash == passwordHash
}

fun getHashWithSalt(stringToHash: String, saltLength: Int = 2): String {
    val salt = SecureRandom.getInstance("SHA1PRNG")
        .generateSeed(saltLength)
    val saltAsHex = Hex.encodeHexString(salt)
    val hash = DigestUtils.sha256Hex("$saltAsHex$stringToHash")
    return "$saltAsHex:$hash"
}