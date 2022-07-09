package com.jz.demo.database.event;

import com.jz.demo.database.po.PeoplePo;
import org.springframework.context.ApplicationEvent;

/**
 * @Auther jd
 */
public class UpdateEvent extends ApplicationEvent {

  public UpdateEvent(PeoplePo people) {
    super(people);
  }

}