package com.cmtt.base;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
//启动Spring
@SpringBootTest
public class HelloControllerTest {

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