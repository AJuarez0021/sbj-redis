package com.work.crud.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 *
 * @author linux
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO implements java.io.Serializable{

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private Double monto;

}
