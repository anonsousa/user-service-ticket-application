package com.antoniosousa.user.domain.repositories;

import com.antoniosousa.user.domain.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {


    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u SET u.integrated = :integrated WHERE u.id = :id")
    void updateIntegratedStatus(Long id, boolean integrated);


    List<UserEntity> findAllByIntegratedIsFalse();

}
