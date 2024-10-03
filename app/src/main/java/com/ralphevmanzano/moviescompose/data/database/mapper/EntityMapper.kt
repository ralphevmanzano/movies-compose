package com.ralphevmanzano.moviescompose.data.database.mapper

interface EntityMapper<Domain, Entity> {
    fun asEntity(domain: Domain): Entity
    fun asDomain(entity: Entity): Domain
}