package sqltutorial.evms_api.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import sqltutorial.evms_api.model.VaccineType;
import sqltutorial.evms_api.repository.VaccineTypeRepository;

@RestController
@RequestMapping("/api/evms")
@Validated
public class VaccineTypeController {
	private static final Logger log = LoggerFactory.getLogger(VaccineTypeController.class);

	@Autowired
	VaccineTypeRepository vaccineTypeRepository;
	
	@GetMapping("/vaccine_types")
	public List<VaccineType> all(UsernamePasswordAuthenticationToken authentication) {	
		log.info("Getting all vaccine types");

		return vaccineTypeRepository.findAll();		
	}
	
	@GetMapping("/vaccine_types/{name}")
	public VaccineType get(@PathVariable("name") String name) {
		return vaccineTypeRepository.findById(name).get();
	}

	@PostMapping("/vaccine_types/add")
	@ResponseStatus(HttpStatus.CREATED)
	public VaccineType add(UsernamePasswordAuthenticationToken authentication, @RequestBody VaccineType vaccineType) {
		User user = (User) authentication.getPrincipal();
		String userName = user.getUsername();
        String role = authentication.getAuthorities().iterator().next().getAuthority();
		System.out.println(userName + ": " + role);		
		
		Date currentDate = new Date();
		vaccineType.setCreatedBy(userName);
		vaccineType.setCreatedDate(currentDate);
		vaccineType.setLastUpdatedBy(userName);
		vaccineType.setLastUpdatedDate(currentDate);
		vaccineType = vaccineTypeRepository.save(vaccineType);

		return vaccineType;
	}

	@PutMapping("/vaccine_types/edit")
	public VaccineType edit(UsernamePasswordAuthenticationToken authentication, @RequestBody VaccineType vaccineType) {
		VaccineType updatedVaccineType = vaccineTypeRepository.findById(vaccineType.getName()).get();
		
		updatedVaccineType.setLastUpdatedBy(((User) authentication.getPrincipal()).getUsername());
		updatedVaccineType.setLastUpdatedDate(new Date());				
		updatedVaccineType.setDescription(vaccineType.getDescription());
		vaccineType = vaccineTypeRepository.save(updatedVaccineType);

		return vaccineType;
	}

	@DeleteMapping("/vaccine_types/delete/{name}")
	public VaccineType delete(@PathVariable("name") String name) {
		VaccineType deletedVaccineType = vaccineTypeRepository.findById(name).get();
		vaccineTypeRepository.delete(vaccineTypeRepository.findById(name).get());
		return deletedVaccineType;
	}	
}
