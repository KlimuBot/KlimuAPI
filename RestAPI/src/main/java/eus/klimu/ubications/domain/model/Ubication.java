package eus.klimu.ubications.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ubication {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userID;
    private Long localID;
    @Column(unique = true)
    private String recibirTiempo;
    private LocalTime hora;
}