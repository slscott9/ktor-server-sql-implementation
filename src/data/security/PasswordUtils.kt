package com.sscott.data.security

import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils
import java.security.SecureRandom

fun getHashWithSalt(stringToHash : String, saltLength: Int = 32) : String {
    val salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLength) //more secure and not psuedo random

    val saltAsHex = Hex.encodeHexString(salt) //get salt  to hex string

    val hash = DigestUtils.sha256Hex("$saltAsHex$stringToHash") //add the salthex to the string to hash

    return "$saltAsHex:$hash" //returns our salted hash
}

//calls this function to verify user entered a correct password
//Where do we call this function -> when user registers to the api
fun checkHashForPassword(password : String, hashWithSalt: String ) : Boolean {

    val hashAndSalt = hashWithSalt.split(":") //password is in this format -> "$saltAsHex:$hash" split the string at the colon

    val salt = hashAndSalt[0]
    val hash = hashAndSalt[1]

    val passwordHash = DigestUtils.sha256Hex("$salt$password")

    return hash == passwordHash
}