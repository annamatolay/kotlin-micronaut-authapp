package com.palmatolay.micronaut

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("com.palmatolay.micronaut")
                .mainClass(Application.javaClass)
                .start()
    }
}