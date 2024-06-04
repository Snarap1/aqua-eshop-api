package com.alibou.security.services;

import com.alibou.security.enums.OrderStatus;
import com.alibou.security.models.*;
import com.alibou.security.repositories.OrderRepo;
import com.alibou.security.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private  final OrderRepo orderRepo;
    private  final  OrderItemService orderItemService;
    private  final  ProductService productService;
    private  final  AddressService addressService;
    private  final  CartService cartService;


    @Autowired
    public OrderService(OrderRepo orderRepo, OrderItemService orderItemService, ProductService productService, AddressService addressService, CartService cartService) {
        this.orderRepo = orderRepo;
        this.orderItemService = orderItemService;
        this.productService = productService;
        this.addressService = addressService;
        this.cartService = cartService;
    }


    // CRUD

    // Create
    public void saveOrder(Order order)
    {
        orderRepo.save(order);
    }

    public void addToCart(Long id, User user){
            Product product = productService.getProduct(id);
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(1);
            orderItem.setCart(user.getCart());
            user.getCart().getOrderItems().add(orderItem);
            orderItemService.createItem(orderItem);
    }

    // Read
    public List<Order> getAllOrders(){
        return orderRepo.findAll();
    }

    public  List<Order> getOrdersByUser(User user){
        return orderRepo.findByUser(user);
    }

    public  List<Order> getOrdersByUserAndStatus(User user,OrderStatus status){
        return orderRepo.findByUserAndStatus(user,status);
    }


    public  List<Order> getOrdersByStatus(OrderStatus status){
        return orderRepo.findByStatus(status);
    }


    public Order getOrder(Long orderId){
        return orderRepo.findById(orderId)
                .orElseThrow(()-> new EntityNotFoundException("Order for this ID not found"));
    }

//    public  Cart getCartByUser(User user){
//        return  cartRepo.getCartByUser(user);
//    }

    // Update

    public void changeStatus(Long orderId,OrderStatus status)
    {
        Order order = getOrder(orderId);
        order.setStatus(status);
        saveOrder(order);
    }

    @SneakyThrows
    public  void setAddress(Long orderId, Long addresId){
        Order order = getOrder(orderId);
        Address address = addressService.getAddressById(addresId);
        order.setAddress(address);
        saveOrder(order);
    }

    public byte[] downloadOrderDetails(Long orderId){
        Order order = getOrder(orderId);
        User user = order.getUser();
        StringBuilder orderDetails =
                new StringBuilder("Заказчик: " + user.getFirstname()
                        + " " + user.getLastname() + " " + user.getEmail() + "\n" +
                        "Способ доставки: " + order.getDeliveryMethod().getDisplayName() + "\n"
                        +"Список товаров: " + "\n");

        for (OrderItem item : order.getOrderItems()) {
            orderDetails.append(item.getProduct().getName()).append(" количество: ").append(item.getQuantity()).append("\n");
        }

        orderDetails.append("  Сумма: ").append(order.getTotalAmount()).append("\n");
        if (order.getAddress() != null){
            Address address = order.getAddress();
            orderDetails.append("Адрес доставки: ").append(address.getCity()+" ")
                    .append(address.getStreet()+" ").append(address.getEntrance()+" ")
                    .append(address.getFloor()+" ").append(address.getAppartment()+" ")
                    .append(address.getDate()+" ").append(address.getTime()).append("\n");
        }
        byte[] content = orderDetails.toString().getBytes();
        return content;
    }


    public void buy(User user){

        Cart cart = user.getCart();
        Order newOrder = new  Order();
        saveOrder(newOrder);

        List<OrderItem> items = cart.getOrderItems();
        List<OrderItem> orderItems   = new ArrayList<>();

        List<OrderItem> list = new ArrayList<>();
        cart.setOrderItems(list);
        cartService.saveCart(cart);

        for(OrderItem item: items){
            item.setCart(null);
            item.setOrder(newOrder);
            orderItems.add(item);
            orderItemService.createItem(item);
        }

        newOrder.setOrderItems(orderItems);
        newOrder.setUser(cart.getUser());
        newOrder.setTotalAmount(cart.getTotalCost());
        newOrder.setStatus(OrderStatus.PROCESSING);
        newOrder.setDeliveryMethod(cart.getDeliveryMethod());
        newOrder.setCreatedAt(LocalDate.now());

        saveOrder(newOrder);
    }

    // Delete
    public  void  deleteOrder(Long orderId){
        Order order = getOrder(orderId);
        orderRepo.delete(order);
    }
}
