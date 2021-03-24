package com.glch.base.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

@Configuration
public class FtpUtil {
    @Autowired
    private Environment environment;
    //存储根目录
    public static final String DEFAULT_PATH = "/FTPFolder";
    //路径分隔符
    public static final String PATH_SPILTE = "/";
    //ftp访问IP
    private static String ftpIp;
    //ftp访问端口号
    private static Integer ftpPort;
    //ftp访问用户
    private static String userName;
    //ftp登录密码
    private static String pwd;
    //本地服务器路径
    public static String localDefaultPath;
    private static FTPClient ftpClient = null;

    public static String getFtpIp() {
        return ftpIp;
    }
    @Value("${ftp.ip}")
    public void setFtpIp(String ftpIp) {
        FtpUtil.ftpIp = ftpIp;
    }

    public static Integer getFtpPort() {
        return ftpPort;
    }
    @Value("${ftp.port}")
    public void setFtpPort(Integer ftpPort) {
        FtpUtil.ftpPort = ftpPort;
    }

    public static String getUserName() {
        return userName;
    }
    @Value("${ftp.userName}")
    public void setUserName(String userName) {
        FtpUtil.userName = userName;
    }

    public static String getPwd() {
        return pwd;
    }
    @Value("${ftp.password}")
    public void setPwd(String pwd) {
        FtpUtil.pwd = pwd;
    }

    public static String getLocalDefaultPath() {
        return localDefaultPath;
    }
    @Value("${ftp.localDiskTmpPath}")
    public void setLocalDefaultPath(String localDefaultPath) {
        FtpUtil.localDefaultPath = localDefaultPath;
    }

