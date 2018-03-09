package com.aukletapm.go.sample.springboot;

import com.aukletapm.go.AukletGoServer;
import com.aukletapm.go.component.NavigationList;
import com.aukletapm.go.component.PhysicalMemoryChart;
import com.aukletapm.go.component.SpaceDoughnutChart;
import com.aukletapm.go.metrics.component.ProcessCpuLoadLineChart;
import com.aukletapm.go.metrics.component.SystemCpuLoadLineChart;
import com.aukletapm.go.metrics.component.TimersComponent;
import com.aukletapm.go.servlet.AukletGoHttpServletHandler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Eric Xu
 * @date 01/03/2018
 */
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan
public class Application implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {

    }

    @Bean
    public AukletGoServer aukletGoServer(PublicMethodAspect publicMethodAspect) {

        NavigationList menu = new NavigationList("menu", "Main Menu");

        AukletGoServer.Builder builder = new AukletGoServer.Builder().serverName("Spring Boot Sample")
                .addComponent(menu)
                .addComponent(new SystemCpuLoadLineChart())
                .addComponent(new ProcessCpuLoadLineChart())
                .addComponent(new PhysicalMemoryChart())
                .addComponent(new SpaceDoughnutChart());
        AukletGoServer server = builder.build();
        menu.add(new TimersComponent("timers", "Timers", publicMethodAspect.getMetricRegistry()));

        return server;
    }

    @Bean
    public AukletGoHttpServletHandler aukletGoHttpServletHandler(AukletGoServer aukletGoServer) {
        return new AukletGoHttpServletHandler.Builder().debug().server(aukletGoServer).build();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

