package com.glch.study.study05.test.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**注意：com.fasterxml.jackson.annotation.JsonProperty 别导错包了
 * @author zzl
 * @Date 2022/7/3
 * @description
 */
@Data
public class Stu implements Serializable {
    @JsonProperty("ID")
//    @JSONField(name = "ID")
    private String ID;
    @JsonProperty("NAME")
//    @JSONField(name = "NAME")
    private String NAME;


}
