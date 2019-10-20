package com.ze.dropwizard;

import com.google.common.io.Resources;
import com.ze.dropwizard.comand.HelloComand;
import com.ze.dropwizard.filter.DateNotSpecifiedFilter;
import com.ze.dropwizard.filter.Dyfilter;
import com.ze.hk2.AutoConfigBundleBuider;
import com.ze.hk2.IOCApplication;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

import java.net.URL;

/**
 * Created by ZE-C on 2017/11/19.
 */
public class DropwizardIOCApp extends IOCApplication<AppConfig> {

    public static void main(String[] args) throws Exception {
        System.out.println();
        URL config =  Resources.getResource("config.yml");
        new DropwizardIOCApp().run("server", config.getPath());

    }

    @Override
    public void run(AppConfig configuration, Environment environment) throws Exception {
        environment.jersey().register(new Dyfilter());
        environment.jersey().register(new DateNotSpecifiedFilter());
        environment.jersey().register(configuration);
    }


    @Override
    public void initialize(Bootstrap<AppConfig> bootstrap) {
        // nothing to do yet
        bootstrap.addBundle(new SwaggerBundle<AppConfig>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(AppConfig configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
        bootstrap.addBundle(new AutoConfigBundleBuider().addPackageName("com.ze").build());
        bootstrap.addCommand(new HelloComand());
    }
}
