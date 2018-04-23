package com.rivero.daniel.randomco.domain.exception


open class RepositoryException : RuntimeException {

    constructor() : super()

    constructor(message: String) : super(message)
}