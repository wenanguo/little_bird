package com.cmtt.base;

import com.alibaba.fastjson.JSON;
import com.cmtt.base.entity.HR;
import com.cmtt.base.service.impl.SysRoleServiceImpl;
import com.cmtt.base.utils.HttpclientUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
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


}