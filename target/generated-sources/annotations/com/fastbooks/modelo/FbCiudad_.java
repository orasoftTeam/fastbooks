package com.fastbooks.modelo;

import com.fastbooks.modelo.FbDireccion;
import com.fastbooks.modelo.FbEstado;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-13T13:41:43")
@StaticMetamodel(FbCiudad.class)
public class FbCiudad_ { 

    public static volatile SingularAttribute<FbCiudad, String> nombre;
    public static volatile SingularAttribute<FbCiudad, BigDecimal> idCiudad;
    public static volatile SingularAttribute<FbCiudad, FbEstado> idEstado;
    public static volatile SingularAttribute<FbCiudad, Date> fechaCreacion;
    public static volatile ListAttribute<FbCiudad, FbDireccion> fbDireccionList;

}