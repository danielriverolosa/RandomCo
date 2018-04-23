package com.rivero.daniel.randomco.test.help

import com.rivero.daniel.randomco.domain.Location
import com.rivero.daniel.randomco.domain.User
import com.rivero.daniel.randomco.domain.usecase.UseCaseCallback
import org.mockito.invocation.InvocationOnMock
import java.lang.IllegalArgumentException
import java.util.*


fun <T, R> InvocationOnMock.onAnswer(supplier: () -> R): Any? {
    var callback: UseCaseCallback<T>? = null

    arguments.forEach { arg ->
        if (arg is UseCaseCallback<*>) {
            callback = arg as UseCaseCallback<T>
            return@forEach
        }
    }

    if (callback == null) throw IllegalArgumentException("missing callback argument")

    val result = supplier.invoke()
    if (result is Throwable) {
        callback?.onError(result)
    } else {
        callback?.onSuccess(result as T)
    }
    return null
}

fun mockUser(): User = User(
        "username",
        "name",
        "surname",
        "gender",
        "email",
        "phone",
        Date(),
        Location("street", "city", "state")
)