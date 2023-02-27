package com.example.userCRUD.scopes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DoubletoneScope implements Scope {

  private List<Object> objects = new ArrayList<>(2);
  private Long counter = 0L;
  @Override
  public Object get(String name, ObjectFactory<?> objectFactory) {

    if(objects.size() == 2){
      int index = (int) (counter%2);
      log.info("The bean size is already equals to 2, user object on index: " + index);
      counter++;
      return objects.get(index);
    }else{
      Object object = objectFactory.getObject();
      log.info("Creating new object...");
      objects.add(object);
      counter++;
      return object;
    }
  }

  @Override
  public Object remove(String name) {
    if(objects.size()!=0){
      log.info("Removing object...");
      return objects.remove(0);
    }
    log.info("Running remove method, but the object already null");

    return null;
  }

  @Override
  public void registerDestructionCallback(String name, Runnable callback) {

  }

  @Override
  public Object resolveContextualObject(String key) {
    return null;
  }

  @Override
  public String getConversationId() {
    return null;
  }
}
