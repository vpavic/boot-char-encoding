package sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class BootCharEncodingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootCharEncodingApplication.class, args);
	}

	@GetMapping(params = { "first", "second" })
	public String getWithParams() {
		return "getWithParams";
	}

	@PostMapping(path = "/{pv}/post")
	public String handlePost(@PathVariable("pv") String pv, Form form) {
		return "Hello " + form.getName();
	}

	public class Form {

		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

}
