package vn.toanpv.sample.arena.match.di

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module
import org.koin.dsl.onClose
import vn.toanpv.sample.arena.match.repository.data.local.RealmTeamLocalSource
import vn.toanpv.sample.arena.match.repository.data.local.entity.MatchRealm
import vn.toanpv.sample.arena.match.repository.data.local.entity.TeamRealm
import vn.toanpv.sample.arena.repository.data.local.TeamLocalSource

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