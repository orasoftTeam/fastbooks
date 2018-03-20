package com.fastbooks.modelo;

import com.fastbooks.modelo.FbDireccion;
import com.fastbooks.modelo.FbPerfilXUsuario;
import com.fastbooks.modelo.FbUsuarioXCia;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-19T16:27:16")
@StaticMetamodel(FbCompania.class)
public class FbCompania_ { 

    public static volatile SingularAttribute<FbCompania, String> giro;
    public static volatile SingularAttribute<FbCompania, String> logo;
    public static volatile SingularAttribute<FbCompania, String> estado;
    public static volatile SingularAttribute<FbCompania, String> email;
    public static volatile SingularAttribute<FbCompania, String> website;
    public static volatile SingularAttribute<FbCompania, String> nomCom;
    public static volatile ListAttribute<FbCompania, FbPerfilXUsuario> fbPerfilXUsuarioList;
    public static volatile SingularAttribute<FbCompania, FbDireccion> idDireccion;
    public static volatile SingularAttribute<FbCompania, Integer> perNat;
    public static volatile SingularAttribute<FbCompania, String> telefono;
    public static volatile SingularAttribute<FbCompania, String> nomLeg;
    public static volatile SingularAttribute<FbCompania, BigDecimal> idCia;
    public static volatile SingularAttribute<FbCompania, Date> fechaCreacion;
    public static volatile ListAttribute<FbCompania, FbUsuarioXCia> fbUsuarioXCiaList;

}