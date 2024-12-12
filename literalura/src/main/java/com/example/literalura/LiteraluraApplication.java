package com.example.literalura;

import com.example.literalura.principal.Pricipal;
import com.example.literalura.repository.AutorRepository;
import com.example.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {
	@Autowired
	private AutorRepository autorRepositorio;
	@Autowired
	private LibroRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Pricipal principal1 = new Pricipal(repository, autorRepositorio);
		principal1.muestraElMenu();
	}
}
