package com.chen.controller;

import com.alibaba.fastjson.JSON;
import com.chen.model.Order;
import com.chen.model.OrderGood;
import com.chen.service.PrintOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrintOrderController {

    @Autowired
    private PrintOrderService printOrderService;

    @PostMapping("/print/order")
    public void print(@RequestBody Order order) {
        OrderGood[] orderGoods = JSON.parseObject(order.getGoods(), OrderGood[].class);
        printOrderService.doPrint(order, orderGoods);
    }
}
