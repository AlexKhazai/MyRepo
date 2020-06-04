package com.launchdarkly.crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.launchdarkly.sdk.LDUser;
import com.launchdarkly.sdk.LDValue;
import com.launchdarkly.sdk.server.LDClient;

import java.io.IOException;
import java.util.Arrays;

@SpringBootApplication 
@EnableJpaRepositories(basePackages="com.launchdarkly.crud.repositories")
@EnableTransactionManagement
@EntityScan(basePackages="com.launchdarkly.crud.entities")
public class Application {

    public static void main(String[] args) throws IOException {

        SpringApplication.run(Application.class, args);

        // Set SDK_KEY to your LaunchDarkly SDK key before compiling
        //final String SDK_KEY = "sdk-f3a63309-e366-4967-b62f-dad4fd70d0df";

        // Set FEATURE_FLAG_KEY to the feature flag key you want to evaluate
        //final String FEATURE_FLAG_KEY = "new-edittab"; or another

 

          //LDClient client = new LDClient(SDK_KEY);

          // Set up the user properties. This user should appear on your LaunchDarkly users dashboard
          // soon after you run the demo.
          /*LDUser user = new LDUser.Builder("alexkhazai82@gmail.com")
                                  .firstName("Alex")
                                  .lastName("Khazai")
                                  .custom("groups", LDValue.buildArray().add("beta_testers").build())
                                  .build();

          boolean showFeature = client.boolVariation(FEATURE_FLAG_KEY, user, false);

          System.out.println("Feature flag '" + FEATURE_FLAG_KEY + "' is " + showFeature + " for this user");
*/
          // Calling client.close() ensures that the SDK shuts down cleanly before the program exits.
          // Unless you do this, the SDK may not have a chance to deliver analytics events to LaunchDarkly,
          // so the user properties and the flag usage statistics may not appear on your dashboard. In a
          // normal long-running application, events would be delivered automatically in the background
          // and you would not need to close the client.
  //        client.close();

    
    
    
    }
    
}
