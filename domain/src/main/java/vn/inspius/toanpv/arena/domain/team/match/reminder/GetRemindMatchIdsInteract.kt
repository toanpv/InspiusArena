package vn.inspius.toanpv.arena.domain.team.match.reminder

import kotlinx.coroutines.flow.Flow
import vn.inspius.toanpv.arena.domain.Interact

interface GetRemindMatchIdsInteract : Interact<Interact.Param, Flow<Set<String>>>