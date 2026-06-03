package org.example.medtrack.mapper;

import org.example.medtrack.dto.*;
import org.example.medtrack.entity.Medicine;

public class MedicineMapper {

    public static Medicine toEntity(MedicineRequestDTO dto) {
        Medicine m = new Medicine();
        m.setName(dto.getName());
        m.setCategory(dto.getCategory());
        m.setQuantity(dto.getQuantity());
        m.setPrice(dto.getPrice());
        m.setExpiryDate(dto.getExpiryDate());
        return m;
    }

    public static MedicineResponseDTO toDTO(Medicine m) {
        MedicineResponseDTO dto = new MedicineResponseDTO();
        dto.setId(m.getId());
        dto.setName(m.getName());
        dto.setCategory(m.getCategory());
        dto.setQuantity(m.getQuantity());
        dto.setPrice(m.getPrice());
        dto.setExpiryDate(m.getExpiryDate());
        return dto;
    }
}
