package vn.toanpv.sample.arena.repository.data

interface EntityMapper<E, D> where D : EntityMapper<E, D> {
    fun convert(): E
}