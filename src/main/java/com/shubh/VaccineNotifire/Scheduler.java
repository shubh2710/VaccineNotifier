package com.shubh.VaccineNotifire;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class Scheduler {

@Autowired
NotifierService service;

    @Scheduled(fixedDelayString = "${ms.recall}0000", initialDelay = 1000)
    public void run() throws AWTException, InterruptedException, IOException {
        System.out.println("calling api");
        service.call();
    }

    @Scheduled(fixedDelayString = "${ms.reminder}0000", initialDelay = 1000)
    public void reminder() throws AWTException, InterruptedException, IOException {
        System.out.println("calling reminder");
        service.reminder();
    }


}
