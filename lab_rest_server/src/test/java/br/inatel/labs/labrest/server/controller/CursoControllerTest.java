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
	void dadoCurso_quandoPostCurso_entaoRespondeComCursoCriado() {
		Curso curso = new Curso(0L,"Manipulando arquivos json", 80);
	
		Curso cursoCriado = webTestClient.post()
				.uri("/curso/")
				.bodyValue(curso)
				.exchange().expectStatus().isCreated().expectBody(Curso.class)
				.returnResult().getResponseBody();
		
		assertNotNull(cursoCriado);
		assertNotNull(cursoCriado.getId());
		assertEquals(cursoCriado.getDescricao(), "Manipulando arquivos json");
		assertEquals(cursoCriado.getCargaHoraria(), 80);
		
	}
	
	@Test
	void dadoCursoInfo_quandoPutCursoInfo_entaoRespondeComStatusAccepted( ) {
		
        Curso curso_antes = webTestClient.get().uri("/curso/"+2L).exchange().expectStatus().isOk().expectBody(Curso.class)
				.returnResult().getResponseBody();
        
		Curso curso_para_atualizar = new Curso(2L, "Programação Java 11 em 2022", 40);
		
		webTestClient.put()
		.uri("/curso/")
		.bodyValue(curso_para_atualizar)
		.exchange().expectStatus().isAccepted().expectBody(Curso.class)
		.returnResult();
		
		Curso curso_depois = webTestClient.get().uri("/curso/"+2L).exchange().expectStatus().isOk().expectBody(Curso.class)
				.returnResult().getResponseBody();
		
		assertEquals(curso_antes.getId(), 2L);
		assertEquals(curso_antes.getDescricao(), "Programação Java 11");
		assertEquals(curso_antes.getCargaHoraria(), 80);
		
		assertEquals(curso_depois.getId(), 2L);
		assertEquals(curso_depois.getDescricao(), "Programação Java 11 em 2022");
		assertEquals(curso_depois.getCargaHoraria(), 40);
	}
	
	@Test
	void dadoCursoIdvalido_quadoDeleteCursoPeloId_entaoRespondeComStatusNoContent() {
		webTestClient.delete().uri("/curso/3").exchange().expectStatus().isNoContent();
		webTestClient.get().uri("/curso/"+ 3L).exchange().expectStatus().isNotFound();
		
	}
	
	@Test
	void dadoCursoIdinvalido_quadoDeleteCursoPeloId_entaoRespondeComStatusNotFound() {
		webTestClient.delete().uri("/curso/100").exchange().expectStatus().isNotFound();
		
	}
	
	
	
	
	
	
	
	
	
	
}
