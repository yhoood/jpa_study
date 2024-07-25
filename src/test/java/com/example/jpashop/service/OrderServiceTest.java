package com.example.jpashop.service;
import com.example.domain.jpashop.Address;
import com.example.domain.jpashop.Member;
import com.example.domain.jpashop.Order;
import com.example.domain.jpashop.EnumOrderStatus;
import com.example.domain.jpashop.ItemBook;
import com.example.domain.jpashop.Item;
import com.example.exception.NotEnoughStockException;
import com.example.jpashop.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {
    @PersistenceContext
    EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    @Rollback(false)
    public void 상품주문() throws Exception {
        //Given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10); //이름, 가격, 재고
        int orderCount = 2;
        //When
        Long orderId = orderService.order(member.getMemberId(), item.getItemId(),
                orderCount);
        //Then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals("상품 주문시 상태는 ORDER",EnumOrderStatus.ORDER,
                getOrder.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야 한다.",1,
                getOrder.getOrderItemList().size());
        assertEquals("주문 가격은 가격 * 수량이다.", 10000 * 2,
                getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야 한다.",8, item.getStockQuantity());
    }
    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception {
        //Given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10); //이름, 가격, 재고
        int orderCount = 11; //재고보다 많은 수량
        //When
        orderService.order(member.getMemberId(), item.getItemId(), orderCount);
        //Then
        fail("재고 수량 부족 예외가 발생해야 한다.");
    }
    @Test
    public void 주문취소() {
        //Given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10); //이름, 가격, 재고
        int orderCount = 2;

        Long orderId = orderService.order(member.getMemberId(), item.getItemId(), orderCount);
        //When
        orderService.cancelOrder(orderId);
        //Then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals("주문 취소시 상태는 CANCEL 이다.",EnumOrderStatus.CANCEL,
                getOrder.getStatus());
        assertEquals("주문이 취소된 상품은 그만큼 재고가 증가해야 한다.", 10,
                item.getStockQuantity());
    }
    private Member createMember() {
        Member member = new Member();
        member.setMemberName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }
    private ItemBook createBook(String name, int price, int stockQuantity) {
        ItemBook book = new ItemBook();
        book.setItemName(name);
        book.setStockQuantity(stockQuantity);
        book.setItemPrice(price);
        em.persist(book);
        return book;
    }
}