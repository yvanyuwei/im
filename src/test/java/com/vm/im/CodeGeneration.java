/*
package com.vm.im;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class CodeGeneration {

    private static String packageName = "D://";    //输出文件路径
    private static String authorName = "zhangqi";     //作者
    private static String[] tables = new String[]{"im_user_chat_group"};
//    private static String[] tables = new String[]{"im_blacklist", "im_group", "im_user", "im_user_chat_group", "im_user_friend", "im_user_friend_apply", "im_user_operation_flow"}; //table名字
//    private static String[] tables = new String[]{"im_level", "im_message", "im_red_packet", "im_red_packet_detial"}; //table名字
//    private static String[] tables = new String[]{"im_chat_group", "im_chat_group_apply", "im_chat_group_flow", "im_chat_group_notice", "im_chat_group_operation_flow", "im_chat_group_user"}; //table名字
    private static String[] prefix = new String[]{"im_"};


    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("1234qwer");
        dsc.setUrl("jdbc:mysql://192.168.1.37:3306/chat_room");
        mpg.setDataSource(dsc);

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(packageName);
        gc.setFileOverride(true);
        gc.setActiveRecord(false);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(false);// XML columList
        gc.setAuthor(authorName);// 作者

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setControllerName("%sController");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        mpg.setGlobalConfig(gc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setInclude(tables); // 需要生成的表
        strategy.setTablePrefix(prefix);//前缀
        strategy.setSuperServiceClass(null);
        strategy.setSuperServiceImplClass(null);
        strategy.setSuperMapperClass(null);

        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.vm.im");

        pc.setController("controller.user");
        pc.setService("service.user");
        pc.setServiceImpl("service.user.impl");
        pc.setMapper("dao.user");
        pc.setEntity("entity.user");
        pc.setXml("xml.user");
        mpg.setPackageInfo(pc);

        // 执行生成
        mpg.execute();

    }

}
*/
