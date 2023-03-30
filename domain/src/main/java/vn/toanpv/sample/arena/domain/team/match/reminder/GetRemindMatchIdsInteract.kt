package vn.toanpv.sample.arena.domain.team.match.reminder

import kotlinx.coroutines.flow.Flow
import vn.toanpv.sample.arena.domain.Interact

interface GetRemindMatchIdsInteract : Interact<Interact.Param, Flow<Set<String>>>