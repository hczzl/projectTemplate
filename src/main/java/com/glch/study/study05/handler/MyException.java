package com.glch.study.study05.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义运行时异常
 *
 * @author zzl
 * @Date 2022/7/3
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyException extends RuntimeException {
    private int code;
    private String msg;
}
