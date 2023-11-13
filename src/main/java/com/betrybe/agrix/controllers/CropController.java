package com.betrybe.agrix.controllers;

import com.betrybe.agrix.controllers.dto.CropDto;
import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.models.entities.Fertilizer;
import com.betrybe.agrix.services.CropService;
import com.betrybe.agrix.services.FertilizerService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Crop.
 */
@RestController
@RequestMapping("/crops")
public class CropController {
  private final CropService cropService;
  private final FertilizerService fertilizerService;

  @Autowired
  public CropController(CropService cropService, FertilizerService fertilizerService) {
    this.cropService = cropService;
    this.fertilizerService = fertilizerService;
  }

  /**
   * Crop.
   */
  @GetMapping
  public List<CropDto> getAllCrops() {
    List<Crop> crops = cropService.getAllCrops();

    return crops.stream()
        .map(crop -> new CropDto(crop.getId(), crop.getName(), crop.getPlantedArea(),
            crop.getPlantedDate(), crop.getHarvestDate(), crop.getFarm().getId()))
        .collect(Collectors.toList());
  }

  /**
   * Crop.
   */
  @GetMapping("/{id}")
  public ResponseEntity<?> getCropById(@PathVariable Long id) {
    Optional<Crop> optionalCrop = cropService.getCropById(id);

    if (optionalCrop.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plantação não encontrada!");
    }

    Crop crop = optionalCrop.get();
    CropDto cropDto = new CropDto(crop.getId(), crop.getName(),
        crop.getPlantedArea(), crop.getPlantedDate(),
        crop.getHarvestDate(), crop.getFarm().getId());

    return ResponseEntity.ok(cropDto);
  }

  /**
   * Crop.
   */
  @GetMapping("/search")
  public ResponseEntity<List<CropDto>> getCropByDate(@RequestParam String start,
      @RequestParam String end) {
    LocalDate reqStart = LocalDate.parse(start);
    LocalDate reqEnd = LocalDate.parse(end);

    List<Crop> crops = cropService.getAllCrops();
    List<CropDto> matchingDates = new ArrayList<>();

    for (Crop crop : crops) {
      LocalDate harvestDate = crop.getHarvestDate();
      int compareStart = harvestDate.compareTo(reqStart);
      int compareEnd = harvestDate.compareTo(reqEnd);

      if (compareStart >= 0 && compareEnd <= 0) {
        // Criei nova inst. de CropDto, pois contém farmId
        CropDto cropDtoWithFarmId = new CropDto(crop.getId(), crop.getName(),
            crop.getPlantedArea(), crop.getPlantedDate(),
            crop.getHarvestDate(), crop.getFarm().getId());
        // adicionei a nova CropDto à lista
        matchingDates.add(cropDtoWithFarmId);
      }
    }
    return ResponseEntity.ok(matchingDates);
  }

  /**
   * Crop.
   */
  @PostMapping("/{cropId}/fertilizers/{fertilizerId}")
  public ResponseEntity<String> associateCropWithFertilizer(@PathVariable Long cropId,
      @PathVariable Long fertilizerId) {
    Optional<Crop> cropOptional = cropService.getCropById(cropId);
    Optional<Fertilizer> fertilizerOptional = fertilizerService.getFertilizerById(fertilizerId);

    if (cropOptional.isPresent() && fertilizerOptional.isPresent()) {
      // desempacota ambos do Optional
      Crop crop = cropOptional.get();
      Fertilizer fertilizer = fertilizerOptional.get();

      crop.getFertilizers().add(fertilizer);
      // persiste no banco
      cropService.insertCrop(crop);

      return ResponseEntity.status(HttpStatus.CREATED)
          .body("Fertilizante e plantação associados com sucesso!");
    } else if (!cropOptional.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plantação não encontrada!");
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fertilizante não encontrado!");
    }
  }

  /**
   * Crop.
   */
  @GetMapping("/{cropId}/fertilizers")
  public ResponseEntity<?> getFertilizersForCrop(@PathVariable Long cropId) {
    Optional<Crop> cropOptional = cropService.getCropById(cropId);

    if (cropOptional.isPresent()) {
      Crop crop = cropOptional.get();
      return ResponseEntity.status(HttpStatus.OK).body(crop.getFertilizers());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plantação não encontrada!");
    }
  }

}
