package com.work.crud.util;

import lombok.Getter;

/**
 *
 * @author linux
 */
public enum Keys {
    PRODUCTS("products");

    @Getter
    private final String clave;

    private Keys(String clave) {
        this.clave = clave;
    }

}
