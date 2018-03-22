package com.fastbooks.modelo;

import com.fastbooks.modelo.FbEstado;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-22T09:00:15")
@StaticMetamodel(FbPais.class)
public class FbPais_ { 

    public static volatile SingularAttribute<FbPais, BigDecimal> idPais;
    public static volatile ListAttribute<FbPais, FbEstado> fbEstadoList;
    public static volatile SingularAttribute<FbPais, Date> fechaCreacion;
    public static volatile SingularAttribute<FbPais, String> nombre;

}