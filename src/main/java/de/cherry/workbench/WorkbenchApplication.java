package de.cherry.workbench;

import de.cherry.workbench.meta.That;
import de.cherry.workbench.meta.domain.Project;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WorkbenchApplication {
	public static void main(String[] args) {
		That that = That.getInstance();
		Project current = that.get();
		that.set(current);
		SpringApplication.run(WorkbenchApplication.class, args);
	}
}
