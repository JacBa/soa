package com.mock.order.bo;

import com.mock.order.bo.exception.BOException;
import com.mock.order.dto.Order;

public interface OrderBO {

	boolean placeOrder(Order order) throws BOException;

	boolean cancelOrder(int id) throws BOException;

	boolean deleteOrder(int id) throws BOException;
}
