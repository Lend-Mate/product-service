package com.lendmate.productservice.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lendmate.productservice.dto.requestDto.FavouriteRequest;
import com.lendmate.productservice.dto.responseDto.FavouriteResponse;
import com.lendmate.productservice.expection.FavouriteNotFoundException;
import com.lendmate.productservice.mapper.FavouriteMapper;
import com.lendmate.productservice.model.Favourite;
import com.lendmate.productservice.repository.FavouriteRepository;
import com.lendmate.productservice.service.FavouriteService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FavouriteServiceImpl implements FavouriteService {
    private final FavouriteRepository favouriteRepository;
    private final FavouriteMapper mapper;

    @Override
    public FavouriteResponse getFavouriteById(Long id) {
        Favourite favourite = favouriteRepository.findById(id)
                .orElseThrow(() -> new FavouriteNotFoundException("Favourite bulunamadı: " + id));
        return mapper.toDto(favourite);
    }

    @Override
    public List<FavouriteResponse> getAllFavourites() {
        return favouriteRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<FavouriteResponse> getFavouritesByUserId(Long userId) {
        return favouriteRepository.findAllByUserId(userId)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public FavouriteResponse createFavourite(FavouriteRequest request) {
        if (favouriteRepository.existsByProductIdAndUserId(request.getProductId(), request.getUserId())) {
            throw new IllegalArgumentException("Bu ürün zaten favorilere eklenmiş.");
        }
        Favourite favourite = mapper.toEntity(request);
        Favourite saved = favouriteRepository.save(favourite);
        return mapper.toDto(saved);
    }

    @Override
    public FavouriteResponse updateFavourite(Long id, FavouriteRequest request) {
        Favourite favourite = favouriteRepository.findById(id)
                .orElseThrow(() -> new FavouriteNotFoundException("Favourite bulunamadı: " + id));
        mapper.updateEntity(favourite, request);
        Favourite updated = favouriteRepository.save(favourite);
        return mapper.toDto(updated);
    }

    @Override
    public void deleteFavourite(Long id) {
        Favourite favourite = favouriteRepository.findById(id)
                .orElseThrow(() -> new FavouriteNotFoundException("Favourite bulunamadı: " + id));
        favouriteRepository.delete(favourite);
    }
}
