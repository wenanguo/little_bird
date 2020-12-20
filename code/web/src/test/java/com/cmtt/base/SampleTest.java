package com.cmtt.base;

import com.cmtt.base.entity.HR;
import com.cmtt.base.service.impl.SysRoleServiceImpl;
import com.cmtt.base.utils.DateTimeUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class SampleTest {

    @Autowired
    SysRoleServiceImpl sysRoleService;

//    @Test
//    public void testSelect() throws IOException {
//
//        // String url="http://www.httpbin.org/anything";
//        String url="https://buy.itunes.apple.com/verifyReceipt";
//
//        Map<String,Object> map=new HashMap<>();
//        map.put("receipt-data","aaa");
//        map.put("password","aaa");
//        map.put("exclude-old-transactions",false);
//
//
//        String a=JSON.toJSONString(map);
//
//
//
//        HR hr = HttpclientUtils.doPost(url,a,null);
//
//
//        System.out.println(hr);
//
//    }

    @Test
    public void testSelect() throws IOException {
        LocalDateTime localDateTime1 = LocalDateTime.of(2020, Month.DECEMBER, 18, 10, 33, 56);
        String socialDateDisplay = DateTimeUtils.getSocialDateDisplay(localDateTime1);
        System.out.println(socialDateDisplay);

    }
}