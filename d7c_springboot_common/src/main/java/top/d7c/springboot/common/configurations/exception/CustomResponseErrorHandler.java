package top.d7c.springboot.common.configurations.exception;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.UnknownHttpStatusCodeException;

/**
 * @Title: CustomResponseErrorHandler
 * @Package: top.d7c.springboot.common.configurations.exception
 * @author: 吴佳隆
 * @date: 2020年7月28日 上午8:56:03
 * @Description: Spring's default implementation of the {@link ResponseErrorHandler} interface.
 * <p>This error handler checks for the status code on the {@link ClientHttpResponse}:
 * Any code with series {@link org.springframework.http.HttpStatus.Series#CLIENT_ERROR}
 * or {@link org.springframework.http.HttpStatus.Series#SERVER_ERROR} is considered to be
 * an error; this behavior can be changed by overriding the {@link #hasError(HttpStatus)}
 * method. Unknown status codes will be ignored by {@link #hasError(ClientHttpResponse)}.
 */
public class CustomResponseErrorHandler implements ResponseErrorHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomResponseErrorHandler.class);

    /**
     * Delegates to {@link #hasError(HttpStatus)} (for a standard status enum value) or
     * {@link #hasError(int)} (for an unknown status code) with the response status code.
     * @see ClientHttpResponse#getRawStatusCode()
     * @see #hasError(HttpStatus)
     * @see #hasError(int)
     */
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        int rawStatusCode = response.getRawStatusCode();
        HttpStatus statusCode = HttpStatus.resolve(rawStatusCode);
        return (statusCode != null ? hasError(statusCode) : hasError(rawStatusCode));
    }

    /**
     * Template method called from {@link #hasError(ClientHttpResponse)}.
     * <p>The default implementation checks {@link HttpStatus#isError()}.
     * Can be overridden in subclasses.
     * @param statusCode the HTTP status code as enum value
     * @return {@code true} if the response indicates an error; {@code false} otherwise
     * @see HttpStatus#isError()
     */
    protected boolean hasError(HttpStatus statusCode) {
        return statusCode.isError();
    }

    /**
     * Template method called from {@link #hasError(ClientHttpResponse)}.
     * <p>The default implementation checks if the given status code is
     * {@code HttpStatus.Series#CLIENT_ERROR CLIENT_ERROR} or
     * {@code HttpStatus.Series#SERVER_ERROR SERVER_ERROR}.
     * Can be overridden in subclasses.
     * @param unknownStatusCode the HTTP status code as raw value
     * @return {@code true} if the response indicates an error; {@code false} otherwise
     * @since 4.3.21
     * @see HttpStatus.Series#CLIENT_ERROR
     * @see HttpStatus.Series#SERVER_ERROR
     */
    protected boolean hasError(int unknownStatusCode) {
        HttpStatus.Series series = HttpStatus.Series.resolve(unknownStatusCode);
        return (series == HttpStatus.Series.CLIENT_ERROR || series == HttpStatus.Series.SERVER_ERROR);
    }

    /**
     * Delegates to {@link #handleError(ClientHttpResponse, HttpStatus)} with the
     * response status code.
     * @throws UnknownHttpStatusCodeException in case of an unresolvable status code
     * @see #handleError(ClientHttpResponse, HttpStatus)
     */
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatus statusCode = HttpStatus.resolve(response.getRawStatusCode());
        if (statusCode == null) {
            logger.error("UNKNOWN_ERROR =======> statusText: {}, rawStatusCode: {}", response.getStatusText(),
                    response.getRawStatusCode());
        }
        handleError(response, statusCode);
    }

    /**
     * Handle the error in the given response with the given resolved status code.
     * <p>The default implementation throws an {@link HttpClientErrorException}
     * if the status code is {@link HttpStatus.Series#CLIENT_ERROR}, an
     * {@link HttpServerErrorException} if it is {@link HttpStatus.Series#SERVER_ERROR},
     * and an {@link UnknownHttpStatusCodeException} in other cases.
     * @since 5.0
     * @see HttpClientErrorException#create
     * @see HttpServerErrorException#create
     */
    protected void handleError(ClientHttpResponse response, HttpStatus statusCode) throws IOException {
        switch (statusCode.series()) {
            case CLIENT_ERROR:
                logger.error("CLIENT_ERROR =======> statusText: {}, HttpStatus:{value: {}, reasonPhrase: {}}, ",
                        response.getStatusText(), statusCode.value(), statusCode.getReasonPhrase());
            case SERVER_ERROR:
                logger.error("SERVER_ERROR =======> statusText: {}, HttpStatus:{value: {}, reasonPhrase: {}}, ",
                        response.getStatusText(), statusCode.value(), statusCode.getReasonPhrase());
            default:
                logger.error("UNKNOWN_ERROR =======> statusText: {}, HttpStatus:{value: {}, reasonPhrase: {}}, ",
                        response.getStatusText(), statusCode.value(), statusCode.getReasonPhrase());
        }
    }

}