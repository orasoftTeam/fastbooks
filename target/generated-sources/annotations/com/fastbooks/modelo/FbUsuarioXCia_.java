package com.fastbooks.modelo;

import com.fastbooks.modelo.FbCompania;
import com.fastbooks.modelo.FbUsuario;
import com.fastbooks.modelo.FbUsuarioXCiaPK;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-12T16:35:36")
@StaticMetamodel(FbUsuarioXCia.class)
public class FbUsuarioXCia_ { 

    public static volatile SingularAttribute<FbUsuarioXCia, FbUsuario> fbUsuario;
    public static volatile SingularAttribute<FbUsuarioXCia, FbUsuarioXCiaPK> fbUsuarioXCiaPK;
    public static volatile SingularAttribute<FbUsuarioXCia, String> estado;
    public static volatile SingularAttribute<FbUsuarioXCia, Date> fechaCreacion;
    public static volatile SingularAttribute<FbUsuarioXCia, FbCompania> fbCompania;

}