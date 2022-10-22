package org.jd.demo.database.event;

import org.jd.demo.database.dao.TestDao;
import org.jd.demo.database.po.PeoplePo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @Auther jd
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateEventListener {

  private final TestDao testDao;

  @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
  public void onApplicationEvent(UpdateEvent event) {
    PeoplePo po = (PeoplePo) event.getSource();
    log.info("接收到事件：{}", po);
    log.info("事件接收线程：{}", Thread.currentThread().getName());
    testDao.updateNameById(po.getName() + 1, po.getId());
    if (true) {
      throw new RuntimeException("模拟异常");
    }
  }

}