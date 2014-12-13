package com.digitalriver.cartsandra.cartapp.server.rxnetty;

import com.digitalriver.cartsandra.cartapp.commands.HelloCommand;
import com.digitalriver.cartsandra.cartapp.commands.HelloToCommand;
import com.netflix.karyon.transport.http.health.HealthCheckEndpoint;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import io.reactivex.netty.protocol.http.server.RequestHandler;
import rx.Observable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author Nitesh Kant
 */

/**
 * @author Nitesh Kant
 */
public class RxNettyHandler implements RequestHandler<ByteBuf, ByteBuf> {

    private final String healthCheckUri;
    private final HealthCheckEndpoint healthCheckEndpoint;

    public RxNettyHandler(String healthCheckUri, HealthCheckEndpoint healthCheckEndpoint) {
        this.healthCheckUri = healthCheckUri;
        this.healthCheckEndpoint = healthCheckEndpoint;
    }

    @Override
    public Observable<Void> handle(HttpServerRequest<ByteBuf> request, HttpServerResponse<ByteBuf> response) {
        if (request.getUri().startsWith(healthCheckUri)) {
            return healthCheckEndpoint.handle(request, response);
        } else if (request.getUri().startsWith("/hello/to/")) {
            int prefixLength = "/hello/to/".length();
            String userName = request.getPath().substring(prefixLength);
            Future<String> helloResponse = new HelloToCommand(userName).queue();
            try {
                String msg = helloResponse.get();

                return response.writeStringAndFlush(("{\"Message\":\"" + msg + "\"}"));
            } catch (InterruptedException e) {
                response.setStatus(HttpResponseStatus.BAD_REQUEST);
                return response.writeStringAndFlush(
                        "{\"Error\":\"Please provide a username to say hello. The URI should be /hello/to/{username}\"}");
            } catch (ExecutionException e) {
                response.setStatus(HttpResponseStatus.BAD_REQUEST);
                return response.writeStringAndFlush(
                        "{\"Error\":\"Please provide a username to say hello. The URI should be /hello/to/{username}\"}");
            }
        } else if (request.getUri().startsWith("/hello")) {
                String helloResponse = new HelloCommand().execute();
                return response.writeStringAndFlush(helloResponse);
        } else {
            response.setStatus(HttpResponseStatus.NOT_FOUND);
            return response.close();
        }
    }
}
