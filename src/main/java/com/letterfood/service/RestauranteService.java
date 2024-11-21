package com.letterfood.service;

import com.letterfood.models.Avaliacao;
import com.letterfood.models.Restaurante;
import com.letterfood.repository.RestauranteRepository;

import java.util.Optional;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import com.mongodb.client.model.Filters;
import com.mongodb.client.gridfs.GridFSBucket;

import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

import org.bson.Document;
import org.bson.types.ObjectId;

public class RestauranteService {

    private final RestauranteRepository restauranteRepository;
    private final GridFSBucket gridFSBucket;
    private static final Logger logger = Logger.getLogger(RestauranteService.class.getName());

    public void adicionarRestaurante(Restaurante restaurante) {
        if (restaurante == null) {
            throw new IllegalArgumentException("Restaurante não pode ser nulo.");
        }
        restauranteRepository.save(restaurante);
    }

    // Método para listar todos os restaurantes
    public List<Restaurante> listarRestaurantes() {
        return restauranteRepository.findAll();
    }

    // Método para buscar restaurante por ID
    public Restaurante buscarRestaurantePorId(String restauranteId) {
        return restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RestauranteNotFoundException("Restaurante com ID " + restauranteId + " não encontrado."));
    }

    // Construtor com injeção de dependência
    public RestauranteService(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    // Construtor com injeção de dependência
    public RestauranteService(RestauranteRepository restauranteRepository, GridFSBucket gridFSBucket) {
        this.restauranteRepository = restauranteRepository;
        this.gridFSBucket = gridFSBucket;  // Inicializando o GridFSBucket
    }

    // Adicionar uma avaliação ao restaurante
    public void adicionarAvaliacao(String restauranteId, Avaliacao avaliacao) {
        if (restauranteId == null || restauranteId.isEmpty()) {
            logger.warning("O ID do restaurante está vazio ou nulo.");
            throw new IllegalArgumentException("ID do restaurante não pode ser vazio ou nulo.");
        }
        if (avaliacao == null) {
            logger.warning("A avaliação fornecida está nula.");
            throw new IllegalArgumentException("Avaliação não pode ser nula.");
        }

        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RestauranteNotFoundException("Restaurante com ID " + restauranteId + " não encontrado."));
        
        restaurante.getAvaliacoes().add(avaliacao);
        restauranteRepository.save(restaurante);
        
        logger.info("Avaliação adicionada com sucesso ao restaurante com ID " + restauranteId);
    }

    // Listar todas as avaliações de um restaurante
    public List<Avaliacao> listarAvaliacoes(String restauranteId) {
        if (restauranteId == null || restauranteId.isEmpty()) {
            logger.warning("O ID do restaurante está vazio ou nulo.");
            throw new IllegalArgumentException("ID do restaurante não pode ser vazio ou nulo.");
        }

        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RestauranteNotFoundException("Restaurante com ID " + restauranteId + " não encontrado."));
        
        logger.info("Listando avaliações para o restaurante com ID " + restauranteId);
        return restaurante.getAvaliacoes();
    }

    // Adicionar imagem ao restaurante
    public String adicionarImagemAoRestaurante(String restauranteId, InputStream imagemStream, String nomeImagem) {
        if (restauranteId == null || restauranteId.isEmpty()) {
            logger.warning("O ID do restaurante está vazio ou nulo.");
            throw new IllegalArgumentException("ID do restaurante não pode ser vazio ou nulo.");
        }

        if (imagemStream == null) {
            logger.warning("O stream da imagem está nulo.");
            throw new IllegalArgumentException("O stream da imagem não pode ser nulo.");
        }

        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RestauranteNotFoundException("Restaurante com ID " + restauranteId + " não encontrado."));

        // Criar uma opção de upload com o nome do arquivo
        GridFSUploadOptions options = new GridFSUploadOptions().metadata(new Document("type", "image"));

        // Upload da imagem para o GridFS
        ObjectId fileId = gridFSBucket.uploadFromStream(nomeImagem, imagemStream, options);
        
        // Associar o fileId (ID do GridFS) ao restaurante
        restaurante.setImagemId(fileId.toString());  // Você pode armazenar o ID da imagem no restaurante
        restauranteRepository.save(restaurante);
        
        logger.info("Imagem adicionada com sucesso ao restaurante com ID " + restauranteId);
        
        return fileId.toString();  // Retorna o ID do arquivo armazenado no GridFS
    }

    // Método para buscar imagem associada ao restaurante
    public InputStream buscarImagemDoRestaurante(String restauranteId) {
        if (restauranteId == null || restauranteId.isEmpty()) {
            logger.warning("O ID do restaurante está vazio ou nulo.");
            throw new IllegalArgumentException("ID do restaurante não pode ser vazio ou nulo.");
        }

        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RestauranteNotFoundException("Restaurante com ID " + restauranteId + " não encontrado."));
        
        String imagemId = restaurante.getImagemId();
        if (imagemId == null) {
            throw new RestauranteNotFoundException("Nenhuma imagem associada ao restaurante com ID " + restauranteId);
        }

        ObjectId fileId = new ObjectId(imagemId);
        GridFSFile gridFSFile = gridFSBucket.find(Filters.eq("_id", fileId)).first();
        
        if (gridFSFile == null) {
            throw new RestauranteNotFoundException("Imagem não encontrada no GridFS.");
        }

        return gridFSBucket.openDownloadStream(gridFSFile.getObjectId()); // Retorna o InputStream da imagem
    }

    // Método para atualizar a descrição do restaurante
    public void atualizarDescricaoRestaurante(String restauranteId, String descricao) {
        if (restauranteId == null || restauranteId.isEmpty()) {
            logger.warning("O ID do restaurante está vazio ou nulo.");
            throw new IllegalArgumentException("ID do restaurante não pode ser vazio ou nulo.");
        }

        if (descricao == null || descricao.isEmpty()) {
            logger.warning("A descrição fornecida está vazia ou nula.");
            throw new IllegalArgumentException("Descrição não pode ser vazia ou nula.");
        }

        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RestauranteNotFoundException("Restaurante com ID " + restauranteId + " não encontrado."));

        restaurante.setDescricao(descricao); // Atualizando a descrição
        restauranteRepository.save(restaurante);

        logger.info("Descrição atualizada com sucesso para o restaurante com ID " + restauranteId);
    }

    // Exceção personalizada para restaurante não encontrado
    public static class RestauranteNotFoundException extends RuntimeException {
        public RestauranteNotFoundException(String message) {
            super(message);
        }
    }

    public String adicionarImagemAoRestaurante(String restauranteId, InputStream imagemStream, String nomeImagem) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'adicionarImagemAoRestaurante'");
    }

    public InputStream buscarImagemDoRestaurante(String restauranteId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarImagemDoRestaurante'");
    }
}
