package dev.best.covidkotlin.data.remote

import com.google.gson.Gson
import dev.best.covidkotlin.utils.safeLog
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.lang.Exception

fun Throwable.toBaseException(): BaseException {
    return when (val throwable = this) {
        is BaseException -> throwable
        is IOException -> BaseException.toNetworkError(throwable)
        is HttpException -> {
            val response = throwable.response()
            val httpCode = throwable.code()

            if (response?.errorBody() == null) {
                BaseException.toHttpError(
                    httpCode = httpCode,
                    response = response
                )
            }

            val serverErrorResponseBody = try {
                response?.errorBody()?.string() ?: ""
            } catch (e: Exception) {
                e.safeLog()
                ""
            }

            val serverErrorResponse =
                try {
                    Gson().fromJson(serverErrorResponseBody, ServerErrorResponse::class.java)
                } catch (e: Exception) {
                    e.safeLog()
                    ServerErrorResponse()
                }

            if (serverErrorResponse != null) {
                BaseException.toServerError(
                    serverErrorResponse = serverErrorResponse,
                    response = response,
                    httpCode = httpCode
                )
            } else {
                BaseException.toHttpError(
                    response = response,
                    httpCode = httpCode
                )
            }
        }
        else -> BaseException.toUnexpectedError(throwable)
    }
}

class BaseException(
    val errorType: ErrorType,
    val serverErrorResponse: ServerErrorResponse? = null,
    val response: Response<*>? = null,
    val httpCode: Int = 0,
    cause: Throwable? = null
) : RuntimeException(cause?.message, cause) {
    override val message: String?
        get() = when (errorType) {
            ErrorType.HTTP -> response?.message()
            ErrorType.NETWORK -> cause?.message
            ErrorType.SERVER -> serverErrorResponse?.errors?.getOrNull(0)
            ErrorType.UNEXPECTED -> cause?.message
        }

    companion object {
        fun toHttpError(response: Response<*>?, httpCode: Int) =
            BaseException(
                errorType = ErrorType.HTTP,
                response = response,
                httpCode = httpCode
            )

        fun toNetworkError(cause: Throwable) =
            BaseException(
                errorType = ErrorType.NETWORK,
                cause = cause
            )

        fun toServerError(
            serverErrorResponse: ServerErrorResponse,
            response: Response<*>?,
            httpCode: Int
        ) =
            BaseException(
                errorType = ErrorType.SERVER,
                serverErrorResponse = serverErrorResponse,
                response = response,
                httpCode = httpCode
            )

        fun toUnexpectedError(cause: Throwable?) = BaseException(
            errorType = ErrorType.UNEXPECTED,
            cause = cause
        )
    }
}

enum class ErrorType {
    NETWORK,
    HTTP,
    SERVER,
    UNEXPECTED
}

data class ServerErrorResponse(
    val message: String? = null,
    val errors: List<String>? = null
)

