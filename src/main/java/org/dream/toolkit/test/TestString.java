package org.dream.toolkit.test;

import org.dream.toolkit.common.StringUtils;

public class TestString {
    public static void main(String[] args) {
        System.out.println(StringUtils.createRandom(1));
        System.out.println(StringUtils.camelcase("stuid"));
        System.out.println(StringUtils.camelcase("stuid1"));
    }
}
