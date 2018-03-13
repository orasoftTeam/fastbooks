package com.fastbooks.modelo;

import com.fastbooks.modelo.FbCiudad;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-13T16:53:40")
@StaticMetamodel(FbDireccion.class)
public class FbDireccion_ { 

    public static volatile SingularAttribute<FbDireccion, String> urbanizacion;
    public static volatile SingularAttribute<FbDireccion, BigDecimal> idDireccion;
    public static volatile SingularAttribute<FbDireccion, String> apartamento;
    public static volatile SingularAttribute<FbDireccion, FbCiudad> idCiudad;
    public static volatile SingularAttribute<FbDireccion, String> piso;
    public static volatile SingularAttribute<FbDireccion, Date> fechaCreacion;
    public static volatile SingularAttribute<FbDireccion, String> avenida;

}