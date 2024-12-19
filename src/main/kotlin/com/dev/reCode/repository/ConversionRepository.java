package com.dev.reCode.repository;

import com.dev.reCode.models.Conversion;
import com.dev.reCode.models.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConversionRepository extends JpaRepository<Conversion, String> {
    Page<Conversion> findPageableAllByUser(Pageable pageable, User user);
    List<Conversion> findAllByUser(User user);
}
