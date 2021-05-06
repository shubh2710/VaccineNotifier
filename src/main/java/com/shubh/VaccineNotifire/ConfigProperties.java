package com.shubh.VaccineNotifire;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "ms")
@Component
public class ConfigProperties {

    List<Information> info=new ArrayList<>();

    public List<Information> getInfo() {
        return info;
    }

    public void setInfo(List<Information> info) {
        this.info = info;
    }
}
