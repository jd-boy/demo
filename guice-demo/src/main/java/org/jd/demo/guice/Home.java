package org.jd.demo.guice;

import com.google.inject.Inject;
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
