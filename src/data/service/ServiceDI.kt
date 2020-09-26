package com.sscott.data.service

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

fun DI.MainBuilder.bindServices() {
    bind<CemeteryService>() with singleton { CemeteryService() }
}