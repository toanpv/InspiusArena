package vn.toanpv.sample.arena.domain.team.sync

import kotlinx.coroutines.flow.Flow
import vn.toanpv.sample.arena.domain.Interact

interface GetSyncDataStatusInteract : Interact<Interact.Param, Flow<Boolean>>