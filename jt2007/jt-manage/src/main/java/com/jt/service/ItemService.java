package com.jt.service;

import com.jt.pojo.Item;
import com.jt.vo.EasyUITable;

public interface ItemService {

    EasyUITable findItemByPage(Integer page, Integer rows);

    EasyUITable findItemByPage2(Integer page, Integer rows);

    void saveItem(Item item);

    void updateItem(Item item);

    void deleteItems(Long[] ids);

    void updateStatus(Long[] ids, Integer status);

    void updateStatus2(Long[] ids, Integer status);
}
