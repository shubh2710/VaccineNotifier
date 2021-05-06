package com.shubh.VaccineNotifire;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.annotation.PostConstruct;
import javax.swing.ImageIcon;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;


public class MyTrayIcon extends TrayIcon {

    private static final String IMAGE_PATH = "C:\\Users\\shubgula\\Desktop\\myprojects\\VaccineNotifire\\VaccineNotifire\\src\\main\\resources\\download.png";
    private static final String TOOLTIP = "Running";
    private PopupMenu popup;
    final SystemTray tray;

    public MyTrayIcon(){
        super(createImage(IMAGE_PATH,TOOLTIP),TOOLTIP);
        popup = new PopupMenu();
        tray = SystemTray.getSystemTray();
        try {
            setup();
        } catch (AWTException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



    @PostConstruct
    public void setup() throws AWTException{
        // Create a pop-up menu components
        MenuItem exitItem = new MenuItem("Exit");
        popup.add(exitItem);
        exitItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                final int exitCode = 0;
                ExitCodeGenerator exitCodeGenerator = new ExitCodeGenerator() {

                    @Override
                    public int getExitCode() {
                      return exitCode;
                    }

                  };

                  tray.remove(MyTrayIcon.this);
                  SpringApplication.exit(VaccineNotifireApplication.context, exitCodeGenerator);
            }
        });
        // popup.addSeparator();
        setPopupMenu(popup);
        tray.add(this);



    }

    protected static Image createImage(String path, String description){

        if(path==null)
            return null;

        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        return image;
       /* URL imageURL = System.class.getResource(path);
        if(imageURL == null){
            System.err.println("Failed Creating Image. Resource not found: "+path);
            return null;
        }else {
            return new ImageIcon(imageURL,description).getImage();
        }*/
    }
}