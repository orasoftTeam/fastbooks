package com.fastbooks.modelo;

import com.fastbooks.modelo.FbDireccion;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-04-02T15:42:47")
@StaticMetamodel(FbCompania.class)
public class FbCompania_ { 

    public static volatile SingularAttribute<FbCompania, String> giro;
    public static volatile SingularAttribute<FbCompania, String> logo;
    public static volatile SingularAttribute<FbCompania, String> estado;
    public static volatile SingularAttribute<FbCompania, String> email;
    public static volatile SingularAttribute<FbCompania, String> website;
    public static volatile SingularAttribute<FbCompania, String> nomCom;
    public static volatile SingularAttribute<FbCompania, FbDireccion> idDireccion;
    public static volatile SingularAttribute<FbCompania, String> telefono;
    public static volatile SingularAttribute<FbCompania, Integer> perNat;
    public static volatile SingularAttribute<FbCompania, String> nomLeg;
    public static volatile SingularAttribute<FbCompania, BigDecimal> idCia;
    public static volatile SingularAttribute<FbCompania, Date> fechaCreacion;

}