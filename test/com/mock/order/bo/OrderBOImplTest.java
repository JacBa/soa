package com.mock.order.bo;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.mock.order.bo.exception.BOException;
import com.mock.order.dao.OrderDAO;
import com.mock.order.dto.Order;

public class OrderBOImplTest {

	@Mock
	OrderDAO dao;
	private OrderBOImpl bo;
	private static final int ORDER_ID = 123;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		bo = new OrderBOImpl();
		bo.setDao(dao);
	}

	@Test
	public void placeOrder_Should_Create_An_Order() throws SQLException,
			BOException {
		//Order order = new Order(); // Matchers
		when(dao.create(any(Order.class))).thenReturn(new Integer(1));
		// use of Mockito Matchers as a helper instead of instantiating Order
		boolean result = bo.placeOrder(any(Order.class));

		assertTrue(result);
		verify(dao, times(1)).create(any(Order.class));
	}

	@Test
	public void placeOrder_Should_Not_Create_An_Order() throws SQLException,
			BOException {
		Order order = new Order();
		when(dao.create(order)).thenReturn(new Integer(0));
		boolean result = bo.placeOrder(order);

		assertFalse(result);
		verify(dao, atLeastOnce()).create(order);
	}

	@Test(expected = BOException.class)
	public void placeOrder_Should_Throw_BOExceptionOnCreate() throws SQLException,
			BOException {
		Order order = new Order();
		when(dao.create(order)).thenThrow(SQLException.class);
		boolean result = bo.placeOrder(order);
	}

	@Test
	public void cancelOrder_Should_Cancel_An_Order() throws SQLException,
			BOException {
		Order order = new Order();
		when(dao.read(ORDER_ID)).thenReturn(order);
		when(dao.update(order)).thenReturn(1);
		boolean result = bo.cancelOrder(ORDER_ID);

		assertTrue(result);
		verify(dao,atMost(1)).read(ORDER_ID);
		verify(dao,atLeast(1)).update(order);
	}

	@Test
	public void cancelOrder_Should_Not_Cancel_An_Order() throws SQLException,
			BOException {
		Order order = new Order();
		when(dao.read(ORDER_ID)).thenReturn(order);
		when(dao.update(order)).thenReturn(0);
		boolean result = bo.cancelOrder(ORDER_ID);

		assertFalse(result);
		verify(dao).read(ORDER_ID);
		verify(dao).update(order);
	}

	@Test(expected = BOException.class)
	public void cancelOrder_Should_Throw_BOExceptionOnRead()
			throws SQLException, BOException {
		when(dao.read(ORDER_ID)).thenThrow(SQLException.class);
		bo.cancelOrder(ORDER_ID);
	}

	@Test(expected = BOException.class)
	public void cancelOrder_Should_Throw_BOExceptionOnUpdate()
			throws SQLException, BOException {
		Order order = new Order();
		when(dao.read(ORDER_ID)).thenReturn(order);
		when(dao.update(order)).thenThrow(SQLException.class);
		bo.cancelOrder(ORDER_ID);
	}

	@Test
	public void deleteOrder_Should_Delete_An_Order() throws SQLException, BOException {
		when(dao.delete(ORDER_ID)).thenReturn(1);
		boolean result = bo.deleteOrder(ORDER_ID);

		assertTrue(result);
		verify(dao).delete(ORDER_ID);
	}
	
	@Test
	public void deleteOrder_Should_Not_Delete_An_Order() throws SQLException, BOException {
		when(dao.delete(ORDER_ID)).thenReturn(0);
		boolean result = bo.deleteOrder(ORDER_ID);

		assertFalse(result);
		verify(dao).delete(ORDER_ID); // times by default is 1
	}
	
	@Test(expected = BOException.class)
	public void deleteOrder_Should_Throw_BOExceptionOnDelete()
			throws SQLException, BOException {
		when(dao.delete(ORDER_ID)).thenThrow(SQLException.class);
		bo.deleteOrder(ORDER_ID);
	}


}
