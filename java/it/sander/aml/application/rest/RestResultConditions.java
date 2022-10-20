package it.sander.aml.application.rest;

import org.springframework.http.HttpStatus;

import it.sander.aml.domain.model.PaginationResponse;
import it.sander.aml.infrastructure.ResourceNotFoundException;

/**
 * Simple static methods to be called at the start of your own methods to verify correct arguments and state. If the Precondition fails, an {@link HttpStatus} code is thrown
 */
public final class RestResultConditions {

    private RestResultConditions() {
        throw new AssertionError();
    }

    // API

    /**
     * Check if some value was found, otherwise throw exception.
     * 
     * @param expression
     *            has value true if found, otherwise false
     * @throws MyResourceNotFoundException
     *             if expression is false, means value not found.
     */
    public static void checkFound(final boolean expression) {
        if (!expression) {
            throw new ResourceNotFoundException();
        }
    }

    /**
     * Check if some value was found, otherwise throw exception.
     * 
     * @param expression
     *            has value true if found, otherwise false
     * @throws MyResourceNotFoundException
     *             if expression is false, means value not found.
     */
    public static <T> T checkFound(final T resource) {
        if (resource == null) {
            throw new ResourceNotFoundException();
        }

        return resource;
    }
    
    /**
     * Check if some value was found, otherwise throw exception.
     * 
     * @param expression
     *            has value true if found, otherwise false
     * @throws MyResourceNotFoundException
     *             if expression is false, means value not found.
     */
    public static <T> PaginationResponse<T> checkFound(final PaginationResponse<T> paginatedResult) {
        if(paginatedResult.isCountRequest() && paginatedResult.getTotalElements()==0 ) {
            throw new ResourceNotFoundException();
        }

        return paginatedResult;
    }

}
