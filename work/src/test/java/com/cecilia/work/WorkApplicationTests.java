package com.cecilia.work;

import com.cecilia.work.pojo.Position;
import com.cecilia.work.pojo.User;
import com.cecilia.work.service.PositionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
class WorkApplicationTests {
    @Autowired
PositionService positionService;
    @Test
    void contextLoads() {

    }

}
