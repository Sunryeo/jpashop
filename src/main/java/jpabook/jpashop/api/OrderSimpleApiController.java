package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.dto.OrderSimpleQueryDto;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            this.orderId = order.getId();
            this.name = order.getMember().getName(); // Lazy 초기화
            this.orderDate = order.getOrderDate();
            this.orderStatus = order.getStatus();
            this.address = order.getDelivery().getAddress(); // Lazy 초기화
        }
    }

    @GetMapping("/api/v2/simple-orders")
    public Result ordersV2() {
        List<SimpleOrderDto> orders = orderService.findOrders(new OrderSearch()).stream()
                .map(el -> new SimpleOrderDto(el)) // == .map(SimpleOrderDto::new)
                .collect(Collectors.toList());

        return new Result(orders);
    }

    @GetMapping("/api/v3/simple-orders")
    public Result orderV3() {
        List<SimpleOrderDto> orders = orderRepository.findAllWithMemberDelivery().stream()
                .map(el -> new SimpleOrderDto(el))
                .collect(Collectors.toList());

        return new Result(orders);
    }

    @GetMapping("/api/v4/simple-orders")
    public Result orderV4() {
        List<OrderSimpleQueryDto> orderDtos = orderRepository.findOrderDtos();
        return new Result(orderDtos);
    }
}
