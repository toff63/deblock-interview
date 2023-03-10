package org.deblock.exercise.stubs;

import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilderFactory;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class DummyWebClientBuilder implements WebClient.Builder {
    @Override
    public WebClient.Builder baseUrl(String baseUrl) {
        return this;
    }

    @Override
    public WebClient.Builder defaultUriVariables(Map<String, ?> defaultUriVariables) {
        return this;
    }

    @Override
    public WebClient.Builder uriBuilderFactory(UriBuilderFactory uriBuilderFactory) {
        return this;
    }

    @Override
    public WebClient.Builder defaultHeader(String header, String... values) {
        return this;
    }

    @Override
    public WebClient.Builder defaultHeaders(Consumer<HttpHeaders> headersConsumer) {
        return this;
    }

    @Override
    public WebClient.Builder defaultCookie(String cookie, String... values) {
        return this;
    }

    @Override
    public WebClient.Builder defaultCookies(Consumer<MultiValueMap<String, String>> cookiesConsumer) {
        return this;
    }

    @Override
    public WebClient.Builder defaultRequest(Consumer<WebClient.RequestHeadersSpec<?>> defaultRequest) {
        return this;
    }

    @Override
    public WebClient.Builder filter(ExchangeFilterFunction filter) {
        return this;
    }

    @Override
    public WebClient.Builder filters(Consumer<List<ExchangeFilterFunction>> filtersConsumer) {
        return this;
    }

    @Override
    public WebClient.Builder clientConnector(ClientHttpConnector connector) {
        return this;
    }

    @Override
    public WebClient.Builder codecs(Consumer<ClientCodecConfigurer> configurer) {
        return this;
    }

    @Override
    public WebClient.Builder exchangeStrategies(ExchangeStrategies strategies) {
        return this;
    }

    @Override
    public WebClient.Builder exchangeStrategies(Consumer<ExchangeStrategies.Builder> configurer) {
        return this;
    }

    @Override
    public WebClient.Builder exchangeFunction(ExchangeFunction exchangeFunction) {
        return this;
    }

    @Override
    public WebClient.Builder apply(Consumer<WebClient.Builder> builderConsumer) {
        return this;
    }

    @Override
    public WebClient.Builder clone() {
        return this;
    }

    @Override
    public WebClient build() {
        return null;
    }
}
