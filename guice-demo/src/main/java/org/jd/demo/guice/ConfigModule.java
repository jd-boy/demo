package org.jd.demo.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;

/**
 * @Auther jd
 */
public class ConfigModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(Animal.class).to(Dog.class).in(Scopes.SINGLETON);
//    bind(People.class).to(Student.class);
  }

  @Provides
  public People peopleProvider() {
    return new Student();
  }

}
