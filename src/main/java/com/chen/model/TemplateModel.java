package com.chen.model;

import com.chen.utils.AbstractTemplate;
import org.thymeleaf.TemplateEngine;

import java.util.List;

public class TemplateModel extends AbstractTemplate {
    public TemplateModel(TemplateEngine engine, String templateName) {
        super(engine, templateName);
    }

    private Order order;
    private List<OrderGood> orderGoodList;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<OrderGood> getOrderGoodList() {
        return orderGoodList;
    }

    public void setOrderGoodList(List<OrderGood> orderGoodList) {
        this.orderGoodList = orderGoodList;
    }
}
