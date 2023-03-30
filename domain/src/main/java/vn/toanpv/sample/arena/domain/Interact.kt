package vn.toanpv.sample.arena.domain

interface Interact<Param : Interact.Param, Result> {
    suspend fun execute(param: Param? = null): Result

    open class Param
}