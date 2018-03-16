package com.fastbooks.modelo;

import com.fastbooks.modelo.FbDireccion;
import com.fastbooks.modelo.FbPerfilXUsuario;
import com.fastbooks.modelo.FbUsuarioPref;
import com.fastbooks.modelo.FbUsuarioXCia;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-16T15:15:57")
@StaticMetamodel(FbUsuario.class)
public class FbUsuario_ { 

    public static volatile SingularAttribute<FbUsuario, Integer> hasCia;
    public static volatile SingularAttribute<FbUsuario, FbDireccion> idDireccion;
    public static volatile ListAttribute<FbUsuario, FbPerfilXUsuario> fbPerfilXUsuarioList;
    public static volatile SingularAttribute<FbUsuario, String> telefono;
    public static volatile SingularAttribute<FbUsuario, String> lastname;
    public static volatile SingularAttribute<FbUsuario, String> firstname;
    public static volatile SingularAttribute<FbUsuario, String> midname;
    public static volatile SingularAttribute<FbUsuario, String> genero;
    public static volatile SingularAttribute<FbUsuario, BigDecimal> idUsuario;
    public static volatile SingularAttribute<FbUsuario, String> estado;
    public static volatile SingularAttribute<FbUsuario, String> email;
    public static volatile ListAttribute<FbUsuario, FbUsuarioPref> fbUsuarioPrefList;
    public static volatile SingularAttribute<FbUsuario, Date> fechaCreacion;
    public static volatile SingularAttribute<FbUsuario, String> bDay;
    public static volatile SingularAttribute<FbUsuario, String> clave;
    public static volatile ListAttribute<FbUsuario, FbUsuarioXCia> fbUsuarioXCiaList;

}