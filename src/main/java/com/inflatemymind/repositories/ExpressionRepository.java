package com.inflatemymind.repositories;

import com.inflatemymind.models.Expression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpressionRepository extends JpaRepository<Expression, Long> {
}
