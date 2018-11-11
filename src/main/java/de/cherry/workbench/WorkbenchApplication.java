package de.cherry.workbench;

import de.cherry.workbench.sapui.model.Counter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WorkbenchApplication {

	static Counter count = Counter.getInstance();

	public static void main(String[] args) {
		SpringApplication.run(WorkbenchApplication.class, args);
	}
}
