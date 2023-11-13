package com.betrybe.agrix.controllers;

import com.betrybe.agrix.controllers.dto.CropDto;
import com.betrybe.agrix.controllers.dto.FarmDto;
import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.models.entities.Farm;
import com.betrybe.agrix.services.CropService;
import com.betrybe.agrix.services.FarmService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Farm.
 */
@RestController
@RequestMapping(value = "/farms")
public class FarmController {
  private final FarmService farmService;
  private final CropService cropService;

  @Autowired
  public FarmController(FarmService farmService, CropService cropService) {
    this.farmService = farmService;
    this.cropService = cropService;
  }

  /**
   * Farm.
   */
  @PostMapping()
  public ResponseEntity<Farm> insertFarm(@RequestBody FarmDto farmDto) {
    Farm newFarm = farmService.insertFarm(farmDto.toFarm());
    return ResponseEntity.status(HttpStatus.CREATED).body(newFarm);
  }

  /**
   * Farm.
   */
  @GetMapping()
  public List<FarmDto> getAllFarms() {
    List<Farm> allFarms = farmService.getAllFarms();

    return allFarms.stream()
        .map((farm) -> new FarmDto(farm.getId(), farm.getName(), farm.getSize()))
        .collect(Collectors.toList());
  }

  /**
   * Farm.
   */
  @GetMapping("/{id}")
  public ResponseEntity<?> getFarmById(@PathVariable Long id) {
    Optional<Farm> optionalFarm = farmService.getFarmById(id);

    if (optionalFarm.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fazenda não encontrada!");
    }

    return ResponseEntity.ok(optionalFarm.get());
  }

  /**
   * Crop.
   */
  @PostMapping("/{farmId}/crops")
  public ResponseEntity<?> insertCrop(@PathVariable Long farmId, @RequestBody CropDto cropDto) {
    Optional<Farm> optionalFarm = farmService.getFarmById(farmId);

    if (optionalFarm.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fazenda não encontrada!");
    }

    // desempacotei farm do Optional
    Farm farm = optionalFarm.get();

    // criei inst. de Crop
    Crop crop = cropDto.toCrop();

    // atruibui à plantação : fazenda encontrada pelo Id + datas da plantação e colheita
    crop.setFarm(farm);
    crop.setPlantedDate(cropDto.plantedDate());
    crop.setHarvestDate(cropDto.harvestDate());

    // persisti a nova inst. crop criada. Ela retorna o obj. persistido
    Crop savedCrop = cropService.insertCrop(crop);

    CropDto response = new CropDto(savedCrop.getId(),
        savedCrop.getName(), savedCrop.getPlantedArea(),
        savedCrop.getPlantedDate(), savedCrop.getHarvestDate(), farmId);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  /**
   * Crop.
   */
  @GetMapping("/{farmId}/crops")
  public ResponseEntity<?> getCropsByFarmId(@PathVariable Long farmId) {
    Optional<Farm> optionalFarm = farmService.getFarmById(farmId);

    if (optionalFarm.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fazenda não encontrada!");
    }

    Farm farm = optionalFarm.get();
    List<Crop> crops = farm.getCrops();

    List<CropDto> cropDtos = crops.stream()
        .map(crop -> new CropDto(crop.getId(), crop.getName(), crop.getPlantedArea(),
            crop.getPlantedDate(), crop.getHarvestDate(), farmId)).collect(Collectors.toList());

    return ResponseEntity.ok(cropDtos);
  }

}

