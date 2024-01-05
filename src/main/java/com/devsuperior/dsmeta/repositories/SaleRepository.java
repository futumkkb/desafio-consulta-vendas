package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.projections.SumaryProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleReportDTO(obj) " +
            "FROM Sale obj " +
            "WHERE obj.date BETWEEN :minLocalDate AND :maxLocalDate " +
            "AND UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :sellerName, '%'))")
    Page<SaleReportDTO> searchReport(LocalDate minLocalDate, LocalDate maxLocalDate, String sellerName, Pageable pageable);



    @Query(nativeQuery = true, value = "SELECT tb_seller.name AS name, SUM(tb_sales.amount) as amount " +
            "FROM tb_sales " +
            "JOIN tb_seller ON tb_sales.seller_id = tb_seller.id " +
            "WHERE tb_sales.date >= :minDate AND tb_sales.date <= :maxDate " +
            "GROUP BY tb_seller.name; ")
    public List<SumaryProjection> summarySearch(LocalDate minDate, LocalDate maxDate);

}
