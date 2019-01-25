package com.example.maxdo.helptronica.core.mvi

import com.hwangjr.rxbus.Bus



object RxBus {

    private var sBus: Bus = Bus()

    @Synchronized
    fun get(): Bus {
       return sBus
    }

}