package com.fastbooks.modelo;

import com.fastbooks.modelo.FbUsuario;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-13T15:47:16")
@StaticMetamodel(FbUsuarioPref.class)
public class FbUsuarioPref_ { 

    public static volatile SingularAttribute<FbUsuarioPref, FbUsuario> idUsuario;
    public static volatile SingularAttribute<FbUsuarioPref, Date> fechaCreacion;
    public static volatile SingularAttribute<FbUsuarioPref, BigDecimal> idPref;
    public static volatile SingularAttribute<FbUsuarioPref, String> idioma;
    public static volatile SingularAttribute<FbUsuarioPref, String> theme;
    public static volatile SingularAttribute<FbUsuarioPref, String> locale;

}