package com.example.onlineartstore;

import com.example.onlineartstore.controller.ParticipateAuctionController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackageClasses=ParticipateAuctionController.class)
public class OnlineArtStoreApplication {

    public static void main(String[] args) {

        SpringApplication.run(OnlineArtStoreApplication.class, args);
    }

}
