package vn.toanpv.sample.arena.match.repository.data.local

import io.realm.kotlin.Realm
import io.realm.kotlin.types.RealmObject


inline fun <reified T : RealmObject> Realm.deleteAll() {
    query(T::class).find().takeIf { !it.isEmpty() }?.let {
        writeBlocking {
            delete(it)
        }
    }
}