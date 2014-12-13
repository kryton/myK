package com.digitalriver.cartsandra.cartapp.server.rxnetty;

import com.digitalriver.cartsandra.cartapp.common.health.HealthCheck;
import com.netflix.adminresources.resources.KaryonWebAdminModule;
import com.netflix.hystrix.contrib.rxnetty.metricsstream.HystrixMetricsStreamHandler;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.netflix.karyon.Karyon;
import com.netflix.karyon.KaryonBootstrapSuite;
import com.netflix.karyon.ShutdownModule;
import com.netflix.karyon.archaius.ArchaiusSuite;
import com.netflix.karyon.eureka.KaryonEurekaModule;
import com.netflix.karyon.servo.KaryonServoModule;
import com.netflix.karyon.transport.http.health.HealthCheckEndpoint;

/**
 * @author Nitesh Kant
 */
public class MyApplicationRunner {

    public static void main(String[] args) {
        HealthCheck healthCheckHandler = new HealthCheck();
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            Karyon.forRequestHandler(8888,
                    new HystrixMetricsStreamHandler(
                            new RxNettyHandler("/healthcheck", new HealthCheckEndpoint(new HealthCheck()))
                    )
                    , new KaryonBootstrapSuite(healthCheckHandler),
                    new ArchaiusSuite("cart-app"),
                    KaryonEurekaModule.asSuite(), /* Uncomment if you need eureka */
                    KaryonWebAdminModule.asSuite(),
                    ShutdownModule.asSuite(),
                    KaryonServoModule.asSuite())
                    .startAndWaitTillShutdown();
        } finally {
            context.shutdown();
        }
    }
}

