package dk.clanie.eodhd;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import dk.clanie.web.WebClientFactory;

@AutoConfiguration
@ConditionalOnClass({EodhdClient.class, WebClientFactory.class})
@ConditionalOnProperty(prefix = "eodhd", name = {"url", "apiToken"})
public class EodhdAutoConfiguration {


	@Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(WebClientFactory.class)
    EodhdClient eodhdClient(WebClientFactory webClientFactory) {
        return new EodhdClient(webClientFactory);
    }


}