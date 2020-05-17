package com.seminario.api.repositories

import com.seminario.api.models.Organization
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrganizationRepository: JpaRepository<Organization, Long>