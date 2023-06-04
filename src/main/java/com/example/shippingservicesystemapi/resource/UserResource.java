package com.example.shippingservicesystemapi.resource;

import com.example.shippingservicesystemapi.dto.UserDTO;
import com.example.shippingservicesystemapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
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
    public ResponseEntity<UserDTO> update(@RequestBody @Validated final UserDTO userDTO) {
        return ResponseEntity.ok(userService.update(userDTO));
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
}
