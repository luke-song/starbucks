package com.example.springcashier;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/")
public class SpringCashierController {

    @Autowired
    private OrderRepository mysql;

    @GetMapping
    public String getAction(@ModelAttribute("command") Command command,
            Model model, HttpSession session) {

        String message = "";

        command.setRegister("5012349");
        message = "Starbucks Reserved Order" + "\n\n" +
                "Register: " + command.getRegister() + "\n" +
                "Status:   " + "Ready for New Order" + "\n";

        String server_ip = "";
        String host_name = "";
        try {
            InetAddress ip = InetAddress.getLocalHost();
            server_ip = ip.getHostAddress();
            host_name = ip.getHostName();
        } catch (Exception e) {
        }

        model.addAttribute("message", message);
        model.addAttribute("server", host_name + "/" + server_ip);

        return "starbucks";

    }

    @PostMapping
    public String postAction(@Valid @ModelAttribute("command") Command command,
            @RequestParam(value = "action", required = true) String action,
            Errors errors, Model model, HttpServletRequest request) {

        String message = "";

        log.info("Action: " + action);
        command.setRegister(command.getStores());
        log.info("Command: " + command);

        /* Process Post Action */
        if (action.equals("Place Order")) {
            Order order = Order.GetNewOrder();
            Integer orderId = order.getId();
            order.setRegister(command.getRegister());
            mysql.save(order);
            message = "Starbucks Reserved Order" + "\n\n" +
                    "OrderId: " + orderId + "\n" +
                    "Drink: " + order.getDrink() + "\n" +
                    "Milk:  " + order.getMilk() + "\n" +
                    "Size:  " + order.getSize() + "\n" +
                    "Total: " + order.getTotal() + "\n" +
                    "\n" +
                    "Register: " + order.getRegister() + "\n" +
                    "Status:   " + order.getStatus() + "\n";
        } else if (action.equals("Get Order")) {
            message = "Starbucks Reserved Order" + "\n\n" +
                    "Register: " + command.getRegister() + "\n" +
                    "Status:   " + "Ready for New Order" + "\n";
        } else if (action.equals("Clear Order")) {
            message = "Starbucks Reserved Order" + "\n\n" +
                    "Register: " + command.getRegister() + "\n" +
                    "Status:   " + "Ready for New Order" + "\n";
        }
        command.setMessage(message);

        String server_ip = "";
        String host_name = "";
        try {
            InetAddress ip = InetAddress.getLocalHost();
            server_ip = ip.getHostAddress();
            host_name = ip.getHostName();
        } catch (Exception e) {
        }

        model.addAttribute("message", message);
        model.addAttribute("server", host_name + "/" + server_ip);

        return "starbucks";

    }

}
