package commands

import epizza.delivery.order.Order
import epizza.delivery.order.OrderServiceClient
import org.crsh.cli.Command
import org.crsh.cli.Usage
import org.crsh.command.InvocationContext
import org.springframework.beans.factory.BeanFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.hateoas.PagedResources
import org.springframework.util.Assert

class orders {

    @Usage("lists open orders")
    @Command
    def main(InvocationContext context) {
        BeanFactory beanFactory = context.getAttributes().get("spring.beanfactory")
        Assert.notNull(beanFactory)
        Pageable currentPage = new PageRequest(0, 10)

        OrderServiceClient listClient = beanFactory.getBean(OrderServiceClient)
        PagedResources<Order> orders = listClient.getOrders(currentPage)
        String result = ""

        for (Order order in orders.getContent()) {
            result += order.getOrderId() + "\t" + order.getDeliveryAddress() + "\n"
        }
        return result
    }

}