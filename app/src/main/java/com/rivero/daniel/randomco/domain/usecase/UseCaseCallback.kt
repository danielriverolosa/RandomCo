package com.rivero.daniel.randomco.domain.usecase


interface UseCaseCallback <in T> {
    fun onSuccess(data: T)

    fun onError(t: Throwable)
}
