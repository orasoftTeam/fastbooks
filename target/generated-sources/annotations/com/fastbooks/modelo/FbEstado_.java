package com.fastbooks.modelo;

import com.fastbooks.modelo.FbDireccion;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-12T16:35:36")
@StaticMetamodel(FbEstado.class)
public class FbEstado_ { 

    public static volatile SingularAttribute<FbEstado, String> nombre;
    public static volatile SingularAttribute<FbEstado, BigDecimal> idEstado;
    public static volatile SingularAttribute<FbEstado, Date> fechaCreacion;
    public static volatile ListAttribute<FbEstado, FbDireccion> fbDireccionList;

}