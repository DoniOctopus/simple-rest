package com.enigma.resttemplate.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "m_post")
@Builder
public class Posts {
    @Id
    private Integer id;
    private String title;
    private String body;
    private Integer userId;
}
