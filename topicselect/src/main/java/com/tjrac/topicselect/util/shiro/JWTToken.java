package com.tjrac.topicselect.util.shiro;

import org.apache.shiro.authc.HostAuthenticationToken;

/**
 * @author myd
 */
public class JWTToken implements HostAuthenticationToken {
	private static final long serialVersionUID = 6532728254200706096L;
	
	private String token;
    private String host;

    public JWTToken(String token) {
        this(token, null);
    }

    public JWTToken(String token, String host) {
        this.token = token;
        this.host = host;
    }

    public String getToken(){
        return this.token;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public String toString(){
        return token + ':' + host;
    }
}
