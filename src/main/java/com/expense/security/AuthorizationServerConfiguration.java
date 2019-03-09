package com.expense.security;

import com.expense.service.UserSecurityService;
import com.expense.setting.EnvironmentVariables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;

    private final UserSecurityService userSecurityService;

    private final PasswordEncoder passwordEncoder;

    private final EnvironmentVariables env;

    private TokenStore tokenStore = new InMemoryTokenStore();

    @Autowired
    public AuthorizationServerConfiguration(
            @Qualifier("authenticationManagerBean") AuthenticationManager authenticationManager,
            UserSecurityService userSecurityService,
            PasswordEncoder passwordEncoder,
            EnvironmentVariables env) {
        this.authenticationManager = authenticationManager;
        this.userSecurityService = userSecurityService;
        this.passwordEncoder = passwordEncoder;
        this.env = env;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpointsConfig) {
        endpointsConfig
                .tokenStore(this.tokenStore)
                .authenticationManager(this.authenticationManager)
                .userDetailsService(this.userSecurityService);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clientsConfig) throws Exception {
        clientsConfig
                .inMemory()
                .withClient(this.env.getClient())
                .authorizedGrantTypes(this.env.getGrants())
                .scopes(this.env.getScopes())
                .refreshTokenValiditySeconds(604800) // Valid for one week
                .accessTokenValiditySeconds(86400) // Valid in the next 24 hours
                .resourceIds(this.env.getResource())
                .secret(this.passwordEncoder.encode(this.env.getSecret()));
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenStore(this.tokenStore);
        return tokenServices;
    }

}
