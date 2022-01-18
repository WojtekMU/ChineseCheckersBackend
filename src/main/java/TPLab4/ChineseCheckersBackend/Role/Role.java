package TPLab4.ChineseCheckersBackend.Role;

import javax.persistence.*;

/**
 * Class representing user roles.
 */
@Entity
public class Role {
    /**
     * Role id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Role name.
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    /**
     * Role empty constructor.
     */
    public Role() {
    }

    /**
     * Role constructor.
     *
     * @param name Role name
     */
    public Role(ERole name) {
        this.name = name;
    }

    /**
     * Role id getter.
     *
     * @return Role id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Role id setter.
     *
     * @param id Long value
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Role name getter.
     *
     * @return Role name
     */
    public ERole getName() {
        return name;
    }

    /**
     * Role name setter
     *
     * @param name Name
     */
    public void setName(ERole name) {
        this.name = name;
    }
}