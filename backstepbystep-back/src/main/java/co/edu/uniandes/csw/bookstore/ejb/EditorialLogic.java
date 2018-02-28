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

import co.edu.uniandes.csw.bookstore.entities.EditorialEntity;
import co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bookstore.persistence.EditorialPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de Editorial.
 * @author ISIS2603
 */
@Stateless
public class EditorialLogic {

    private static final Logger LOGGER = Logger.getLogger(EditorialLogic.class.getName());

    @Inject
    private EditorialPersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    /**
     * Crea una editorial en la persistencia.
     * @param entity La entidad que representa la editorial a persistir.
     * @return La entiddad de la editorial luego de persistirla.
     * @throws BusinessLogicException Si la editorial a persistir ya existe.
     */
    public EditorialEntity createEditorial(EditorialEntity entity) throws BusinessLogicException {
        LOGGER.info("Inicia proceso de creación de editorial");
        // Verifica la regla de negocio que dice que no puede haber dos editoriales con el mismo nombre
        if (persistence.findByName(entity.getName()) != null) {
            throw new BusinessLogicException("Ya existe una Editorial con el nombre \"" + entity.getName() + "\"");
        }
        // Invoca la persistencia para crear la editorial
        persistence.create(entity);
        LOGGER.info("Termina proceso de creación de editorial");
        return entity;
    }

    /**
     * 
     * Obtener todas las editoriales existentes en la base de datos.
     *
     * @return una lista de editoriales.
     */
    public List<EditorialEntity> getEditorials() {
        LOGGER.info("Inicia proceso de consultar todas las editoriales");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        List<EditorialEntity> editorials = persistence.findAll();
        LOGGER.info("Termina proceso de consultar todas las editoriales");
        return editorials;
    }

    /**
     *
     * Obtener una editorial por medio de su id.
     * 
     * @param id: id de la editorial para ser buscada.
     * @return la editorial solicitada por medio de su id.
     */
    public EditorialEntity getEditorial(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar editorial con id={0}", id);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        EditorialEntity editorial = persistence.find(id);
        if (editorial == null) {
            LOGGER.log(Level.SEVERE, "La editorial con el id {0} no existe", id);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar editorial con id={0}", id);
        return editorial;
    }

    /**
     *
     * Actualizar una editorial.
     *
     * @param id: id de la editorial para buscarla en la base de datos.
     * @param entity: editorial con los cambios para ser actualizada, por
     * ejemplo el nombre.
     * @return la editorial con los cambios actualizados en la base de datos.
     */
    public EditorialEntity updateEditorial(Long id, EditorialEntity entity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar editorial con id={0}", id);
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        EditorialEntity newEntity = persistence.update(entity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar editorial con id={0}", entity.getId());
        return newEntity;
    }

    /**
     * Borrar un editorial
     *
     * @param id: id de la editorial a borrar
     */
    public void deleteEditorial(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar editorial con id={0}", id);
        // Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
        persistence.delete(id);
        LOGGER.log(Level.INFO, "Termina proceso de borrar editorial con id={0}", id);
    }

}
