package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class EasyUITree implements Serializable {
    private static final long serialVersionUID = -6430564299899005614L;
    private Long id;        //节点ID
    private String text;    //节点名称
    private String state;   //节点状态
}
