package com.fastbooks.modelo;

import com.fastbooks.modelo.FbCompania;
import com.fastbooks.modelo.FbProduct;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-04-05T13:00:17")
@StaticMetamodel(FbTax.class)
public class FbTax_ { 

    public static volatile ListAttribute<FbTax, FbProduct> fbProductList;
    public static volatile SingularAttribute<FbTax, String> descrip;
    public static volatile SingularAttribute<FbTax, String> rate;
    public static volatile SingularAttribute<FbTax, BigDecimal> idTax;
    public static volatile SingularAttribute<FbTax, FbCompania> idCia;
    public static volatile SingularAttribute<FbTax, String> name;
    public static volatile SingularAttribute<FbTax, Date> fechaCreacion;
    public static volatile SingularAttribute<FbTax, String> status;
    public static volatile SingularAttribute<FbTax, BigInteger> userCreacion;

}