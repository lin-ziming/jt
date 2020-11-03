package com.jt.service;

import com.jt.vo.EasyUITable;

public interface ItemService {

    EasyUITable findItemByPage(Integer page, Integer rows);

    EasyUITable findItemByPage2(Integer page, Integer rows);
}
