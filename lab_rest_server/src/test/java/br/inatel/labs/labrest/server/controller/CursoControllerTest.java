package br.inatel.labs.labrest.server.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.junit.jupiter.api.Assertions.*;
import br.inatel.labs.labrest.server.model.Curso;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CursoControllerTest {
	
	@Autowired
	private WebTestClient webTestClient;

	@Test
	void deveListarCursos() {
		webTestClient.get().uri("/curso").exchange().expectStatus().isOk().expectBody().returnResult();
	}
	
	@Test
	void dadoCursoIdValido_quandoGetCursoPeloId_entaoRespondeComCursoValido() {
		Long cursoIdValido = 1L;
		
		Curso cursoRespondido = webTestClient.get().uri("/curso/"+cursoIdValido).exchange().expectStatus().isOk().expectBody(Curso.class)
				.returnResult().getResponseBody();
		
		assertNotNull(cursoRespondido);
		assertEquals(cursoRespondido.getId(), cursoIdValido);
	} 

	@Test
	void dadoCursoIdInvalido_quandoGetCursoPeloId_entaoRespondeComStatusNotFound() {
		Long cursoIdInvalido = 100L;
		 webTestClient.get().uri("/curso/"+ cursoIdInvalido).exchange().expectStatus().isNotFound();
		
	}
	
	@Test
	void dadoNovoCurso_quandoPostCurso_entaoRespondeComStatusCreatedCursoValido() {
		Curso novocurso = new Curso();
		novocurso.setDescricao("Manipulando arquivos json");
		novocurso.setCargaHoraria(80);
		
		Curso cursoCriado = webTestClient.post()
				.uri("/curso")
				.bodyValue(novocurso)
				.exchange().expectStatus().isCreated().expectBody(Curso.class)
				.returnResult().getResponseBody();
		
		assertNotNull(cursoCriado);
		assertNotNull(cursoCriado.getId());
		assertEquals(cursoCriado.getDescricao(), "Manipulando arquivos json");
		assertEquals(cursoCriado.getCargaHoraria(), 80);
		
	}
	
	@Test
	void dadoCursoExistente_quandoPutCurso_entaoRespondeComStatusAccepted( ) {
		
		Curso cursoExistente = new Curso();
		cursoExistente.setId(2L);
		cursoExistente.setDescricao("Programação Java 11 em 2022");
		cursoExistente.setCargaHoraria(40);
		
		
		webTestClient.put()
		.uri("/curso")
		.bodyValue(cursoExistente)
		.exchange().expectStatus().isAccepted().expectBody().isEmpty();
		
	}
	
	@Test
	void dadoCursoIdvalido_quadoDeleteCursoPeloId_entaoRespondeComStatusNoContentECorpoVazio() {
		Long cursoIdValido = 3L;
		webTestClient.delete().uri("/curso/"+cursoIdValido).exchange().expectStatus().isNoContent().expectBody().isEmpty();
		
	}
	
	@Test
	void dadoCursoIdinvalido_quadoDeleteCursoPeloId_entaoRespondeComStatusNotFound() {
		Long cursoIdInvalido = 100L;
		webTestClient.delete().uri("/curso/"+cursoIdInvalido).exchange().expectStatus().isNotFound();
		
	}
	
	
}
