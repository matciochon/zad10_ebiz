package org.grupa2.programowaniezespoloweprojekt.config;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OAuthConfig {

    @Value("${usos.consumer.key}")
    private String consumerKey;

    @Value("${usos.consumer.secret}")
    private String consumerSecret;

    public OAuthConsumer getOAuthConsumer() {
        return new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
    }
}
