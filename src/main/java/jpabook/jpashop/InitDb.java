package jpabook.jpashop;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void dbInit1() {
            Member member1 = createMember("userA", "seoul", "street1", "12345");
            em.persist(member1);

            Book book1 = createBook("JPA book1", 10000, 100);
            em.persist(book1);

            Book book2 = createBook("JPA book2", 20000, 100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = createDelivery(member1);
            Order order = Order.createOrder(member1, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2() {
            Member member2 = createMember("userB", "busan", "street2", "11111");
            em.persist(member2);

            Book book1 = createBook("Spring book1", 20000, 100);
            em.persist(book1);

            Book book2 = createBook("Spring book2", 40000, 100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 2);

            Delivery delivery = createDelivery(member2);
            Order order = Order.createOrder(member2, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        private static Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

        private static Book createBook(String name, int price, int stock) {
            Book book = new Book();
            book.setName(name);
            book.setPrice(price);
            book.setStockQuantity(stock);
            return book;
        }

        private static Member createMember(String name, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }
    }
}
