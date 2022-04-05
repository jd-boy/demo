package com.jz.demo.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.jz.demo.guice.core.AutoModuleScanner;

/**
 * @Auther jd
 */
public class GuiceMain {

  public static void main(String[] args) {
    AutoModuleScanner.init();
    Injector injector = AutoModuleScanner.injector();
    Home home1 = injector.getInstance(Home.class);
    Home home2 = injector.getInstance(Home.class);
    System.out.println(home1.getAnimal() == home2.getAnimal());
    System.out.println(home1.getPeople() == home2.getPeople());
  }

}
