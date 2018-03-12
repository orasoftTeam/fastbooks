package com.fastbooks.modelo;

import com.fastbooks.modelo.FbMenu;
import com.fastbooks.modelo.FbModuloXPerfil;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-12T09:57:26")
@StaticMetamodel(FbModulo.class)
public class FbModulo_ { 

    public static volatile SingularAttribute<FbModulo, String> estadomodulo;
    public static volatile SingularAttribute<FbModulo, BigDecimal> idModulo;
    public static volatile ListAttribute<FbModulo, FbModuloXPerfil> fbModuloXPerfilList;
    public static volatile SingularAttribute<FbModulo, String> nombremodulo;
    public static volatile SingularAttribute<FbModulo, Date> fechaCreacion;
    public static volatile ListAttribute<FbModulo, FbMenu> fbMenuList;

}