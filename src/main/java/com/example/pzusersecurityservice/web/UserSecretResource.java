package com.example.pzusersecurityservice.web;

import com.example.pzusersecurityservice.model.Roles;
import com.example.pzusersecurityservice.model.UserSecret;
import com.example.pzusersecurityservice.security.JwtTokenProvider;
import com.example.pzusersecurityservice.service.UserSecurityService;
import com.example.pzusersecurityservice.web.dto.NewUserSecretRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/secret")
@AllArgsConstructor
public class UserSecretResource {
    private UserSecurityService userSecurityService;
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/new")
    public ResponseEntity<?> createUserSecretEntity(@RequestBody @Valid NewUserSecretRequestDto dto, BindingResult result){
        if (result.hasErrors()) {
            HashMap<String, String> map = new HashMap<>();
            result.getFieldErrors().forEach(e -> map.put(e.getField(), e.getDefaultMessage()));
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
        if(dto.getPassword().equals(dto.getPasswordTwo()) && userSecurityService.existsUserCredentials(dto.getDetailsId())){
            UserSecret sec = UserSecret.builder()
                    .detailsId(dto.getDetailsId())
                    .password(dto.getPassword())
                    .role(Roles.PARTICIPANT)
                    .build();
            UserSecret saved = userSecurityService.save(sec);
            String jwt = userSecurityService.createJWT(saved.getId().toString(), saved.getRole());
            return new ResponseEntity<>(jwt, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping()
    public ResponseEntity<UserSecret> getUserSecretEntity( HttpServletRequest request){
        String s = jwtTokenProvider.resolveToken(request);
        String username = jwtTokenProvider.getUsername(s);
        return new ResponseEntity<>(userSecurityService.getUserSecret(UUID.fromString(username)), HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteUserSecretEntity(HttpServletRequest request){
        String s = jwtTokenProvider.resolveToken(request);
        String id = jwtTokenProvider.getUsername(s);
        userSecurityService.deleteUserSecret(UUID.fromString(id));
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
