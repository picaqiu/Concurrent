package com.im.concurrent.service.rocketmq;

public interface  Subject {
   void registryObservers(Observer o);

   void removeObservers(Observer o);

   void notifyObservers();

   void setData(float temperature,float humidity,float pressure);
}
