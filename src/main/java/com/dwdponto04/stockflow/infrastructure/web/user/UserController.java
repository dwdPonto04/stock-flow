package com.dwdponto04.stockflow.infrastructure.web.user;

import com.dwdponto04.stockflow.business.user.dto.request.CreateUserRequestDTO;
import com.dwdponto04.stockflow.business.user.dto.request.UpdateUserRequestDTO;
import com.dwdponto04.stockflow.business.user.dto.response.UserResponseDTO;
import com.dwdponto04.stockflow.business.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid  @RequestBody
                                                          CreateUserRequestDTO requestDTO){
        UserResponseDTO responseDTO = userService.createUser(requestDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id){
        UserResponseDTO responseDTO = userService.findById(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/email")
    public ResponseEntity<UserResponseDTO> findByEmail(@RequestParam String email){
        UserResponseDTO responseDTO = userService.findByEmail(email);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll(){
        List<UserResponseDTO> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @PutMapping ("/{id}")
    public ResponseEntity<UserResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody
            UpdateUserRequestDTO updateUserRequestDTO
    ){
        UserResponseDTO responseDTO = userService.update(id,updateUserRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }




}
