# VaccineNotifier

#Steps to run
  clone
  clean install
  update application.yaml as your need
  run VaccineNotifireApplication as java application



#Configure in application

proxy: true                             // set true if you are on Amdocs vpn
ms:
  recall: 60                               // set min it will keep checking every 60 mins in gov web side if any slot available for you
  reminder: 2                           // set this min it will keep you remind that it find you a slot for you
  info:                                        // list of configuration that you can add as many you want
    - age: 18                                // minimum age for vaccination 
      date: 05-05-2021              // current date (it will find all slots after this date)
      pin: 211003                        // your area pin
      vaccine: covaxin                // vaccine name 
    - age: 45                               // another configuration 
      date: 05-05-2021
      pin: 211003
      vaccine: covishield 
