package vn.toanpv.sample

interface Interact<Param : Interact.Param, Result> {
    suspend fun execute(param: Param? = null): Result

    open class Param
}