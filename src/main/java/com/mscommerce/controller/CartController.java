package com.mscommerce.controller;

import com.mscommerce.exception.BadRequestException;
import com.mscommerce.exception.ResourceNotFoundException;
import com.mscommerce.models.DTO.cart.CartDTO;
import com.mscommerce.models.DTO.cart.CartDTORequest;
import com.mscommerce.models.DTO.cart.CartDTOUpdate;
import com.mscommerce.service.implementation.CartServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    final private CartServiceImpl cartServiceImpl;

    @GetMapping("/all-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CartDTO>> adminGetAllCarts() throws ResourceNotFoundException {
        List<CartDTO> cartDTOs = cartServiceImpl.adminGetAllCarts();
        return ResponseEntity.ok(cartDTOs);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    public ResponseEntity<List<CartDTO>> getAllCarts() throws ResourceNotFoundException {
        List<CartDTO> cartDTOs = cartServiceImpl.getAllCarts();
        return ResponseEntity.ok().body(cartDTOs);
    }

    @GetMapping("/id/{cartId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CartDTO> adminGetCartById(@PathVariable Integer cartId) throws ResourceNotFoundException {
        CartDTO cartDTO = cartServiceImpl.adminGetCartById(cartId);
        return ResponseEntity.ok().body(cartDTO);
    }

    @PostMapping("/create-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CartDTO> adminCreateCart(@RequestBody CartDTO cartDTO) throws BadRequestException, ResourceNotFoundException {
        CartDTO createdCart = cartServiceImpl.adminCreateCart(cartDTO);
        return new ResponseEntity<>(createdCart, HttpStatus.CREATED);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    public ResponseEntity<CartDTO> createCart(@RequestBody CartDTORequest cartDTORequest) throws BadRequestException, ResourceNotFoundException {
        CartDTO createdCart = cartServiceImpl.createCart(cartDTORequest);
        return new ResponseEntity<>(createdCart, HttpStatus.CREATED);
    }

    @PutMapping("/update-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CartDTO> adminUpdateCart(@RequestBody CartDTO cartDTO) throws BadRequestException, ResourceNotFoundException {
        CartDTO updatedCart = cartServiceImpl.adminUpdateCart(cartDTO);
        return ResponseEntity.ok().body(updatedCart);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    public ResponseEntity<CartDTO> updateCart(@RequestBody CartDTOUpdate cartDTOUpdate) throws BadRequestException, ResourceNotFoundException {
        CartDTO updatedCart = cartServiceImpl.updateCart(cartDTOUpdate);
        return ResponseEntity.ok().body(updatedCart);
    }

    @DeleteMapping("/delete-admin/{cartId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> adminDeleteCart(@PathVariable Integer cartId) throws ResourceNotFoundException {
        cartServiceImpl.adminDeleteCart(cartId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{cartId}")
    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCart(@PathVariable Integer cartId) throws ResourceNotFoundException {
        cartServiceImpl.deleteCart(cartId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clean")
    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    public ResponseEntity<Void> cleanCart() {
        cartServiceImpl.cleanCart();
        return ResponseEntity.noContent().build();
    }
}
