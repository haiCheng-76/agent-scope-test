package website.longhc;


import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

/**
 * @author haicheng.long@gokinsolar.com
 * @Classname Test
 * @Description
 * @Date 2026/10/20 9:03
 */
@Slf4j
public class Test {


    @org.junit.Test
    public void test() {
        Properties properties = System.getProperties();
        for (Object o : properties.keySet()) {
            log.info("key: {}, value: {}", o.toString(), properties.get(o));
        }
    }
}
