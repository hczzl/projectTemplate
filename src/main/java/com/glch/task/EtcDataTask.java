package com.glch.task;

import com.csvreader.CsvReader;
import com.glch.base.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.Charset;

/**
 * @author zhongzhilong
 * @date 2021-02-28
 * @description 定时同步etc数据
 */
@Component
@Configuration
@EnableScheduling
public class EtcDataTask {
    /**
     * csv文件磁盘地址
     */
    @Value("${csvFilePath}")
    public String filePath;

    @Autowired
    @Qualifier("storageTemplate")
    private JdbcTemplate storageTemplate;

    @Scheduled(cron = "0/10 * * * * ? ")
    public void insertData() {
        // 获取目标文件夹下的所有.csv文件
        File root = new File(filePath);
        File[] fileList = root.listFiles();
        if (fileList == null || fileList.length < 1) {
            return;
        }
        CsvReader r;
        for (File file : fileList) {
            // 判断file是否是文件夹
            if (file.isDirectory()) {
                continue;
            }
            // 判断文件内容是不是空的，若为空，删除该文件
            if (!file.exists() || file.length() == 0) {
                file.delete();
                continue;
            }
            // 文件路径+文件名
            String csvFilePath = file.getAbsolutePath();
            try {
                // 生成CsvReader对象，以;为分隔符，GBK编码方式
                r = new CsvReader(csvFilePath, ';', Charset.forName("GBK"));
                if (r == null) {
                    continue;
                }
                // 根据文件名决定存入哪个表中
                String fileName = file.getName();
                int index = fileName.indexOf("_");
                String flag = fileName.substring(0, index);
                //读取表头
                r.readHeaders();
                // 拼接插入语句
                StringBuilder sql = new StringBuilder("insert into stu(id,add_time,name,create_time) values \n");
                StringBuilder sqlTem = new StringBuilder();
                int i = 0;
                while (r.readRecord()) {
                    String id = r.get("id");
                    String addTime = r.get("add_time");
                    String name = r.get("name");
                    String createTime = r.get("create_time");
                    System.out.println(id + ";" + addTime + ";" + name + ";" + createTime);
                    if (i > 0) {
                        sqlTem.append(",");
                    }
                    Long count = storageTemplate.queryForObject("select count(*) from stu where id = ?", new Object[]{Integer.parseInt(id)}, Long.class);
                    if (count < 1) {
                        sqlTem.append("('").append(id).append("',").append("'").append(addTime).append("',")
                                .append("'").append(name).append("',").append("'").append(createTime).append("') \n");
                        i++;
                    }
                }
                if (!StringUtil.isEmpty(sqlTem.toString())) {
                    sql.append(sqlTem);
                    storageTemplate.update(sql.toString());
                }
                r.close();
                file.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
