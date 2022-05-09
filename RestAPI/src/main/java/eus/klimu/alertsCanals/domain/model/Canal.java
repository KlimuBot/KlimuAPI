package eus.klimu.canals.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Canal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long canalID;
    @Column(unique = true)
    private String nombre;
}