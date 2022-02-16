package com.chen.service;

import com.chen.model.Order;
import com.chen.model.OrderGood;

public interface PrintOrderService {
    void doPrint(Order order, OrderGood[] orderGoods);
}
