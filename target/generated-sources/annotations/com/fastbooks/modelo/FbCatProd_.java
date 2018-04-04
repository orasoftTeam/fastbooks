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

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-04-03T12:39:07")
@StaticMetamodel(FbCatProd.class)
public class FbCatProd_ { 

    public static volatile SingularAttribute<FbCatProd, BigDecimal> idCat;
    public static volatile SingularAttribute<FbCatProd, String> status;
    public static volatile SingularAttribute<FbCatProd, String> name;
    public static volatile SingularAttribute<FbCatProd, String> descrip;
    public static volatile SingularAttribute<FbCatProd, FbCompania> idCia;
    public static volatile SingularAttribute<FbCatProd, Date> fechaCreacion;
    public static volatile SingularAttribute<FbCatProd, BigInteger> userCreacion;
    public static volatile ListAttribute<FbCatProd, FbProduct> fbProductList;

}