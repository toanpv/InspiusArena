package vn.inspius.toanpv.arena.match.di

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module
import org.koin.dsl.onClose
import vn.inspius.toanpv.arena.match.repository.data.local.RealmTeamLocalSource
import vn.inspius.toanpv.arena.match.repository.data.local.entity.MatchRealm
import vn.inspius.toanpv.arena.match.repository.data.local.entity.TeamRealm
import vn.inspius.toanpv.arena.repository.data.local.TeamLocalSource

val localModule = module {
    single {
        Realm.open(
            RealmConfiguration.Builder(
                setOf(
                    TeamRealm::class,
                    MatchRealm::class
                )
            )
                .deleteRealmIfMigrationNeeded()
                .name("InspiusArenaRealm")
                .build()
        )
    } onClose {
        it?.close()
    }
    single<TeamLocalSource> { RealmTeamLocalSource(get()) }
}