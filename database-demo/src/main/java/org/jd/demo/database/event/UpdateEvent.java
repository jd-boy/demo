package org.jd.demo.database.event;

import org.jd.demo.database.po.PeoplePo;
import org.springframework.context.ApplicationEvent;

/**
 * @Auther jd
 */
public class UpdateEvent extends ApplicationEvent {

  public UpdateEvent(PeoplePo people) {
    super(people);
  }

}