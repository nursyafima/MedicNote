package com.example.psiprojek

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pasien(
    var id:String,
    var nama:String,
    var keluhan:String,
    var penyakit:String,
    var tinggiBeratBadan:String,
    var tensi:String,
    var hasil:String,
    var resepObat:String) : Parcelable {

    constructor() :this("","","","","","","",""){

    }
}