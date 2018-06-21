package com.chinasoft.shell;

import com.chinasoft.service.AosSerice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

/**
 * Created by VerRan.Liu on 2018/6/11.
 * ShellUI 用于实现华为API调试的使用shell工具
 */
@ShellComponent
public class ShellUI {

    @Autowired
    private AosSerice aosSerice;


    @ShellMethod("method for query AOS template")
    public  String template(String templateId){
         return aosSerice.queryAOSTemplate(templateId);
    }


    @ShellMethod("show the summary data of work time list ")
    public String show(){
        StringBuffer buffer =new StringBuffer();
        buffer.append("11111");
        return buffer.toString();
    }
}
