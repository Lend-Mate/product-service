package com.project.lendmate.service;

import java.util.List;

import com.project.lendmate.dto.requestDto.FavouriteRequest;
import com.project.lendmate.dto.responseDto.FavouriteResponse;

public interface FavouriteService {
    FavouriteResponse getFavouriteById(Long id);
    List<FavouriteResponse> getAllFavourites();
    List<FavouriteResponse> getFavouritesByUserId(Long userId);
    FavouriteResponse createFavourite(FavouriteRequest request);
    FavouriteResponse updateFavourite(Long id, FavouriteRequest request);
    void deleteFavourite(Long id);
}