    /**
     * 连接FTP
     */
    public static void connect() {
        try {
            if(ftpClient!=null) {
                ftpClient.disconnect();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            ftpClient = connect(ftpIp,ftpPort,userName,pwd,null);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void checkConnect() {
        if(null == ftpClient){
            connect();
        }
        try {
            String str = ftpClient.getStatus();
            System.out.println(str);
        } catch (Exception e) {
            connect();
            e.printStackTrace();
        }
    }

    public static FTPClient connect(String ftpIp, Integer ftpPort, String userName, String pwd, String charset){
        FTPClient ftpClient = null;
        try {
            ftpClient = new FTPClient();
            //设置字符编码,默认utf-8
            if(StringUtil.isEmpty(charset)){
                charset = "utf-8";
            }
            ftpClient.setCharset(Charset.forName(charset));
            //连接ftp服务器
            ftpClient.connect(ftpIp,ftpPort);
            //登录ftp
            ftpClient.login(userName,pwd);
            //设置文件类型
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return ftpClient;
    }

    /**
     * 断开FTP
     */
    public static void close(){
        try {
            if(null != ftpClient){
                ftpClient.disconnect();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static boolean upload(String remotePath, String remoteFileName,String base64){
        boolean flag;
        if(StringUtil.isEmpty(base64)){
            return false;
        }
        try {
            InputStream in = DataTransUtil.base64ToInputStream(base64);
            if(null != in){
                flag = upload(remotePath, remoteFileName,in);
            }else{
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return flag;
    }
    /**
     * 上传文件（输入流）
     * @param remotePath ftp目录路径
     * @param remoteFileName 文件名
     * @param in 输入流
     * @return
     */
    public static boolean upload(String remotePath, String remoteFileName,InputStream in){
        boolean flag;
        remotePath = DEFAULT_PATH + remotePath;
        try {
            if(null == ftpClient){
                connect();
            }
            // 将工作空间切换到根目录
            ftpClient.changeWorkingDirectory("/");
            if(remotePath != null){
                boolean exist = isExist(DEFAULT_PATH);
                String path = "";
                if(exist){
                    // 根目录存在，切换工作目录
                    ftpClient.changeWorkingDirectory(DEFAULT_PATH);
                    path += DEFAULT_PATH;
                    String[] directories = remotePath.substring(remotePath.indexOf(DEFAULT_PATH)+DEFAULT_PATH.length(),remotePath.length()).split(PATH_SPILTE);
                    //根据路径切换工作目录，如果目录不存在，则生成目录
                    for(int i=0;i<directories.length;i++){
                        if(!StringUtil.isEmpty(directories[i])){
                            path += directories[i];
                            boolean dirExist = isExist(path);
                            if(dirExist){
                                ftpClient.changeWorkingDirectory(remotePath);
                            }else{
                                ftpClient.makeDirectory(directories[i]);
                            }
                            path += PATH_SPILTE;
                        }
                    }
                }else{//根目录不存在，则根据路径生成目录
                    String[] directories = remotePath.split(PATH_SPILTE);
                    for(String dir:directories){
                        if(!StringUtil.isEmpty(dir)){
                            path += dir;
                            ftpClient.makeDirectory(path);
                            path += PATH_SPILTE;
                        }
                    }
                }
            }
            String remote = new String((remotePath + PATH_SPILTE + remoteFileName).getBytes("GBK"),"iso-8859-1");
            flag = ftpClient.storeFile(remote,in);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally{
            try {
                if(null != in){
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    /**
     * 上传
     * @param remotePath
     * @param remoteFileName
     * @param o
     * @return
     */
    public static boolean upload(String remotePath, String remoteFileName, Object o){
        boolean flag;
        try {
            if(null == o){
                return false;
            }
            String jsonStr = o.toString();
            byte[] b = jsonStr.getBytes();
            flag = upload(remotePath, remoteFileName,b);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return flag;
    }

    /**
     * 上传(字节数组)
     * @param remotePath
     * @param remoteFileName
     * @param bytes
     * @return
     */
    public static boolean upload(String remotePath, String remoteFileName,byte[] bytes){
        InputStream in;
        boolean flag;
        try {
            in = DataTransUtil.byte2InputStream(bytes);
            if(null == in){
                return false;
            }
            flag = upload(remotePath, remoteFileName,in);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return flag;
    };
    /**
     * 判断文件夹是否存在
     * @param path
     * @return
     */
    public static boolean isExist(String path){
        boolean flag = false;
        if(null == ftpClient){
            connect();
        }
        try {
            if(path.endsWith(PATH_SPILTE)){
                path = path.substring(0,path.length()-1);
            }
            //获取路径最后一级目录名
            String directory = path.substring(path.lastIndexOf(PATH_SPILTE)+1,path.length());
            //获取父目录路径
            String parentPath = path.substring(0,path.lastIndexOf(PATH_SPILTE));
            //获取指定路径下的目录列表
            FTPFile[] ftpFiles = ftpClient.listDirectories(parentPath);
            for(FTPFile ftpFile : ftpFiles ){
                if(directory.equals(ftpFile.getName())){
                    flag = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 下载文件（输入流）
     * @param remotePath
     * @param remoteFileName
     * @return
     */
    public static InputStream downloadToStream(String remotePath, String remoteFileName){
        InputStream in = null;
        try {
            if(null == ftpClient){
                connect();
            }
            remotePath = new String((DEFAULT_PATH + remotePath + remoteFileName).getBytes("GBK"),"iso-8859-1");
            in = ftpClient.retrieveFileStream(remotePath);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return in;
    }

    public static String downloadToBase64(String remotePath, String remoteFileName){
        String base64 = "";
        try {
            InputStream in = downloadToStream(remotePath,remoteFileName);
            if(null != in){
                base64 = DataTransUtil.stream2Basr64(in);
            }
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
        return base64;
    }

    public static Boolean downloadToLocalDisk(String remoteFilePath, String localFilePath) throws Exception {
        FileOutputStream fos = null;
        Boolean ok = null;
        File file = new File(localFilePath);
        try {
            checkConnect();

            FileUtil.createDirsIfNotExist(localFilePath);
            fos = new FileOutputStream(file);
            remoteFilePath = new String((DEFAULT_PATH + "/" +remoteFilePath).getBytes("GBK"),"iso-8859-1");
            ok  = ftpClient.retrieveFile(remoteFilePath, fos);
            if(ok==false || file.length()==0){
                file.delete();
            }

        }catch (Exception e){
            try{
                if(fos != null){
                    fos.close();
                }
            }catch(Exception e1){}
            file.delete();
            e.printStackTrace();
            throw e;
        } finally {
            try{
                if(fos != null){
                    fos.close();
                }
            }catch(Exception e){}
        }
        return ok;
    }


    /**
     * 获取图片base64字符串
     * @param imageUrl 图片路径
     * @return
     */
    public static String getBase64ByImageUrl(String imageUrl){
        BufferedImage image = null;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        String base64 = "";
        try {
            //根据url获取图片对象
            URL url = new URL(imageUrl);
            image = ImageIO.read(url);
            if(null != image){
                ImageIO.write(image, "jpg", os);
                //输出流转换为byte数组
                byte[] imageBytes = os.toByteArray();
                //byte数组转换为base64字符串
                base64 = Base64.encodeBase64String(imageBytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return base64;
        }
        return base64;
    }

    /**
     * 下载
     * @param response
     * @param url
     */
    public static void download(HttpServletResponse response,String url){
        OutputStream os = null;
        InputStream in = null;
        try {
            String filePath = url.substring(0,url.lastIndexOf(PATH_SPILTE) + 1);
            String fileName = url.substring(url.lastIndexOf(PATH_SPILTE) + 1,url.length());
            //获取输出流
            os = response.getOutputStream();
            response.reset();
            //设置下载的文件名
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
//            response.addHeader("Content-Length", ftpFiles.getFileSize().toString());
            in = FtpUtil.downloadToStream(filePath,fileName);
            int length;
            byte[] b = new byte[1024];
            while((length = in.read(b))>0){
                os.write(b, 0, length);
            }
            os.flush();
            in.close();
            os.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try {
                if(in != null){
                    in.close();
                }
                if(os != null){
                    os.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /*public static void fileTransferBetweenFTP(){
        if(null == ftpClient){
            connect();
        }

    }

    public static void fileTransferBetweenFTP(FTPClient localClient, FTPClient targetClient, String localDir, String targetDir){
        try {
            if(null == localClient){
                localClient = connect(ftpIp,ftpPort,userName,pwd,null);
            }
            if(null == targetClient){
                targetClient = connect(ftpIp,ftpPort,userName,pwd,null);
            }
            localClient.enterLocalPassiveMode();
            FTPFile[] fs = localClient.listDirectories(localDir);
            for(FTPFile ftpFile : fs){
                String fileName = new String(ftpFile.getName().getBytes("iso-8859-1"),"utf-8");
                if(fileName.endsWith(".dat_fa") || fileName.endsWith(".txt")){
                    targetClient.setRemoteVerificationEnabled(false);
                    OutputStream os = targetClient.storeFileStream(targetClient.printWorkingDirectory() + targetDir + PATH_SPILTE + fileName);
                    if(null == os){
                        break;
                    }
                    localClient.changeWorkingDirectory(localDir);
                    localClient.retrieveFile(fileName,os);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(localClient.isConnected()){
                try {
                    localClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(targetClient.isConnected()){
                try {
                    targetClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/

    //调试代码
    public static void main(String[] args) {
        String base64 = "";
        //upload("20200115","中文.jpg",base64);
        try {
            downloadToLocalDisk("/guanghuisuiyue.wav", localDefaultPath+"guanghuisuiyue.wav");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
