package com.cmtt.base;

import com.cmtt.base.entity.SysRole;
import com.cmtt.base.entity.SysUser;
import com.cmtt.base.service.impl.SysRoleServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleTest {

    @Autowired
    SysRoleServiceImpl sysRoleService;

    @Test
    public void testSelect() {


        List<SysRole> userRole = sysRoleService.getUserRole(new SysUser().setId(2));
        userRole.forEach(System.out::println);
    }


}