package ru.rovkinmax.bts.model.system.rx

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class TestSchedulers : SchedulersProvider {
    override fun io(): Scheduler = Schedulers.trampoline()
    override fun computation(): Scheduler = Schedulers.trampoline()
    override fun main(): Scheduler = Schedulers.trampoline()
}