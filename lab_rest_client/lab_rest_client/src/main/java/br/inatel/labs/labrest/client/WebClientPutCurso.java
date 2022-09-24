package br.inatel.labs.labrest.client;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import br.inatel.labs.labrest.client.model.Curso;

public class WebClientPutCurso {
	public static void main(String[] args) {
		Curso cursoExistente  = new Curso(1L, "REST com Spring Boot e Spring WebFlux", 120);
		ResponseEntity<Void> responseEntity = WebClient.create("http://localhost:8080")
				.put()
				.uri("/curso")
				.bodyValue(cursoExistente)
				.retrieve()
				.toBodilessEntity()
				.block();
		
		HttpStatus statusCode = responseEntity.getStatusCode();
		
		System.out.println("Curso atualizado. Status da resposta: "+ statusCode);
		
	}

}
