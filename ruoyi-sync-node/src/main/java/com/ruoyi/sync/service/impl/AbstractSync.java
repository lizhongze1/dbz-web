package com.ruoyi.sync.service.impl;

import java.io.IOException;

/**
 * 　  * @className: AbstractSync
 * 　　* @description:TODO
 * 　　* @param:
 * 　　* @return:
 * 　　* @throws:
 * 　　* @email lzz215855518@gmail.com
 * 　　* @author: lizz
 * 　　* @date: 2020/12/05 14:04
 */
public abstract class AbstractSync {




    public boolean start() throws IOException {
        return false;
    }
    public  boolean stop(){
        return false;
    }
}
