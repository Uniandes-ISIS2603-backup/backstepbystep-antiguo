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
package co.edu.uniandes.csw.bookstore.dtos;

import co.edu.uniandes.csw.bookstore.entities.BookEntity;

/**
 * Clase que extiende de {@link BookDTO} para manejar las relaciones entre
 * los Book JSON y otros DTOs. Para conocer el
 * contenido de la un Libro vaya a la documentacion de {@link BookDTO}
 * 
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *      "name: string,
 *      "isbn": string,
 *      "image: string,
 *      "description": string,
 *      "publishingdate": string,
 *      "editorial": {@link EditorialDTO}
 *   }
 * </pre>
 * Por ejemplo una editorial se representa asi:<br>
 * 
 * <pre>
 * 
 *   {
 *      "id": 123,
 *      "name: "Historia de los hombres lobo",
 *      "isbn": "930330149-8",
 *      "image: "https://static.iris.net.co/arcadia/upload/images/2017/7/31/64899_1.jpg",
 *      "description": "Jorge Fondebrider traza un mundo fantástico con mapas de la geografía real y de sus mitologías, observando a los hombres lobo que han vivido en la imaginación de Europa y América.",
 *      "publishingdate": "2000-08-20T00:00:00-05:00",
 *      "editorial": {"id" : 1, "name" : "Plaza y Janes"}
 *   }
 *
 * </pre>
 * @author ISIS2603
 */
public class BookDetailDTO extends BookDTO {

    /*
    * Relación a una editorial
     */
    private EditorialDTO editorial;

    public BookDetailDTO() {
        super();
    }

    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param entity La entidad de la cual se construye el DTO
     */
    public BookDetailDTO(BookEntity entity) {
        super(entity);
        if (entity.getEditorial() != null) {
            this.editorial = new EditorialDTO(entity.getEditorial());
        } else {
            entity.setEditorial(null);
        }
    }

    /**
     * Transformar el DTO a una entidad
     * @return La entidad que representa el libro.
     */
    @Override
    public BookEntity toEntity() {
        BookEntity bookE = super.toEntity();
        if (this.getEditorial() != null) {
            bookE.setEditorial(this.getEditorial().toEntity());
        }
        return bookE;
    }

    /**
     * Devuelve la editorial asociada a este libro.
     * @return the editorial
     */
    public EditorialDTO getEditorial() {
        return editorial;
    }

    /**
     * Modifica la editorial asociada a este libro.
     * @param editorial the editorial to set
     */
    public void setEditorial(EditorialDTO editorial) {
        this.editorial = editorial;
    }
}
