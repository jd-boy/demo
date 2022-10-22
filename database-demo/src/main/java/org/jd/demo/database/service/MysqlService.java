package org.jd.demo.database.service;

import org.jd.demo.database.dao.TestDao;
import org.jd.demo.database.event.UpdateEvent;
import org.jd.demo.database.po.PeoplePo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author JD
 **/
@Service
public class MysqlService {

    private static final Logger log = LoggerFactory.getLogger(MysqlService.class);

    @Autowired
    private TestDao testDao;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Transactional
    public void test() {
        testDao.selectName(1);
        log.info(testDao.selectName(2));
        log.info(testDao.selectName(3));
        log.info(testDao.selectName(4));
        try {
            log.info("开始睡眠");
            Thread.sleep(31000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("睡眠结束");
    }

    @Transactional
    public void testEventTransactional() {
        PeoplePo po = new PeoplePo("张三");
        testDao.insert(po);
        log.info("事件发布线程：{}", Thread.currentThread().getName());
        eventPublisher.publishEvent(new UpdateEvent(po));
    }

}
