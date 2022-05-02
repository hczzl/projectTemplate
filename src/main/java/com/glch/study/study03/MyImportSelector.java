package com.glch.study.study03;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author zzl
 * @Date 2022/5/2
 * @description
 */
public class MyImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        // 可以同时注册多个，传入完整的类路径即可
        return new String[]{"com.glch.study.study03.UserService", "com.glch.study.study03.RoleService"};
    }
}
