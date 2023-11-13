package com.betrybe.agrix.services;

import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.models.repositories.CropRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Crop.
 */
@Service
public class CropService {
  private final CropRepository cropRepository;

  @Autowired
  public CropService(CropRepository cropRepository) {
    this.cropRepository = cropRepository;
  }

  public Crop insertCrop(Crop crop) {
    return cropRepository.save(crop);
  }

  public List<Crop> getAllCrops() {
    return cropRepository.findAll();
  }

  public Optional<Crop> getCropById(Long id) {
    return cropRepository.findById(id);
  }
}
