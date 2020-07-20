/*
 * Copyright (c) 2020. 
 * Developed by : Pocket Studio. 
 * Contact: tel:  +55 (61) 99948-6774 | Skype & Mail: pocketstudioapp@gmail.com | 
 * www.pocketstudio.com.br
 */

package com.pktstudio.wheresj.model

import com.google.gson.annotations.SerializedName

data class Req (
    @SerializedName("photo_url")
    val photo_url : String,
    @SerializedName("segmentos")
    val segmentos : List<String>,
    @SerializedName("_id")
    val _id : String,
    @SerializedName("fantasia")
    val fantasia : String,
    @SerializedName("endereco")
    val endereco : String,
    @SerializedName("telefone")
    val telefone : String,
    @SerializedName("email")
    val email : String,
    @SerializedName("dataDeCriacao")
    val dataDeCriacao : String,
    @SerializedName("__v")
    val __v : Int
)