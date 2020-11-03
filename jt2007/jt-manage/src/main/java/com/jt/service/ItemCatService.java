package com.jt.service;


import com.jt.vo.EasyUITree;

import java.util.List;

public interface ItemCatService {

    String findItemCatById(Long itemCatId);

    List<EasyUITree> findItemCatList(Long parentId);
}
