package com.fastbooks.modelo;

import com.fastbooks.modelo.FbCiudad;
import com.fastbooks.modelo.FbCompania;
import com.fastbooks.modelo.FbEstado;
import com.fastbooks.modelo.FbPais;
import com.fastbooks.modelo.FbUsuario;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-12T16:35:36")
@StaticMetamodel(FbDireccion.class)
public class FbDireccion_ { 

    public static volatile SingularAttribute<FbDireccion, String> urbanizacion;
    public static volatile SingularAttribute<FbDireccion, FbPais> idPais;
    public static volatile ListAttribute<FbDireccion, FbCompania> fbCompaniaList;
    public static volatile SingularAttribute<FbDireccion, BigDecimal> idDireccion;
    public static volatile SingularAttribute<FbDireccion, String> apartamento;
    public static volatile SingularAttribute<FbDireccion, FbCiudad> idCiudad;
    public static volatile SingularAttribute<FbDireccion, String> piso;
    public static volatile SingularAttribute<FbDireccion, FbEstado> idEstado;
    public static volatile SingularAttribute<FbDireccion, Date> fechaCreacion;
    public static volatile SingularAttribute<FbDireccion, String> avenida;
    public static volatile ListAttribute<FbDireccion, FbUsuario> fbUsuarioList;

}