package br.com.pitang.selecao_java.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.pitang.selecao_java.model.JwtRequest;
import br.com.pitang.selecao_java.model.JwtResponse;
import br.com.pitang.selecao_java.services.UserService;
import br.com.pitang.selecao_java.util.JwtUtil;
import br.com.pitang.selecao_java.vo.ErrorMessageVO;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        try {
        	UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        	authenticationManager.authenticate(token);
        } catch (BadCredentialsException e) {
            ErrorMessageVO vo = new ErrorMessageVO("Invalid login or password",1);
            e.printStackTrace();
    		return new ResponseEntity<>(vo, HttpStatus.BAD_REQUEST);
        }

        final UserDetails userDetails = userService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(new JwtResponse(jwt));
    }
}