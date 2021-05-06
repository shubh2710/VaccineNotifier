package com.shubh.VaccineNotifire;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.lang.model.element.NestingKind;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotifierService {

    @Autowired
    RestTemplate template;


    @Autowired
    ConfigProperties properties;
    boolean isNotify=false;

    @Autowired
    ResourceLoader resourceLoader;

    Set<String> reminderData=new HashSet<>();


    boolean isFirstRun=true;
    boolean isSlotAvailable=false;
    public void call() throws AWTException, InterruptedException, IOException {

        Set<AreaInfo> pins=properties.info.stream().map(v->{
            return new AreaInfo(v.pin,v.date); }).collect(Collectors.toSet());

        Map<AreaInfo,JsonNode> responseDataList=new HashMap<>();
        for(AreaInfo pin:pins){
            Thread.sleep(1000);
            responseDataList.put(pin,callApi(pin));
        }
        for(Information info:properties.info){
            getAndMatchData(responseDataList.get(new AreaInfo(info.pin,info.date)),info);
        }
        if(isFirstRun && !isSlotAvailable){
            isFirstRun=false;
            displayTray("No slots available at this time!");
            System.out.println("No slots available at this time!");
        }
    }

    private JsonNode callApi(AreaInfo pin) {
        String url="https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByPin?pincode="+pin.getPin()+"&date="+pin.getDate();
        //String url="http://localhost:9191/vaccine";

        HttpHeaders headers = new HttpHeaders();
        headers.set("accept-language", "<string>");
        headers.set("user-agent", " Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36");
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<JsonNode> res=template.exchange(url, HttpMethod.GET, entity, JsonNode.class);
        return res.getBody();
    }

    private void getAndMatchData(JsonNode jsonProperty, Information property) throws AWTException, IOException {

        ArrayNode centers= (ArrayNode) jsonProperty.get("centers");

        isNotify=false;

        for(JsonNode center:centers){
            String centerName=center.get("name").asText();
            ArrayNode sessions= (ArrayNode)   center.get("sessions");
            for(JsonNode session:sessions){
                int age = session.get("min_age_limit").asInt();
                String vaccine=session.get("vaccine").asText();
                int available_capacity = session.get("available_capacity").asInt();

                boolean isAvailable=false;
                if(available_capacity>0){
                    ArrayNode slots= (ArrayNode) session.get("slots");
                    if(slots.size()>0){
                        isAvailable=true;
                    }
                }
                String vaccineData=property.getVaccine();
                if(vaccine.equalsIgnoreCase(vaccineData) && isAvailable){
                    if(property.getAge()>=age){
                        isSlotAvailable=true;
                        System.out.println("Availability: for age "+ age );
                        System.out.println("center: "+ centerName );
                        System.out.println("isAvailable: "+ isAvailable );
                        System.out.println("vaccine name: "+ vaccine );
                        System.out.println("-------------------------");
                        displayTray("Availability: for age "+ age +"+ \n"+ "Center: "+ centerName +"\n"+ "Vaccine name: "+ vaccine);
                        reminderData.add("Availability: for age "+ age +"+ \n"+ "Center: "+ centerName +"\n"+ "Vaccine name: "+ vaccine);
                    }
                }

            }
        }
    }

    public void displayTray(String msg) throws AWTException, IOException {
        if(!isNotify){
            notification(msg);
            isNotify=true;
        }
    }

    private void notification(String msg) throws AWTException, IOException {
        SystemTray tray = SystemTray.getSystemTray();
        if (SystemTray.isSupported()) {

            Resource resource = resourceLoader.getResource("classpath:download.png");

            Image image = Toolkit.getDefaultToolkit().createImage(resource.getURL());

            TrayIcon trayIcon = new TrayIcon(image, "Vaccine Notification");

            trayIcon.setImageAutoSize(true);


            tray.add(trayIcon);

            trayIcon.displayMessage("Vaccination Notification", msg, TrayIcon.MessageType.NONE);

            tray.remove(trayIcon);

        } else {
            System.err.println("System tray not supported!");
        }
    }

    public void reminder() throws IOException, AWTException, InterruptedException {
        for(String reminders:reminderData){
            Thread.sleep(1000);
            notification(reminders);
        }
    }
}
