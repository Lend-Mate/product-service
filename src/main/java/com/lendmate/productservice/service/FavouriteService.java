package com.lendmate.productservice.service;

import java.util.List;

import com.lendmate.productservice.dto.requestDto.FavouriteRequest;
import com.lendmate.productservice.dto.responseDto.FavouriteResponse;

public interface FavouriteService {
    FavouriteResponse getFavouriteById(Long id);
    List<FavouriteResponse> getAllFavourites();
    List<FavouriteResponse> getFavouritesByUserId(Long userId);
    FavouriteResponse createFavourite(FavouriteRequest request);
    FavouriteResponse updateFavourite(Long id, FavouriteRequest request);
    void deleteFavourite(Long id);
}
