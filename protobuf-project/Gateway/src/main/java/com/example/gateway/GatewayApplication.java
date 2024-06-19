package com.example.gateway;

import com.example.gateway.model.PlanEntity;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyRequestBodyGatewayFilterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@SpringBootApplication
public class GatewayApplication {

	@Bean
	@LoadBalanced
	RestTemplate restTemplate(ProtobufHttpMessageConverter hmc) {
		return new RestTemplate(Arrays.asList(hmc));
	}

	@Bean
	ProtobufHttpMessageConverter protobufHttpMessageConverter() {
		return new ProtobufHttpMessageConverter();
	}


	private final ModifyResponseContentTypeFilter modifyResponseContentTypeFilter;


	@Autowired
    public GatewayApplication(ModifyResponseContentTypeFilter modifyResponseContentTypeFilter) {
        this.modifyResponseContentTypeFilter = modifyResponseContentTypeFilter;
    }

    public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}


	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
		return builder.routes()

				.route("plan-service", r -> r.path("/api/plans/**")
						.filters(f ->
								f.addRequestHeader("Content-Type", "application/x-protobuf")
										//.filter(modifyResponseContentTypeFilter.apply(new ModifyResponseContentTypeFilter.Config()))

						)
						.uri("http://localhost:8080")
				)
				/*.route(r -> r.path("/api/subscriptions/userPlanDetails")
						.filters(f ->
								f.addRequestHeader("Content-Type", "application/x-protobuf")
										.modifyResponseBody(byte[].class, String.class, MediaType.APPLICATION_JSON_VALUE,
												(exchange, bytes) -> {
													if (bytes == null) {
														return Mono.empty();
													}
													PlanEntity.Plan plan = null;
													try {
														plan = PlanEntity.Plan.parseFrom(bytes);
														plan.toByteArray();
													} catch (InvalidProtocolBufferException e) {
														return Mono.error(new RuntimeException("Failed to parse PlanEntity.Plan from byte array", e));
													}
													String json;
													try {
														json = JsonFormat.printer().print(plan);
													} catch (InvalidProtocolBufferException e) {
														return Mono.error(new RuntimeException("Failed to convert PlanEntity.Plan to JSON", e));
													}
													return Mono.just(json);
												}
										)
						)
						.uri("http://localhost:8081")
				)*/
				.route(r -> r.path("/api/subscriptions/**")
						.filters(f ->
								f.addRequestHeader("Content-Type", "application/x-protobuf")
										//.filter(modifyResponseContentTypeFilter.apply(new ModifyResponseContentTypeFilter.Config()))

						)
						.uri("http://localhost:8081")
				)
				.route(r -> r.path("/api/public/**")
						.filters(f ->
								f.addRequestHeader("Content-Type", "application/x-protobuf")
										//.filter(modifyResponseContentTypeFilter.apply(new ModifyResponseContentTypeFilter.Config()))

						)
						.uri("http://localhost:8083")
				)
				.build();
	}


	@Component
	public static class ModifyResponseContentTypeFilter extends AbstractGatewayFilterFactory<ModifyResponseContentTypeFilter.Config> {

		public ModifyResponseContentTypeFilter() {
			super(Config.class);
		}

		@Override
		public GatewayFilter apply(Config config) {
			return (exchange, chain) -> {
				exchange.getRequest().mutate().header(HttpHeaders.ACCEPT, "application/json").build();
				return chain.filter(exchange);
			};
		}

		public static class Config {
		}
	}
}
