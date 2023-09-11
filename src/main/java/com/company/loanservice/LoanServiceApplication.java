package com.company.loanservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
//@EnableDiscoveryClient
@OpenAPIDefinition(
        info = @Info(
                title = "BankScheme Microservice",
                description = "This project for BankScheme Microservice",
                version = "2023.10.08",
                contact = @Contact(
                        name = "Farrux Izzatullayev",
                        url = "http://localhost:8080/swagger-ui/index.html#/product-controller",
                        email = "farruxizzatullayev07@gmail.com"
                ),
                license = @License(
                        name = "License name",
                        url = "http://localhost:8080/swagger-ui/index.html#/product-controller"
                )
        ),
        tags = {@Tag(
                name = "BankSchemeMVS Create! ",
                description = "Tag description Create"
        ),
                @Tag(
                        name = "BanSchemeNVS Get! ",
                        description = "Tag description Get"
                ),
                @Tag(
                        name = "BankScheme Update! ",
                        description = "Tag description Update"
                ),
                @Tag(
                        name = "BankScheme Delete! ",
                        description = "Tag description Delete"
                ),
                @Tag(
                        name = "BankScheme GetAll! ",
                        description = "Tag description GetAll"
                ),
                @Tag(
                        name = "BankScheme GetSearch",
                        description = "Tag description GetSearch!"
                )
        }
)
public class LoanServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoanServiceApplication.class, args);
    }

}
