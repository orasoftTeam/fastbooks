package com.fastbooks.modelo;

import com.fastbooks.modelo.FbModulo;
import com.fastbooks.modelo.FbModuloXPerfilPK;
import com.fastbooks.modelo.FbPerfiles;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-24T23:49:37")
@StaticMetamodel(FbModuloXPerfil.class)
public class FbModuloXPerfil_ { 

    public static volatile SingularAttribute<FbModuloXPerfil, String> estado;
    public static volatile SingularAttribute<FbModuloXPerfil, FbPerfiles> fbPerfiles;
    public static volatile SingularAttribute<FbModuloXPerfil, Date> fechaCreacion;
    public static volatile SingularAttribute<FbModuloXPerfil, FbModulo> fbModulo;
    public static volatile SingularAttribute<FbModuloXPerfil, FbModuloXPerfilPK> fbModuloXPerfilPK;

}