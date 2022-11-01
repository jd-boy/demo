package org.jd.demo.maxwell.handler;

import org.jd.demo.maxwell.message.MaxwellMessage;

/**
 * @Auther jd
 */
public interface MessageHandler {

  void handler(MaxwellMessage message);

}
