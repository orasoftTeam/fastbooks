package com.fastbooks.modelo;

import com.fastbooks.modelo.FbDireccion;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-12T09:57:26")
@StaticMetamodel(FbPais.class)
public class FbPais_ { 

    public static volatile SingularAttribute<FbPais, String> nombre;
    public static volatile SingularAttribute<FbPais, BigDecimal> idPais;
    public static volatile SingularAttribute<FbPais, Date> fechaCreacion;
    public static volatile ListAttribute<FbPais, FbDireccion> fbDireccionList;

}