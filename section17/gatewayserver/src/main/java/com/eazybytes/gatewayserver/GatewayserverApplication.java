package com.eazybytes.gatewayserver;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	@Bean
	public RouteLocator eazyBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder){
		return routeLocatorBuilder.routes()
								.route(p -> p
									.path("/eazybank/accounts/**")  //know as predicate or path predicate
									.filters(f -> f.rewritePath("/eazybank/accounts/(?<remaning>.*)","/${remaning}")
											.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
											.circuitBreaker(config -> config.setName("accountsCircuitBreaker") //the name accountsCircuitBreakfer is given by us and can be anything. It is to identify for which api this circuitBreaker is.
																			.setFallbackUri("forward:/contactSupport")) //this will forward the call to /contactSupport api endpoint which we defined in controller of gatewayserver if any issue found by ciructbraker
										)
								// .uri("lb://ACCOUNTS"))
								.uri("http://accounts:8080"))

								.route(p -> p
									.path("/eazybank/loans/**")
									.filters(f -> f.rewritePath("/eazybank/loans/(?<remaning>.*)","/${remaning}")
											.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
											.retry(retryConfig -> retryConfig.setRetries(3) //number of retry
																		.setMethods(HttpMethod.GET) //retyr method should execute only at get method no other method like put post etc
																		.setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true) //.setBackoff(firstBackof value--> wait for 100milisec before initiate 1st retry, maxbackoff -> wait max 1000 milisec, factor to apply on previous backoff value, false --> apply factor on inital backoff  true--> apply factor on previous backoff value)
													)
										)
								// .uri("lb://LOANS"))
								.uri("http://loanss:8090"))

								.route(p -> p
									.path("/eazybank/cards/**")
									.filters(f -> f.rewritePath("/eazybank/cards/(?<remaning>.*)","/${remaning}")
											.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
												//we need to start the redis database or redis container to use below
											.requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter()) //redisReateLimiter we defined the bean below
																				.setKeyResolver(userKeyResolver())) //userKeyResolver we defined the bean below
										)
								// .uri("lb://CARDS")).build();
								.uri("http://cards:9000")).build();
	}

	//change the default timeout of circuitbreaker from 1 sec

	@Bean
	public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer(){
		return factory -> factory.configureDefault(id->new Resilience4JConfigBuilder(id)
								.circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
								.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(180)).build()).build());
	}

	//we need to start the redis database or redis container to use this
	@Bean
	public RedisRateLimiter redisRateLimiter(){
		return new RedisRateLimiter(1,1,1); //(ReplenishRate, BurstCapacity, RequestedTokens)
	}

	@Bean
	KeyResolver userKeyResolver(){
		return exchange -> Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst("user"))
								.defaultIfEmpty("anonymous"); //get header with name user from the request if header not present assign anonymous as user in KeyResolver.
	}
}
