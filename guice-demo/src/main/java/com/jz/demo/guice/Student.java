package com.jz.demo.guice;

import com.google.inject.Singleton;

/**
 * @Auther jd
 */
@Singleton
public class Student implements People {

  @Override
  public void say() {
    System.out.println("我是学生");
  }

}
