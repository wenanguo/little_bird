package com.cmtt.base;

import com.alibaba.fastjson.JSON;
import com.cmtt.base.entity.HR;
import com.cmtt.base.utils.HttpUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

//@RunWith(SpringRunner.class)
////启动Spring
//@SpringBootTest
public class HelloControllerTest {



    @Test
    public void getSendsms() throws Exception {

        String url="http://www.teamyy.cn:18087/api/sys_user/sms";
        //String url="http://127.0.0.1:8080/api/sys_user/sms";
        Map<String, Object> map = new HashMap<>();
        map.put("phone", "15285027249");
        map.put("type", "1");

        String req = JSON.toJSONString(map);


        HR hr = HttpUtils.doPost(url, req, null);


        System.out.println(hr);


    }

//    @Autowired
//    private ApiController apiController;

//    @Test
//    public void getHello() throws Exception {
//        // 正确密码
//        SysUser sysUser=new SysUser();
//        sysUser.setUsername("wenanguo");
//        sysUser.setPassword("ffd0a949f2bdafb98ab04aee7e3102dd");
//        final HttpResp httpResp = apiController.login(sysUser);
//        Assert.assertEquals(200L, httpResp.getCode().longValue());
//
//
//        // 错误密码
//        SysUser sysUser2=new SysUser();
//        sysUser2.setUsername("wenanguo");
//        sysUser2.setPassword("错误密码");
//        final HttpResp httpResp2 = apiController.login(sysUser2);
//        Assert.assertEquals(201L, httpResp2.getCode().longValue());
//
//    }


}