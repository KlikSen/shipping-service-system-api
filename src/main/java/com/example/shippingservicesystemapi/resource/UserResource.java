package com.example.shippingservicesystemapi.resource;

import com.example.shippingservicesystemapi.dto.ShopOwnerDTO;
import com.example.shippingservicesystemapi.dto.UserDTO;
import com.example.shippingservicesystemapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserResource {
    @Autowired
    private UserService userService;

    @PostMapping(path = "/create")
    public ResponseEntity<String> create(@RequestBody @Validated final UserDTO userDTO){
        userService.create(userDTO);
        return new ResponseEntity<>("Created user successfully!",HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> read(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(userService.read(id));
    }

    @PutMapping("/update")
    @PreAuthorize("isFullyAuthenticated()")
    public ResponseEntity<UserDTO> update(@RequestBody @Validated final UserDTO userDTO) {
        return ResponseEntity.ok(userService.update(userDTO));
    }

    @PutMapping("/update_email")
    @PreAuthorize("isFullyAuthenticated()")
    public ResponseEntity<String> updateEmail(@RequestParam("id") Long id, @RequestParam("email") String email) {
        userService.updateEmail(id, email);
        return ResponseEntity.ok("Email has been updated");
    }

    @PutMapping("/update_password")
    @PreAuthorize("isFullyAuthenticated()")
    public ResponseEntity<String> updatePassword(@RequestParam("id") Long id, @RequestParam("password") String password) {
        userService.updatePassword(id, password);
        return ResponseEntity.ok("Password has been updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") final Long id) {
        userService.delete(id);
        return new ResponseEntity<>("Deleted user successfully!", HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> readAll(){
        return ResponseEntity.ok(userService.readAll());
    }

    @PostMapping("/create_shop_owner")
    public ResponseEntity<String> createShopOwner(@RequestBody @Validated final ShopOwnerDTO shopOwnerDTO){
        userService.createShopOwner(shopOwnerDTO);
        return new ResponseEntity<>("Created user and shop successfully!",HttpStatus.CREATED);
    }

}
