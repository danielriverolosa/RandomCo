package com.rivero.daniel.randomco.domain.exception


class HttpException(httpError: Int) : RuntimeException("Http error: $httpError")