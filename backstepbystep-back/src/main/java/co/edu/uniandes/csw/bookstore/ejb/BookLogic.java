/*
MIT License

Copyright (c) 2017 Universidad de los Andes - ISIS2603

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package co.edu.uniandes.csw.bookstore.ejb;

import co.edu.uniandes.csw.bookstore.entities.BookEntity;
import co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bookstore.persistence.BookPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de Book.
 * @author ISIS2603
 */
@Stateless
public class BookLogic {

    private static final Logger LOGGER = Logger.getLogger(BookLogic.class.getName());


    @Inject
    private BookPersistence persistence;

    /**
     * Devuelve todos los libros que hay en la base de datos.
     * @return Lista de entidades de tipo libro.
     */
    public List<BookEntity> getBooks() {
        LOGGER.info("Inicia proceso de consultar todos los libros");
        List<BookEntity> books = persistence.findAll();
        LOGGER.info("Termina proceso de consultar todos los libros");
        return books;
    }

    /**
     * Busca un libro por ID
     * @param id El id del libro a buscar
     * @return El libro encontrado, null si no lo encuentra.
     */
    public BookEntity getBook(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar libro con id={0}", id);
        BookEntity book = persistence.find(id);
        if (book == null) {
            LOGGER.log(Level.SEVERE, "El libro con el id {0} no existe", id);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar libro con id={0}", id);
        return book;
    }

    /**
     * Guardar un nuevo libro
     * @param entity La entidad de tipo libro del nuevo libro a persistir.
     * @return La entidad luego de persistirla
     * @throws BusinessLogicException Si el ISBN ya existe en la persitencia.
     */
    public BookEntity createBook(BookEntity entity) throws BusinessLogicException {
        LOGGER.info("Inicia proceso de creación de libro");
        if (!validateISBN(entity.getIsbn())) {
            throw new BusinessLogicException("El ISBN es inválido");
        }
        persistence.create(entity);
        LOGGER.info("Termina proceso de creación de libro");
        return entity;
    }

    /**
     * Actualizar un libro por ID
     * @param id El ID del libro a actualizar
     * @param entity La entidad del libro con los cambios deseados
     * @return La entidad del libro luego de actualizarla
     * @throws BusinessLogicException Si el IBN de la actualización es inválido
     */
    public BookEntity updateBook(Long id, BookEntity entity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar libro con id={0}", id);
        if (!validateISBN(entity.getIsbn())) {
            throw new BusinessLogicException("El ISBN es inválido");
        }
        BookEntity newEntity = persistence.update(entity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar libro con id={0}", entity.getId());
        return newEntity;
    }

    /**
     * Eliminar un libro por ID
     * @param id El ID del libro a eliminar
     */
    public void deleteBook(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar libro con id={0}", id);
        persistence.delete(id);
        LOGGER.log(Level.INFO, "Termina proceso de borrar libro con id={0}", id);
    }

    private boolean validateISBN(String isbn) {
        if (isbn == null || isbn.isEmpty()) {
            return false;
        }
        return true;
    }

}
