package com.rivero.daniel.randomco.domain.exception


open class ConnectivityException : RepositoryException {
    constructor() : super()
    constructor(message: String) : super(message)
}
