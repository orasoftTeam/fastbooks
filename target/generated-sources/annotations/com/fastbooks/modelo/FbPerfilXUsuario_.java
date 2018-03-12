package com.fastbooks.modelo;

import com.fastbooks.modelo.FbCompania;
import com.fastbooks.modelo.FbPerfilXUsuarioPK;
import com.fastbooks.modelo.FbPerfiles;
import com.fastbooks.modelo.FbUsuario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-12T16:35:36")
@StaticMetamodel(FbPerfilXUsuario.class)
public class FbPerfilXUsuario_ { 

    public static volatile SingularAttribute<FbPerfilXUsuario, FbUsuario> fbUsuario;
    public static volatile SingularAttribute<FbPerfilXUsuario, FbPerfiles> fbPerfiles;
    public static volatile SingularAttribute<FbPerfilXUsuario, String> estado;
    public static volatile SingularAttribute<FbPerfilXUsuario, FbPerfilXUsuarioPK> fbPerfilXUsuarioPK;
    public static volatile SingularAttribute<FbPerfilXUsuario, Date> fechaCreacion;
    public static volatile SingularAttribute<FbPerfilXUsuario, FbCompania> fbCompania;

}