/*
 * Copyright (c) 2020.
 * Developed by : Pocket Studio.
 * Contact: tel:  +55 (61) 99948-6774 | Skype & Mail: pocketstudioapp@gmail.com |
 * www.pocketstudio.com.br
 */



package com.pktstudio.wheresj.model

import com.google.gson.annotations.SerializedName

data class WhereCompany (

    var __v: Int? = 0,
    var _id: String? = "",
    var dataDeCriacao: String? = "",
    var email: String ?="",
    var endereco: String ?="",
    var fantasia: String ?="",
    var photo_url: String ?="",
    //val segmentos: List<String>? ,
    var telefone: String? =""

)