package com.jz.demo.guice;

import com.google.inject.Inject;
import javax.inject.Scope;
import lombok.Getter;

/**
 * @Auther jd
 */
@Getter
public class Home {

  @Inject
  private People people;

  @Inject
  private Animal animal;

//  @Inject
//  public Home(People people, Animal animal) {
//    this.people = people;
//    this.animal = animal;
//  }

  public void print() {
    people.say();
    animal.eat();
  }

}
