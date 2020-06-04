package com.launchdarkly.crud.services;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import com.launchdarkly.sdk.LDUser;
import com.launchdarkly.sdk.LDValue;
import com.launchdarkly.sdk.server.LDClient;

@Component
public class LaunchDarklyService {
	//private final String SDK_KEY = "sdk-f3a63309-e366-4967-b62f-dad4fd70d0df";
	private final String SDK_KEY = "sdk-04799a0e-ecb8-4411-8cba-94a61f79f0e8";
	private String FEATURE_FLAG_KEY;
    private LDClient client = new LDClient(SDK_KEY);
    private LDUser ldUser;
	
	public String getFEATURE_FLAG_KEY() {
		return FEATURE_FLAG_KEY;
	}
	
	public void setFEATURE_FLAG_KEY(String fEATURE_FLAG_KEY) {
		FEATURE_FLAG_KEY = fEATURE_FLAG_KEY;
	}
	
	public String getSDK_KEY() {
		return SDK_KEY;
	}
	
	@PostConstruct
	private void init() {
		this.ldUser = new LDUser.Builder("alexkhazai82@gmail.com")
				.firstName("Alex")
				.lastName("Khazai")
				.custom("groups", LDValue.buildArray().add("beta_testers").build()).build();
		
	}
	
    @PreDestroy
	private void shutDown() throws IOException {
		this.client.close();
	}
    
	public Boolean FlagStatus() throws IOException {
		return client.boolVariation(FEATURE_FLAG_KEY, ldUser, false);

	}
	
}
