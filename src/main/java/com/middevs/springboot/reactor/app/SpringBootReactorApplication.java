package com.middevs.springboot.reactor.app;

import com.middevs.springboot.reactor.app.models.Comentario;
import com.middevs.springboot.reactor.app.models.Usuario;
import com.middevs.springboot.reactor.app.models.UsuarioComentario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(SpringBootReactorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		///Flux<String> nombres = Flux.just("Andres","Juan")
		///		.doOnNext(element -> System.out.println(element));
///
		///nombres.subscribe();
		exampleUsuarioComentarioZipWith();

	}


	public void exampleUsuarioComentarioZipWith(){

		Mono<Usuario> usuarioMono = Mono.fromCallable(()-> new Usuario("Jhon", "Cena"));
		Mono<Comentario> comentariosMono = Mono.fromCallable(()-> {
			Comentario comentarios = new Comentario();
			comentarios.addComentario("hey1!!");
			comentarios.addComentario("hey2!!");
			comentarios.addComentario("hey3!!");
			return comentarios;
		});

		Mono<UsuarioComentario> usuarioComentarioMono = usuarioMono.zipWith(comentariosMono,(usuario, comentarios)-> new UsuarioComentario(usuario, comentarios));
		usuarioComentarioMono.subscribe(uc -> log.info(uc.toString()));
	}

    public void exampleFlatMapTwo(){

        Mono<Usuario> usuarioMono = Mono.fromCallable(()-> new Usuario("Jhon", "Cena"));
        Mono<Comentario> comentariosMono = Mono.fromCallable(()-> {
            Comentario comentarios = new Comentario();
            comentarios.addComentario("hey1!!");
            comentarios.addComentario("hey2!!");
            comentarios.addComentario("hey3!!");
            return comentarios;
        });

        usuarioMono.flatMap( u -> comentariosMono.map(c -> new UsuarioComentario(u,c)))
                .subscribe(uc -> log.info(uc.toString()));


    }


	public void exampleConvertToString(){


		List<Usuario> usersList = new ArrayList<>();
		usersList.add( new Usuario ("Rodrigo"," Fernández"));
		usersList.add(new Usuario ("Diego"," Kumdrieck"));
		usersList.add(new Usuario ("Jorge"," Baca"));
		usersList.add(new Usuario ("Stev"," Huaman"));
		usersList.add(new Usuario ("Jorge"," Sanchez"));

		Flux.fromIterable(usersList)
				.map(usuario -> usuario.getNombre().toUpperCase().concat(usuario.getApellido().toUpperCase()))
				.flatMap(nombre -> {
					if (nombre.contains("JORGE")) {
						return Mono.just(nombre);
					}else {
						return Mono.empty();
					}
				})
				.map(nombre -> {
					return nombre.toLowerCase();
				})
				.subscribe(nombre -> log.info(nombre));

	}


	public void exampleFlatMap(){


		List<String> usersList = new ArrayList<>();
		usersList.add("Rodrigo Fernández");
		usersList.add("Diego Kumdrieck");
		usersList.add("Jorge Baca");
		usersList.add("Stev Huaman");
		usersList.add("Jorge Sanchez");

		Flux.fromIterable(usersList)
				.map(nombre -> new Usuario(nombre.split(" ")[0].toUpperCase(),nombre.split(" ")[1].toUpperCase()))
				//.filter(usuario -> usuario.getNombre().equalsIgnoreCase("jorge"))
				.flatMap(usuario -> {
					if (usuario.getNombre().equalsIgnoreCase("jorge")) {
						return Mono.just(usuario);
					}else {
						return Mono.empty();
					}
				})
				.map(usuario -> {
					String nombre = usuario.getNombre().toLowerCase();
					usuario.setNombre(nombre);
					return usuario;
				})
				.subscribe(u -> log.info(u.toString()));

	}


}
