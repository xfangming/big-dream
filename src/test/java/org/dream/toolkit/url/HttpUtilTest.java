package org.dream.toolkit.url;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * @author tobiasy
 * @date 2018/12/21
 */
public class HttpUtilTest {

    @Test
    public void doGet() {
        String res = HttpUtil.doGet("http://10.211.81.45:7081/student/list?pageNum=1&pageSize=8",new HashMap<>());
        System.out.println(res);
    }

    @Test
    public void doPost() {
    }
}