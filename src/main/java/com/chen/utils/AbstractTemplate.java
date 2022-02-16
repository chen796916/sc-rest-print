package com.chen.utils;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractTemplate {
    // 使用thymeleaf模版引擎
    private TemplateEngine engine;
    // 模版名称
    private String templateName;

    private AbstractTemplate() {}

    public AbstractTemplate(TemplateEngine engine,String templateName) {
        this.engine = engine;
        this.templateName=templateName;
    }

    /**
     * 模版名称
     *
     * @return
     */
    protected String templateName(){
        return this.templateName;
    }

    /**
     * 所有的参数数据
     *
     * @return
     */
    private Map<String, Object> variables(){
        Map<String, Object> variables = new HashMap<>();
        // 对应html模版中的template变量，取值的时候就按照“${template.字段名}”格式，可自行修改
        variables.put("template", this);
        return variables;
    };

    /**
     * 解析模版，生成html
     *
     * @return
     */
    public String process() {
        Context ctx = new Context();
        // 设置model
        ctx.setVariables(variables());
        // 根据model解析成html字符串
        return engine.process(templateName(), ctx);
    }

    public void parse2Pdf(String targetPdfFilePath) {
        String html = process();
        HtmlUtil.html2Pdf(html, targetPdfFilePath);
    }
}
