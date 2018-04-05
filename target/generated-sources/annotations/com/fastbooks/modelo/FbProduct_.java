package com.fastbooks.modelo;

import com.fastbooks.modelo.FbCatProd;
import com.fastbooks.modelo.FbCompania;
import com.fastbooks.modelo.FbTax;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-04-05T13:00:17")
@StaticMetamodel(FbProduct.class)
public class FbProduct_ { 

    public static volatile SingularAttribute<FbProduct, String> descrip;
    public static volatile SingularAttribute<FbProduct, BigInteger> incTax;
    public static volatile SingularAttribute<FbProduct, BigDecimal> idProd;
    public static volatile SingularAttribute<FbProduct, FbTax> idTax;
    public static volatile SingularAttribute<FbProduct, String> photo;
    public static volatile SingularAttribute<FbProduct, String> type;
    public static volatile SingularAttribute<FbProduct, BigInteger> initQuant;
    public static volatile SingularAttribute<FbProduct, FbCatProd> idCat;
    public static volatile SingularAttribute<FbProduct, BigInteger> userCreacion;
    public static volatile SingularAttribute<FbProduct, BigDecimal> price;
    public static volatile SingularAttribute<FbProduct, FbCompania> idCia;
    public static volatile SingularAttribute<FbProduct, String> name;
    public static volatile SingularAttribute<FbProduct, Date> fechaCreacion;
    public static volatile SingularAttribute<FbProduct, String> sku;
    public static volatile SingularAttribute<FbProduct, String> status;

}