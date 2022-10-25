package uz.uzgps.productservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;

@RestController
public class Controller {

    private static final String USER_ROLE_PRODUCT = "product";

    @GetMapping("/product")
    @RolesAllowed({USER_ROLE_PRODUCT})
    public String getProduct(Principal principal) {

        return "Response from Product Service, User Id:" + principal.getName();
    }
}