package com.ecard.vCard.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecard.vCard.Entity.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    
}
