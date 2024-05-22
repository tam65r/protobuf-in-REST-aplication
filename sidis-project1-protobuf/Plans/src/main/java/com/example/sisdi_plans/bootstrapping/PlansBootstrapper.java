package com.example.sisdi_plans.bootstrapping;


import com.example.sisdi_plans.planmanagement.model.MusicSuggestions;
import com.example.sisdi_plans.planmanagement.model.PlanJPA;
import com.example.sisdi_plans.planmanagement.repositories.PlanDBRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("bootstrap")
public class PlansBootstrapper implements CommandLineRunner {

	@Value("${server.port}")
	private int serverPort;


	private final PlanDBRepository repository;

	@Override
	public void run(String... args) throws Exception {
		if (serverPort == 8080) {
			if (repository.findByName("Free").isEmpty()) {
				final PlanJPA p1 = new PlanJPA("Free", "Free Plan",1000, MusicSuggestions.AUTOMATIC,1000,0,0);
				repository.save(p1);
			}
			if (repository.findByName("Silver").isEmpty()) {
				final PlanJPA p2 = new PlanJPA("Silver", "Silver Plan",1000, MusicSuggestions.AUTOMATIC,5000,(float) 4.99,(float) 49.90);
				repository.save(p2);
			}
			if (repository.findByName("Gold").isEmpty()) {
				final PlanJPA p3 = new PlanJPA("Gold", "Gold Plan",1000, MusicSuggestions.PERSONALIZED,10000,(float) 5.99,(float) 59.90);
				repository.save(p3);
			}
			if (repository.findByName("Platinum").isEmpty()) {
				final PlanJPA p4 = new PlanJPA("Platinum", "Platinum Plan",1000, MusicSuggestions.PERSONALIZED,12500,(float) 7.99,(float) 79.90);
				repository.save(p4);
			}
		}
		if (serverPort == 8085) {
			if (repository.findByName("Diamond").isEmpty()) {
				final PlanJPA p1 = new PlanJPA("Diamond", "Diamond Plan",10000, MusicSuggestions.AUTOMATIC,1000,(float) 15.99,(float) 159.90);
				repository.save(p1);
			}
		}
	}
}
