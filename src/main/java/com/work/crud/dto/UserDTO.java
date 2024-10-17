/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.work.crud.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 *
 * @author linux
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements java.io.Serializable {

    private String id;
    private String name;
    private String email;
}
