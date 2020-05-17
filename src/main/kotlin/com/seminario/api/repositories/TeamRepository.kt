package com.seminario.api.repositories

import com.seminario.api.models.Team
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface TeamRepository: JpaRepository<Team, Long>{
    fun deleteById(id: Long?): Long?
}