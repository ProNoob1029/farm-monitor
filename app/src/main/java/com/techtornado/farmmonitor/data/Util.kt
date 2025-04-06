package com.techtornado.farmmonitor.data

import com.google.android.gms.maps.model.LatLng

fun List<Double>.toLatLng() = LatLng(this[1], this[0])
fun List<List<Double>>.toLatLng() = map { it.toLatLng() }