package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SumaryMinDTO;
import com.devsuperior.dsmeta.projections.SumaryProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;

	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}
	@Transactional(readOnly = true)
	public List<SumaryMinDTO> sumarySearch(String minDate, String maxDate){

		LocalDate finalDate = maxDate.equals("") ?
				LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()) :
				LocalDate.parse(maxDate);
		LocalDate initialDate = minDate.equals("") ?
				finalDate.minusYears(1L) :
				LocalDate.parse(minDate);



		List<SumaryProjection> sumary = repository.summarySearch(initialDate, finalDate);
		return sumary.stream().map(SumaryMinDTO::new).collect(Collectors.toList());

	}

	public Page<SaleReportDTO> findReport(String minDate, String maxDate, String sellerName, Pageable pageable) {

		LocalDate maxLocalDate = maxDate.equals("") ? LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()) : LocalDate.parse(maxDate);
		LocalDate minLocalDate = minDate.equals("") ? maxLocalDate.minusYears(1L) : LocalDate.parse(minDate);

		return repository.searchReport(minLocalDate, maxLocalDate, sellerName, pageable);

	}
}
