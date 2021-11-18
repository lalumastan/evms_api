package sqltutorial.evms_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sqltutorial.evms_api.model.VaccineType;

public interface VaccineTypeRepository extends JpaRepository<VaccineType, String> {

}
