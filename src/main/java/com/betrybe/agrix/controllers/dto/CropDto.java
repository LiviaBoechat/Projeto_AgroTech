package com.betrybe.agrix.controllers.dto;

import com.betrybe.agrix.models.entities.Crop;
import java.time.LocalDate;

/**
 * Crop DTO.
 */
public record CropDto(Long id, String name, double plantedArea,
                      LocalDate plantedDate, LocalDate harvestDate, Long farmId) {
  public Crop toCrop() {
    Crop crop = new Crop(id, name, plantedArea, plantedDate, harvestDate);
    return crop;
  }

  /**
   * Crop DTO.
   */
  public static CropDto fromCrop(Crop crop) {
    return new CropDto(
        crop.getId(),
        crop.getName(),
        crop.getPlantedArea(),
        crop.getPlantedDate(),
        crop.getHarvestDate(),
        crop.getFarm().getId()
    );
  }
}
