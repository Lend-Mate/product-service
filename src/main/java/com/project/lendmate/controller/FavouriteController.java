package com.project.lendmate.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.lendmate.dto.requestDto.FavouriteRequest;
import com.project.lendmate.dto.responseDto.FavouriteResponse;
import com.project.lendmate.service.FavouriteService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/favourites")
public class FavouriteController {
    private final FavouriteService favouriteService;

    @PostMapping
    public ResponseEntity<FavouriteResponse> createFavourite(@Valid @RequestBody FavouriteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(favouriteService.createFavourite(request));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FavouriteResponse> getFavourite(@PathVariable Long id) {
        return ResponseEntity.ok(favouriteService.getFavouriteById(id));
    }

    @GetMapping
    public ResponseEntity<List<FavouriteResponse>> getAllFavourites() {
        return ResponseEntity.ok(favouriteService.getAllFavourites());
    }

    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<List<FavouriteResponse>> getFavouritesByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(favouriteService.getFavouritesByUserId(userId));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<FavouriteResponse> updateFavourite(@PathVariable Long id, @Valid @RequestBody FavouriteRequest request) {
        return ResponseEntity.ok(favouriteService.updateFavourite(id, request));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteFavourite(@PathVariable Long id) {
        favouriteService.deleteFavourite(id);
        return ResponseEntity.noContent().build();
    }
}
