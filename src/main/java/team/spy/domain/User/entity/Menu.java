package team.spy.domain.User.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "T_MENU")
public class Menu {

    @Id
    private int idx;

    @ManyToOne
    @JoinColumn(name = "MENU_ID")
    private MenuPermission menuPermission;

    private String menuName;

    private LocalDate registerDate;

    private LocalDate updateDate;

}
