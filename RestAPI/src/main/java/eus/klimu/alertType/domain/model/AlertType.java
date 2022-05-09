package eus.klimu.alertType.domain.model;

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
public class AlertType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long alertTypeID;
    @Column(unique = true)
    private String nombre;
    private String recomendacion;
}