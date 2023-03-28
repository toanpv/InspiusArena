package vn.inspius.toanpv.arena.domain.team.sync

import kotlinx.coroutines.flow.Flow
import vn.inspius.toanpv.arena.domain.Interact

interface GetSyncDataStatusInteract : Interact<Interact.Param, Flow<Boolean>>