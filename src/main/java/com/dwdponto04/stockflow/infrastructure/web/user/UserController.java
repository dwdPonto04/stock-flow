package com.dwdponto04.stockflow.infrastructure.web.user;

import com.dwdponto04.stockflow.business.user.dto.request.CreateUserRequestDTO;
import com.dwdponto04.stockflow.business.user.dto.request.PatchUserRequestDTO;
import com.dwdponto04.stockflow.business.user.dto.request.PutUserRequestDTO;
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

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDTO>updateWithPatch(
            @PathVariable Long id,
            @Valid @RequestBody
            PatchUserRequestDTO patchUserRequestDTO
    ){
        UserResponseDTO responseDTO = userService.updateWithPatch(id,patchUserRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping ("/{id}")
    public ResponseEntity<UserResponseDTO> updateWithPut(
            @PathVariable Long id,
            @Valid @RequestBody
            PutUserRequestDTO putUserRequestDTO
    ){
        UserResponseDTO responseDTO = userService.updateWithPut(id,putUserRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }




}
