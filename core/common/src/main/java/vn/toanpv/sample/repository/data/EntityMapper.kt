package vn.toanpv.sample.repository.data

interface EntityMapper<E, D> where D : EntityMapper<E, D> {
    fun convert(): E
}