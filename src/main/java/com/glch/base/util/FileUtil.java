package com.glch.base.util;

import java.io.*;

public class FileUtil {


    public static void createDirsIfNotExist(String filepath) throws Exception {
        File file = new File(filepath);
        String loaclPath = file.getCanonicalPath();
        if(loaclPath.contains("\\")){
            loaclPath = loaclPath.substring(0,loaclPath.lastIndexOf("\\"));
        }
        File filePath = new File(loaclPath);
        if(!filePath.exists()){
            filePath.mkdirs();
        }
    }

    /**
     * 复制文件，不覆盖
     * @param src
     * @param desc
     * @return
     */
    public static boolean copyFile(String src, String desc) {
        File srcFile = new File(src);
        File descFile = new File(desc);
        if(!srcFile.isFile() || desc==null || desc.length()==0 || descFile.exists()) {
            return false;
        }
        try {
            FileUtil.createDirsIfNotExist(desc);
            FileUtil.createDirsIfNotExist(desc);
        } catch (Exception e) {
            e.printStackTrace();
            descFile.delete();
            return false;
        }
        boolean ok = false;
        InputStream ins = null;
        OutputStream os = null;
        try{
            ins = new FileInputStream(srcFile);
            os = new FileOutputStream(descFile);
            byte[] buffer = new byte[1024*1024];
            int byteRead = 0;
            while(byteRead != -1) {
                byteRead = ins.read(buffer);
                if(byteRead>0) {
                    os.write(buffer, 0, byteRead);
                }
            }
            os.flush();
            os.close();
            ok = true;
        }catch (Exception e) {
            e.printStackTrace();
            try {
                if(os!=null){
                    ins.close();
                }
            }catch (Exception e1) {}
            descFile.delete();
        }finally {
            try {
                if(ins!=null){
                    ins.close();
                }
            }catch (Exception e) {}
            try {
                if(os!=null){
                    ins.close();
                }
            }catch (Exception e) {}
        }
        return ok;
    }


}
