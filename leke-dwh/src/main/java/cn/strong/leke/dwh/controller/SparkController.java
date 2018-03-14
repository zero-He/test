package cn.strong.leke.dwh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zero on 2017/3/14.
 */
@Controller
public class SparkController {

    @RequestMapping("/classesAnalyze")
    public String submitSpark() {
        String scriptPath = "/opt/sparkshell/submitspark.sh";
        String taskId = "";
        try {
            String execCmd = "/bin/sh " + scriptPath + " " + taskId; //+param
            Process process = Runtime.getRuntime().exec(execCmd);
            process.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }


        return "/hello.jsp";
    }

}
