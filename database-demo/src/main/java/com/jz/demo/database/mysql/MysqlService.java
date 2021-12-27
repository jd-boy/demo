package com.jz.demo.database.mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

}
