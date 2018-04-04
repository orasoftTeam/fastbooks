package com.fastbooks.modelo;

import com.fastbooks.modelo.FbModulo;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-04-03T12:39:07")
@StaticMetamodel(FbMenu.class)
public class FbMenu_ { 

    public static volatile SingularAttribute<FbMenu, BigInteger> secuencia;
    public static volatile SingularAttribute<FbMenu, String> nombremenu;
    public static volatile SingularAttribute<FbMenu, FbModulo> idModulo;
    public static volatile SingularAttribute<FbMenu, String> opcion;
    public static volatile SingularAttribute<FbMenu, BigDecimal> idMenu;
    public static volatile SingularAttribute<FbMenu, Date> fechaCreacion;
    public static volatile SingularAttribute<FbMenu, String> estadomenu;
    public static volatile SingularAttribute<FbMenu, BigInteger> nivel;

}